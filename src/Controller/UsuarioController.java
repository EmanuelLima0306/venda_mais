/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.EstadoModel;
import Model.TipoUsuarioModel;
import Model.UsuarioModel;
import java.awt.Menu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author celso
 */
public class UsuarioController implements IController<UsuarioModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(UsuarioModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `usuario`\n"
                    + "("
                    + "`Nome`,\n"
                    + "`Email`,\n"
                    + "`Contacto`,\n"
                    + "`Senha`,\n"
                    + "`IdEstado`,\n"
                    + "`IdTipoUsuario`,\n"
                    + "`Data`,\n"
                    + "`Usuario`,StatusAcesso,IdUsuario)\n"
                    + "VALUES\n"
                    + "("
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "md5(?),\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,?,?);";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getNome());
            command.setString(2, obj.getEmail());
            command.setString(3, obj.getContacto());
            command.setString(4, obj.getSenha());
            command.setInt(5, obj.getEstado().getId());
            command.setInt(6, obj.getTipoUsuario().getId());
            command.setDouble(7, obj.getTaxa());

            command.setString(7, obj.getData());
            command.setString(8, obj.getUsuario());
            command.setString(9, obj.getStatusAcesso());
            command.setInt(10, obj.getIdUsuario());

            result = command.executeUpdate() > 0;
            
            // adicionar as permições ao novo usuario
            if(result){
            
                MenuItemUsuarioController menuItemUsuarioController = new MenuItemUsuarioController();
                menuItemUsuarioController.saveAllByUser(getLastByUsuario(obj));
            
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        
        
        return result;
    }

    @Override
    public boolean update(UsuarioModel obj) {

        boolean result = false;
        try {
            String sql = "UPDATE `usuario`\n"
                    + " SET "
                    + "`Nome` = ?,\n"
                    + "`Email` = ?,\n"
                    + "`Contacto` = ?,\n"
                    + "`Senha`= md5(?),\n"
                    + "`IdEstado` = ?,\n"
                    + "`IdTipoUsuario` = ?,\n"
                    + "`Usuario` = ?,StatusAcesso=?, IdUsuario = ?, Taxa = ? WHERE Id = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getNome());
            command.setString(2, obj.getEmail());
            command.setString(3, obj.getContacto());
            command.setString(4, obj.getSenha());
            command.setInt(5, obj.getEstado().getId());
            command.setInt(6, obj.getTipoUsuario().getId());
            

//            command.setString(7, obj.getData());
            command.setString(7, obj.getUsuario());
            command.setString(8, obj.getStatusAcesso());
            command.setInt(9, obj.getIdUsuario());
            command.setDouble(10, obj.getTaxa());

            command.setInt(11, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return result;
    }

    public boolean updateEstado(UsuarioModel obj) {

        boolean result = false;
        try {
            String sql = "UPDATE `usuario`\n"
                    + " SET "
                    + "`IdEstado` = ?\n"
                    + " WHERE Id = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);

            command.setInt(1, obj.getEstado().getId());

            command.setInt(2, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(UsuarioModel obj) {

        return obj.getId() > 0 ? update(obj) : save(obj);
    }

    @Override
    public UsuarioModel getById(int id) {
        
                UsuarioModel usuario = null;
        try {
                String sql = "SELECT * FROM usuario WHERE id = ?";

                con = conFactory.open();//ABRIR CONEXAO COM O SERVIDOR DE BD
                 command = con.prepareStatement(sql);
                command.setInt(1, id);


                 query = command.executeQuery();
                if (query.next()) {

                    usuario = new UsuarioModel();
                    usuario.setEstado(new EstadoModel(query.getInt("IdEstado"), ""));

                    usuario.setId(query.getInt("Id"));
                    usuario.setNome(query.getString("Nome"));
                    usuario.setUsuario(query.getString("Usuario"));
                    usuario.setEmail(query.getString("Email"));
                    usuario.setContacto(query.getString("Contacto"));
                    usuario.setSenha(query.getString("Senha"));
                    usuario.setStatusAcesso(query.getString("StatusAcesso"));
                    usuario.setIdUsuario(query.getInt("IdUsuario"));
                    usuario.setTipoUsuario(new TipoUsuarioModel(query.getInt("IdTipoUsuario"), sql));
                }
            } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }    
//        ////conFactory.close(con, command, query);

        return usuario;
    
    }

    @Override
    public List<UsuarioModel> get() {

        List<UsuarioModel> lista = new ArrayList();
        try {

            String sql = "SELECT * FROM usuario u "
                    + " INNER JOIN tipousuario t  ON u.IdTipoUsuario = t.Id"
                    + " WHERE u.IdEstado NOT IN(3) and u.Nome NOT IN('ASCEMIL','DIVERSOS')";

            con = conFactory.open();;//ABRIR CONEXAO COM O SERVIDOR DE BD
            command = con.prepareStatement(sql);

            query = command.executeQuery();
            while (query.next()) {

                UsuarioModel usuario = new UsuarioModel();
                usuario.setId(query.getInt("u.Id"));
                usuario.setNome(query.getString("Nome"));
                usuario.setUsuario(query.getString("Usuario"));
                usuario.setEmail(query.getString("Email"));
                usuario.setContacto(query.getString("Contacto"));
                usuario.setSenha(query.getString("Senha"));
                usuario.setIdUsuario(query.getInt("u.IdUsuario"));
                usuario.setTipoUsuario(new TipoUsuarioModel(query.getInt("IdTipoUsuario"),
                        query.getString("t.Designacao")));
                lista.add(usuario);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }
    public List<UsuarioModel> getCaixaAberto() {

        List<UsuarioModel> lista = new ArrayList();
        try {

            String sql = "SELECT * FROM usuario u "
                    + " INNER JOIN tipousuario t  ON u.IdTipoUsuario = t.Id INNER JOIN caixa caixa ON u.Id=caixa.IdUsuario"
                    + " WHERE u.IdEstado NOT IN(3) and u.Nome NOT IN('ASCEMIL','DIVERSOS') AND caixa.estado='Aberto'";

            con = conFactory.open();;//ABRIR CONEXAO COM O SERVIDOR DE BD
            command = con.prepareStatement(sql);

            query = command.executeQuery();
            while (query.next()) {

                UsuarioModel usuario = new UsuarioModel();
                usuario.setId(query.getInt("u.Id"));
                usuario.setNome(query.getString("Nome"));
                usuario.setUsuario(query.getString("Usuario"));
                usuario.setEmail(query.getString("Email"));
                usuario.setContacto(query.getString("Contacto"));
                usuario.setSenha(query.getString("Senha"));
                usuario.setIdUsuario(query.getInt("u.IdUsuario"));
                usuario.setTipoUsuario(new TipoUsuarioModel(query.getInt("IdTipoUsuario"),
                        query.getString("t.Designacao")));
                lista.add(usuario);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }
    public List<UsuarioModel> getAll() {

        List<UsuarioModel> lista = new ArrayList();
        try {

            String sql = "SELECT * FROM usuario u "
                    + " INNER JOIN tipousuario t  ON u.IdTipoUsuario = t.Id"
                    + " WHERE u.IdEstado NOT IN(3) and u.Id NOT IN(1) and u.IdTipoUsuario = 3 ORDER BY 1";

            con = conFactory.open();;//ABRIR CONEXAO COM O SERVIDOR DE BD
            command = con.prepareStatement(sql);

            query = command.executeQuery();
            while (query.next()) {

                UsuarioModel usuario = new UsuarioModel();
                usuario.setId(query.getInt("u.Id"));
                usuario.setNome(query.getString("Nome"));
                usuario.setUsuario(query.getString("Usuario"));
                usuario.setEmail(query.getString("Email"));
                usuario.setContacto(query.getString("Contacto"));
                usuario.setSenha(query.getString("Senha"));
                usuario.setIdUsuario(query.getInt("u.IdUsuario"));
                usuario.setTipoUsuario(new TipoUsuarioModel(query.getInt("IdTipoUsuario"),
                        query.getString("t.Designacao")));
                lista.add(usuario);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public List<UsuarioModel> get(String designacao) {

        List<UsuarioModel> lista = new ArrayList();
        try {

            String sql = "SELECT * FROM usuario u "
                    + " INNER JOIN tipousuario t  ON u.IdTipoUsuario = t.Id"
                    + " WHERE u.IdEstado NOT IN(3) AND u.Nome NOT IN('ASCEMIL', 'DIVERSOS') AND u.Nome LIKE '%" + designacao + "%'";

            con = conFactory.open();;//ABRIR CONEXAO COM O SERVIDOR DE BD
            command = con.prepareStatement(sql);

            query = command.executeQuery();
            while (query.next()) {

                UsuarioModel usuario = new UsuarioModel();
                usuario.setId(query.getInt("u.Id"));
                usuario.setNome(query.getString("Nome"));
                usuario.setUsuario(query.getString("Usuario"));
                usuario.setEmail(query.getString("Email"));
                usuario.setContacto(query.getString("Contacto"));
                usuario.setSenha(query.getString("Senha"));
                usuario.setIdUsuario(query.getInt("u.IdUsuario"));
                usuario.setTipoUsuario(new TipoUsuarioModel(query.getInt("IdTipoUsuario"),
                        query.getString("t.Designacao")));
                lista.add(usuario);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public UsuarioModel getUsuario(UsuarioModel modelo) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM usuario WHERE IdEstado NOT IN(3) and Usuario = ? AND Senha = md5(?)";

        con = conFactory.open();//ABRIR CONEXAO COM O SERVIDOR DE BD
         command = con.prepareStatement(sql);
        command.setString(1, modelo.getUsuario());
        command.setString(2, modelo.getSenha());

        UsuarioModel usuario = null;

         query = command.executeQuery();
        if (query.next()) {

            usuario = new UsuarioModel();
            usuario.setEstado(new EstadoModel(query.getInt("IdEstado"), ""));

            usuario.setId(query.getInt("Id"));
            usuario.setNome(query.getString("Nome"));
            usuario.setUsuario(query.getString("Usuario"));
            usuario.setEmail(query.getString("Email"));
            usuario.setContacto(query.getString("Contacto"));
            usuario.setSenha(query.getString("Senha"));
            usuario.setStatusAcesso(query.getString("StatusAcesso"));
            usuario.setIdUsuario(query.getInt("IdUsuario"));

            usuario.setTipoUsuario(new TipoUsuarioModel(query.getInt("IdTipoUsuario"), sql));
        }
        command.close();
        ////con.close();
        query.close();
        ////conFactory.close(con, command, query);

        return usuario;

    }
    
    public UsuarioModel getLastByUsuario(UsuarioModel modelo) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM usuario WHERE IdUsuario = ? ORDER BY id asc";

        con = conFactory.open();//ABRIR CONEXAO COM O SERVIDOR DE BD
         command = con.prepareStatement(sql);
        command.setInt(1, modelo.getIdUsuario());

        UsuarioModel usuario = null;

         query = command.executeQuery();
        if (query.last()) {

            usuario = new UsuarioModel();
            usuario.setEstado(new EstadoModel(query.getInt("IdEstado"), ""));

            usuario.setId(query.getInt("Id"));
            usuario.setNome(query.getString("Nome"));
            usuario.setUsuario(query.getString("Usuario"));
            usuario.setEmail(query.getString("Email"));
            usuario.setContacto(query.getString("Contacto"));
            usuario.setSenha(query.getString("Senha"));
            usuario.setStatusAcesso(query.getString("StatusAcesso"));
            
            usuario.setIdUsuario(query.getInt("IdUsuario"));
            usuario.setTipoUsuario(new TipoUsuarioModel(query.getInt("IdTipoUsuario"), sql));
        }
        command.close();
        ////con.close();
        query.close();
//        ////conFactory.close(con, command, query);

        return usuario;

    }

}

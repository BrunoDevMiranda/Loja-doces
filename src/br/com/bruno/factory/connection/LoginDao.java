package br.com.bruno.factory.connection;


import br.com.bruno.factory.ConnectionFactory;
import br.com.bruno.factory.DbException;
import br.com.bruno.services.Login;


import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class LoginDao {

    public void save(Login login) throws RuntimeException {
        String sql = "INSERT INTO tb_login(usuario,senha,tipo) VALUES (?,?,?)";
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            //Create connection for DB
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setString(1, login.getUsuario());
            pstm.setString(2,login.getSenha());
            pstm.setString(3,login.getTipoUsuario());

            int line = pstm.executeUpdate();
            if (line > 0) {
                System.out.println("Usuário foi cadastrado com sucesso");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //close Connection
            try {
                ConnectionFactory.closePreparedStatement(pstm);
                ConnectionFactory.closeConnection(connection);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    public void update(Login login)  {
        String sql = "UPDATE tb_login set usuario = ?, senha=?,tipo=?" + "WHERE ID = ?";
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setString(1, login.getUsuario());
            pstm.setString(2, login.getSenha());
            pstm.setString(3, login.getTipoUsuario());
            pstm.setInt(4,login.getID());
            int line = pstm.executeUpdate();
            if (line > 0){
                System.out.println("login foi Altarado com sucesso");
            }else{
                System.out.println("login não Cadastrado");
               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            //close Connection
            try {
                ConnectionFactory.closePreparedStatement(pstm);
                ConnectionFactory.closeConnection(connection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Login> findlogin() throws RuntimeException {
        String sql = "SELECT * FROM tb_login ";
        List<Login> logins = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        try {
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            rset = pstm.executeQuery();
            while (rset.next()) {
                Login login = new Login();
                login.setUsuario(rset.getString("usuario"));
                login.setSenha(rset.getString("senha"));
                login.setTipoUsuario(rset.getString("tipo"));
                logins.add(login);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //close Connection
            try {
                ConnectionFactory.closePreparedStatement(pstm);
                ConnectionFactory.closeConnection(connection);
                ConnectionFactory.closeResultSet(rset);
            } catch (DbException e) {
                throw new RuntimeException(e);
            }
            return logins;
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM tb_login WHERE id = ?";
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            //Create connection for DB
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, id);
            int line = pstm.executeUpdate();
            if (line > 0){
                System.out.println("login foi deletada do banco de dados");
            }else{
                System.out.println("login não Cadastrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            //close Connection
            try {
                ConnectionFactory.closePreparedStatement(pstm);
                ConnectionFactory.closeConnection(connection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Login findByCodigo(Integer id) {
        String sql = "SELECT * FROM tb_login where id = ?";
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;
        Login login = new Login();
        try {
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, id);
            rset = pstm.executeQuery();
            if (rset.next()) {
                login.setID(rset.getInt("id"));
                login.setUsuario(rset.getString("usuario"));
                login.setSenha(rset.getString("senha"));
                login.setTipoUsuario(rset.getString("tipo"));
            } else {
                JOptionPane.showMessageDialog(null, "login não foi encontrado no banco de dados", "Tente novamente", JOptionPane.ERROR_MESSAGE);
                System.out.println("login não encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            //close Connection
            try {
                ConnectionFactory.closePreparedStatement(pstm);
                ConnectionFactory.closeConnection(connection);
                ConnectionFactory.closeResultSet(rset);
            } catch (DbException e) {
                throw new RuntimeException(e);
            }
            return login;
        }
    }



}

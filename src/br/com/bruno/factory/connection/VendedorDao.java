package br.com.bruno.factory.connection;


import br.com.bruno.factory.ConnectionFactory;
import br.com.bruno.factory.DbException;
import br.com.bruno.model.Vendedor;


import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VendedorDao {

    public void save(Vendedor vendedor) throws RuntimeException {
        String sql = "INSERT INTO tb_vendedor(nome,cpf) VALUES (?,?)";
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            //Create connection for DB
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setString(1, vendedor.getNome());
            pstm.setString(2,vendedor.getCpf());

//            pstm.execute();
            int line = pstm.executeUpdate();
            if (line > 0) {
                System.out.println("vendedor foi cadastrado com sucesso");
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

    public void update(Vendedor vendedor)  {
        String sql = "UPDATE tb_vendedor set nome = ?, cpf=?" + "WHERE id = ?";
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setString(1, vendedor.getNome());
            pstm.setString(2, vendedor.getCpf());
            pstm.setInt(3, vendedor.getId());
            int line = pstm.executeUpdate();
            if (line > 0){
                System.out.println("vendedor foi Altarado com sucesso");
            }else{
                System.out.println("vendedor não Cadastrado");
               
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

    public List<Vendedor> findvendedor() throws RuntimeException {
        String sql = "SELECT * FROM tb_vendedor ";
        List<Vendedor> vendedors = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        try {
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            rset = pstm.executeQuery();
            while (rset.next()) {
                Vendedor vendedor = new Vendedor();
                vendedor.setId(rset.getInt(1));
                vendedor.setNome(rset.getString("nome"));
                vendedor.setCpf(rset.getString("cpf"));
                vendedors.add(vendedor);
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
            return vendedors;
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM tb_vendedor WHERE id = ?";
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            //Create connection for DB
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, id);
            int line = pstm.executeUpdate();
            if (line > 0){
                System.out.println("vendedor foi deletada do banco de dados");
            }else{
                System.out.println("vendedor não Cadastrado");
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

    public Vendedor findByCodigo(Integer id) {
        String sql = "SELECT * FROM tb_vendedor where id = ?";
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;
        Vendedor vendedor = new Vendedor();
        try {
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, id);
            rset = pstm.executeQuery();
            if (rset.next()) {
                vendedor.setId(rset.getInt("id"));
                vendedor.setNome(rset.getString("nome"));
                vendedor.setCpf(rset.getString("cpf"));
                if (id != null) {
                    System.out.println("Codigo: " + id + ", nome: " + vendedor.getNome() + ", CPF: " + vendedor.getCpf());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vendedor não foi encontrado no banco de dados", "Tente novamente", JOptionPane.ERROR_MESSAGE);
                System.out.println("vendedor não encontrado");
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
            return vendedor;
        }
    }



}

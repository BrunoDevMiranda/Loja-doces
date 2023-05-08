package br.com.bruno.factory.connection;


import br.com.bruno.factory.ConnectionFactory;
import br.com.bruno.factory.DbException;
import br.com.bruno.model.Produto;


import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDao {

    public void save(Produto produto) throws RuntimeException {
        String sql = "INSERT INTO tb_produto(nome,tipo,preco) VALUES (?,?,?)";
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            //Create connection for DB
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setString(1, produto.getNome());
            pstm.setString(2,produto.getTipo());
            pstm.setDouble(3,produto.getPreco());

//            pstm.execute();
            int line = pstm.executeUpdate();
            if (line > 0) {
                System.out.println("produto foi cadastrado com sucesso");
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

    public void update(Produto produto)  {
        String sql = "UPDATE tb_produto set nome = ?,tipo=?, preco=?" + "WHERE ID = ?";
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setString(1, produto.getNome());
            pstm.setString(2, produto.getTipo());
            pstm.setDouble(3, produto.getPreco());
            pstm.setInt(4, produto.getId());
            int line = pstm.executeUpdate();
            if (line > 0){
                System.out.println("produto foi Alterado com sucesso");
                JOptionPane.showMessageDialog(null,"produto foi Alterado com sucesso");
            }else{
                System.out.println("produto não Cadastrado");
                JOptionPane.showMessageDialog(null,"produto não encontrado","Tente novamente",JOptionPane.ERROR_MESSAGE);
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

    public List<Produto> findproduto() throws RuntimeException {
        String sql = "SELECT * FROM tb_produto ";
        List<Produto> produtos = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        try {
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            rset = pstm.executeQuery();
            while (rset.next()) {
                Produto produto = new Produto();
                produto.setId(rset.getInt(1));
                produto.setNome(rset.getString("nome"));
                produto.setTipo(rset.getString("tipo"));
                produto.setPreco(rset.getDouble("preco"));
                produtos.add(produto);
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
            return produtos;
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM tb_produto WHERE id = ?";
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            //Create connection for DB
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, id);
            int line = pstm.executeUpdate();
            if (line > 0){
                System.out.println("produto foi deletada do banco de dados");
                JOptionPane.showMessageDialog(null,"produto deletado com sucesso");
            }else{
                System.out.println("produto não Cadastrado");
                JOptionPane.showMessageDialog(null,"produto não encontrado","Tente novamente",JOptionPane.ERROR_MESSAGE);
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

    public static Produto findByCodigo(Integer id) {
        String sql = "SELECT * FROM tb_produto where ID = ?";
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;
        Produto produto = new Produto();
        try {
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, id);
            rset = pstm.executeQuery();
            if (rset.next()) {
                produto.setId(rset.getInt("id"));
                produto.setNome(rset.getString("nome"));
                produto.setTipo(rset.getString("tipo"));
                produto.setPreco(rset.getDouble("preco"));
                if (id != null) {
                    System.out.println("Codigo: " + id + ", nome: " + produto.getNome() + ", Tipo: " + produto.getTipo());                }
            } else {
                System.out.println("produto não encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            //close Connection
            try {
                ConnectionFactory.closePreparedStatement(pstm);
                ConnectionFactory.closeConnection(connection);
                ConnectionFactory.closeResultSet(rset);
            }catch (Exception e){

            }
        }
        return produto;
    }



}

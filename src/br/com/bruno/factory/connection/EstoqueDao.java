package br.com.bruno.factory.connection;


import br.com.bruno.factory.ConnectionFactory;
import br.com.bruno.factory.DbException;
import br.com.bruno.model.Estoque;
import br.com.bruno.model.Produto;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EstoqueDao {
    public void save(Estoque estoque) throws RuntimeException {
        String sql = "INSERT INTO tb_estoque(produto_id,quantidade) VALUES (?,?)";
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            //Create connection for DB
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, estoque.getProduto().getId());
            pstm.setInt(2,estoque.getQuantidade());

            int line = pstm.executeUpdate();
            if (line > 0) {
                System.out.println("estoque foi cadastrado com sucesso");
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

    public List<Estoque> findEstoque() throws RuntimeException {
        String sql = "SELECT p.nome, p.tipo, p.preco, e.quantidade FROM tb_estoque e " +
                "JOIN tb_produto p ON e.produto_id = p.id";
        List<Estoque> estoques = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;
        try {
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            rset = pstm.executeQuery();
            while (rset.next()) {
                Estoque estoque = new Estoque();
                Produto produto = new Produto();

                produto.setNome(rset.getString("nome"));
                produto.setTipo(rset.getString("tipo"));
                produto.setPreco(rset.getDouble("preco"));

                estoque.setProduto(produto);
                estoque.setQuantidade(rset.getInt("quantidade"));

                estoques.add(estoque);
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
        }
        return estoques;
    }

    public void delete(int id) {
        String sql = "DELETE FROM tb_estoque WHERE id = ?";
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            //Create connection for DB
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, id);
            int line = pstm.executeUpdate();
            if (line > 0){
                System.out.println("estoque foi deletada do banco de dados");
                JOptionPane.showMessageDialog(null,"estoque deletado com sucesso");
            }else{
                System.out.println("estoque não Cadastrado");
                JOptionPane.showMessageDialog(null,"estoque não encontrado","Tente novamente",JOptionPane.ERROR_MESSAGE);
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



}

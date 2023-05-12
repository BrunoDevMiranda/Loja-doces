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
        String selectSql = "SELECT COUNT(*) FROM tb_estoque WHERE produto_id = ?";
        String updateSql = "UPDATE tb_estoque SET quantidade = quantidade + ? WHERE produto_id = ?";
        String insertSql = "INSERT INTO tb_estoque(produto_id,quantidade) VALUES (?,?)";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;
        PreparedStatement insertStmt = null;
        try {
            // Create connection for DB
            connection = ConnectionFactory.getConnection();

            // Check if product already exists in the database
            selectStmt = connection.prepareStatement(selectSql);
            selectStmt.setInt(1, estoque.getProduto().getId());
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Product already exists in the database, update quantity
                updateStmt = connection.prepareStatement(updateSql);
                updateStmt.setInt(1, estoque.getQuantidade());
                updateStmt.setInt(2, estoque.getProduto().getId());
                int line = updateStmt.executeUpdate();
                if (line > 0) {
                    int resposta = JOptionPane.showConfirmDialog(null, "Produto já existente deseja atualizar a quantidade?", "Confirmação", JOptionPane.YES_NO_OPTION);
                    if (resposta == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(null, "Estoque atualizado com sucesso!");
                        System.out.println("Estoque atualizado com sucesso");
                    } else {
                        JOptionPane.showMessageDialog(null, "Tenta cadastrar outro produto");
                    }
                }
            } else {
                // Product does not exist in the database, insert new record
                insertStmt = connection.prepareStatement(insertSql);
                insertStmt.setInt(1, estoque.getProduto().getId());
                insertStmt.setInt(2, estoque.getQuantidade());
                int line = insertStmt.executeUpdate();
                if (line > 0) {
                    JOptionPane.showMessageDialog(null, " Produto cadastrado no estoque com sucesso!");
                    System.out.println("Estoque foi cadastrado com sucesso");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                ConnectionFactory.closePreparedStatement(selectStmt);
                ConnectionFactory.closePreparedStatement(updateStmt);
                ConnectionFactory.closePreparedStatement(insertStmt);
                ConnectionFactory.closeConnection(connection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Estoque> findEstoqueAll() throws RuntimeException {
        String sql = "SELECT * From tb_estoque";
        List<Estoque> estoques = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;
        try {
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            rset = pstm.executeQuery();
            while (rset.next()) {
                Produto produto = new Produto();
                produto.setId(rset.getInt("produto_id"));
                Estoque estoque = new Estoque();
                estoque.setId(rset.getInt("ID"));
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
            if (line > 0) {
                System.out.println("estoque foi deletada do banco de dados");
                JOptionPane.showMessageDialog(null, "estoque deletado com sucesso");
            } else {
                System.out.println("estoque não Cadastrado");
                JOptionPane.showMessageDialog(null, "estoque não encontrado", "Tente novamente", JOptionPane.ERROR_MESSAGE);
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

    public List<Estoque> innerJoin() throws RuntimeException {
        String sql = "SELECT p.ID , p.Nome, p.Tipo, p.Preco, e.Quantidade " +
                "FROM tb_estoque e " +
                "INNER JOIN tb_produto p " +
                "ON e.produto_id = p.ID"+
                " ORDER BY p.ID";
        List<Estoque> estoques = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;
        try {
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            rset = pstm.executeQuery();
            while (rset.next()) {
                Produto produto = new Produto();
                produto.setId(rset.getInt("ID"));
                produto.setNome(rset.getString("Nome"));
                produto.setTipo(rset.getString("Tipo"));
                produto.setPreco(rset.getDouble("Preco"));
                Estoque estoque = new Estoque();

                estoque.setProduto(produto);
                estoque.setQuantidade(rset.getInt("Quantidade"));
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


    public int findByEstoqueProduto(Produto produto) {
        String sql = "SELECT quantidade FROM tb_estoque WHERE produto_id = ?";
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;
        int quantidade = 0;
        try {
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, produto.getId());
            rset = pstm.executeQuery();
            if (rset.next()) {
                quantidade = rset.getInt("quantidade");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionFactory.closePreparedStatement(pstm);
                ConnectionFactory.closeConnection(connection);
                ConnectionFactory.closeResultSet(rset);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return quantidade;


    }

    public void atualizarEstoque(Produto produto, int quantidadeVendida) throws DbException {
        String sql = "UPDATE tb_estoque SET quantidade = quantidade - ? WHERE produto_id = ?";
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, quantidadeVendida);
            pstm.setInt(2, produto.getId());
            pstm.executeUpdate();
        } catch (Exception e) {
            throw new DbException("Erro ao atualizar estoque: " + e.getMessage());
        } finally {
            try {
                ConnectionFactory.closePreparedStatement(pstm);
                ConnectionFactory.closeConnection(connection);
            } catch (Exception e) {
                throw new DbException("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

}

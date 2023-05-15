package br.com.bruno.factory.connection;


import br.com.bruno.factory.ConnectionFactory;
import br.com.bruno.factory.DbException;
import br.com.bruno.model.Ingredientes;


import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class IngredientesDao {
    public void save(Ingredientes ingredientes) throws RuntimeException {
        String selectSql = "SELECT COUNT(*) FROM tb_ingrediente WHERE ID = ?";
        String updateSql = "UPDATE tb_ingrediente SET peso_liquido = tb_ingrediente.peso_liquido + ? WHERE ID = ?";
        String insertSql = "INSERT INTO tb_ingrediente(ID,peso_liquido) VALUES (?,?)";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;
        PreparedStatement insertStmt = null;
        try {
            // Create connection for DB
            connection = ConnectionFactory.getConnection();

            // Check if product already exists in the database
            selectStmt = connection.prepareStatement(selectSql);
            selectStmt.setInt(1, ingredientes.getID());
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Product already exists in the database, update quantity
                updateStmt = connection.prepareStatement(updateSql);
                updateStmt.setFloat(1, ingredientes.getPeso_liquido());
                updateStmt.setInt(2, ingredientes.getID());
                int line = updateStmt.executeUpdate();
                if (line > 0) {
                    int resposta = JOptionPane.showConfirmDialog(null, "Ingrediente já existente deseja atualizar a quantidade?", "Confirmação", JOptionPane.YES_NO_OPTION);
                    if (resposta == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(null, "Ingrediente atualizado com sucesso!");
                        System.out.println("Ingrediente atualizado com sucesso");
                    } else {
                        JOptionPane.showMessageDialog(null, "Tenta cadastrar outro Ingrediente");
                    }
                }
            } else {
                // Product does not exist in the database, insert new record
                insertStmt = connection.prepareStatement(insertSql);
                insertStmt.setInt(1, ingredientes.getID());
                insertStmt.setFloat(2, ingredientes.getPeso_liquido());
                int line = insertStmt.executeUpdate();
                if (line > 0) {
                    JOptionPane.showMessageDialog(null, " Ingrediente cadastrado no estoque com sucesso!");
                    System.out.println("Ingrediente foi cadastrado com sucesso");
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

    public void update(int ID, float quatidade) throws DbException {
        String sql = "UPDATE tb_ingrediente set  peso_liquido = peso_liquido - ?" + "WHERE id = ?";
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setFloat(1, quatidade);
            pstm.setInt(2, ID);
            int line = pstm.executeUpdate();
            if (line > 0) {
                System.out.println("ingredientes foi alterado com sucesso");
            } else {

                System.out.println("ingredientes não Cadastrado");
            }
        } catch (Exception e) {
            throw new DbException("Erro ao atualizar ingredientes: " + e.getMessage());
        } finally {
            try {
                ConnectionFactory.closePreparedStatement(pstm);
                ConnectionFactory.closeConnection(connection);
            } catch (Exception e) {
                throw new DbException("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    public List<Ingredientes> findingredientes() throws RuntimeException {
        String sql = "SELECT * FROM tb_ingrediente ";
        List<Ingredientes> ingredientes = new ArrayList<Ingredientes>();
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        try {
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            rset = pstm.executeQuery();
            while (rset.next()) {
                Ingredientes ingrediente = new Ingredientes();
                ingrediente.setID(rset.getInt(1));
                ingrediente.setNome(rset.getString("nome"));
                ingrediente.setPeso_liquido(rset.getFloat("peso_liquido"));
                ingredientes.add(ingrediente);
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
        return ingredientes;
    }

    public void delete(int id) {
        String sql = "DELETE FROM tb_ingrediente WHERE id = ?";
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            //Create connection for DB
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, id);
            int line = pstm.executeUpdate();
            if (line > 0) {
                System.out.println("ingredientes foi deletada do banco de dados");
            } else {
                System.out.println("ingredientes não Cadastrado");
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

    public Ingredientes findById(Integer id) {
        String sql = "SELECT * FROM tb_ingrediente where id = ?";
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;
        Ingredientes ingredientes = new Ingredientes();

        try {
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, id);
            rset = pstm.executeQuery();
            if (rset.next()) {
                ingredientes.setID(rset.getInt("id"));
                ingredientes.setNome(rset.getString("nome"));
                ingredientes.setPeso_liquido(rset.getFloat("peso_liquido"));
            } else {
                JOptionPane.showMessageDialog(null, "ingredientes não foi encontrado no banco de dados", "Tente novamente", JOptionPane.ERROR_MESSAGE);
                System.out.println("ingredientes não encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //close Connection
            try {
                ConnectionFactory.closePreparedStatement(pstm);
                ConnectionFactory.closeConnection(connection);
                ConnectionFactory.closeResultSet(rset);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return ingredientes;
    }


}



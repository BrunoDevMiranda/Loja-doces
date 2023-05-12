package br.com.bruno.factory.connection;


import br.com.bruno.factory.ConnectionFactory;
import br.com.bruno.factory.DbException;
import br.com.bruno.model.Cliente;
import br.com.bruno.view.PessoasView.NovoCliente;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {
    public void save(Cliente cliente) throws RuntimeException {
        String sql = "INSERT INTO tb_cliente(nome,cpf) VALUES (?,?)";
        Connection connection = null;
        PreparedStatement pstm = null;
        String cpf = cliente.getCpf();
        String cpfFormatado = cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
        try {
            //Create connection for DB
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setString(1, cliente.getNome());
            pstm.setString(2, cpfFormatado);
//            pstm.execute();
            int line = pstm.executeUpdate();
            if (line > 0) {
                System.out.println("Cliente foi cadastrado com sucesso");
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

    public void update(Cliente cliente) {
        String sql = "UPDATE tb_cliente set nome = ?, cpf=?" + "WHERE id = ?";
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setString(1, cliente.getNome());
            pstm.setString(2, cliente.getCpf());
            pstm.setInt(3, cliente.getId());
            int line = pstm.executeUpdate();
            if (line > 0) {
                System.out.println("Cliente foi alterado com sucesso");
            } else {

                System.out.println("Cliente não Cadastrado");
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

    public List<Cliente> findCliente() throws RuntimeException {
        String sql = "SELECT * FROM tb_cliente ";
        List<Cliente> clientes = new ArrayList<Cliente>();
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        try {
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            rset = pstm.executeQuery();
            while (rset.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rset.getInt(1));
                cliente.setNome(rset.getString("nome"));
                cliente.setCpf(rset.getString("cpf"));
                clientes.add(cliente);
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
            return clientes;
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM tb_cliente WHERE id = ?";
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            //Create connection for DB
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, id);
            int line = pstm.executeUpdate();
            if (line > 0) {
                System.out.println("Cliente foi deletada do banco de dados");
            } else {
                System.out.println("Cliente não Cadastrado");
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

    public Cliente findById(Integer id) {
        String sql = "SELECT * FROM tb_cliente where id = ?";
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;
        Cliente cliente = new Cliente();

        try {
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, id);
            rset = pstm.executeQuery();
            if (rset.next()) {
                cliente.setId(rset.getInt("id"));
                cliente.setNome(rset.getString("nome"));
                cliente.setCpf(rset.getString("cpf"));
                if (id != null) {
                    System.out.println("Codigo: " + id + ", nome: " + cliente.getNome() + ", CPF: " + cliente.getCpf());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Cliente não foi encontrado no banco de dados", "Tente novamente", JOptionPane.ERROR_MESSAGE);
                System.out.println("Cliente não encontrado");
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

            }
        }
        return cliente;
    }
    public Cliente findByCpf(String cpf) {

        String cpfSemPontos = cpf.replace("\\.", "").replace("-",""); // Remover os pontos do CPF
        String sql = "SELECT * FROM tb_cliente WHERE REPLACE(REPLACE(cpf, '.', ''), '-', '') = ?";

        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;
        Cliente cliente = new Cliente();

        try {
            connection = ConnectionFactory.getConnection();
            pstm = connection.prepareStatement(sql);
            pstm.setString(1, cpfSemPontos); // Definir o CPF sem pontos como parâmetro
            rset = pstm.executeQuery();
            if (rset.next()) {
                cliente.setId(rset.getInt("id"));
                cliente.setNome(rset.getString("nome"));
                cliente.setCpf(rset.getString("cpf"));
                System.out.println("Codigo: " + cliente.getId() + ", nome: " + cliente.getNome() + ", CPF: " + cliente.getCpf());
            } else {
                int  confirma =JOptionPane.showConfirmDialog(null, "Cliente não encontrado, Cadastrar um novo?","Confirm",JOptionPane.YES_NO_CANCEL_OPTION);
                if (confirma == JOptionPane.YES_OPTION){
                    NovoCliente novoCliente = new NovoCliente();}

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
                e.printStackTrace();
            }
        }
        return cliente;
    }

}



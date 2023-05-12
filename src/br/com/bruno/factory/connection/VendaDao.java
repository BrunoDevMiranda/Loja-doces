package br.com.bruno.factory.connection;


import br.com.bruno.factory.ConnectionFactory;
import br.com.bruno.factory.DbException;


import br.com.bruno.model.ItemVenda;
import br.com.bruno.model.Venda;


import java.sql.*;




    public class VendaDao {
        public void save(Venda venda) throws DbException {
            Connection connection = null;
            PreparedStatement pstm = null;

            try {
                connection = ConnectionFactory.getConnection();
                connection.setAutoCommit(false);

                // insere a venda na tabela Venda
                pstm = connection.prepareStatement(
                        "INSERT INTO tb_venda (data_venda,cliente_id,vendedor_id,produto_id,produto_preco,quantidade) " +
                                "VALUES (?, ?, ?, ?, ?, ?)");
                pstm.setDate(1, Date.valueOf(venda.getData()));
                pstm.setInt(2, venda.getCliente().getId());
                pstm.setInt(3, venda.getVendedor().getId());

                for (ItemVenda itemVenda : venda.getItens()) {
                    pstm.setInt(4, itemVenda.getProduto().getId());
                    pstm.setDouble(5, itemVenda.getProduto().getPreco());
                    pstm.setInt(6, itemVenda.getQuantidade());
                    pstm.executeUpdate();
                }
                connection.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }







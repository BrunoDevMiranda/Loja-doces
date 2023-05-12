package br.com.bruno.application;



import br.com.bruno.factory.DbException;
import br.com.bruno.factory.connection.*;
import br.com.bruno.model.*;




public class Main {
    public static void main(String[] args) throws DbException {
        Cliente cliente = new Cliente();
        Vendedor vendedor = new Vendedor();
        VendedorDao vendedorDao = new VendedorDao();
        VendaDao vendaDao = new VendaDao();

        cliente = new ClienteDao().findById(2);
        vendedor = new VendedorDao().findByCodigo(1);

        Venda venda = new Venda();
        ItemVenda itemVenda = new ItemVenda();
        Produto produto = new Produto();
        ProdutoDao produtoDao = new ProdutoDao();

        produto = produtoDao.findByID(3);

        Estoque estoque = new Estoque();
        EstoqueDao estoqueDao = new EstoqueDao();















    }
}










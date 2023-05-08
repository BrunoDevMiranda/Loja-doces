package br.com.bruno.application;


import br.com.bruno.factory.ConnectionFactory;
import br.com.bruno.factory.connection.ClienteDao;
import br.com.bruno.factory.connection.ProdutoDao;
import br.com.bruno.model.Cliente;
import br.com.bruno.model.Produto;

public class Main  {
    public static void main(String[] args)  {

        Produto produto = new Produto();
        ProdutoDao produtodao = new ProdutoDao();

        produto.setNome("Cafe");
        produto.setId(10);
        produtodao.update(produto);
    }
}









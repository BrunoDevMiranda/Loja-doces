package br.com.bruno.services;

import br.com.bruno.factory.DbException;
import br.com.bruno.factory.connection.EstoqueDao;
import br.com.bruno.factory.connection.IngredientesDao;
import br.com.bruno.model.Estoque;
import br.com.bruno.model.Ingredientes;
import br.com.bruno.model.Produto;

import static java.lang.System.*;

public class Fabricar {


    Estoque estoque = new Estoque();
    EstoqueDao estoqueDao = new EstoqueDao();
    IngredientesDao ingredientesDao = new IngredientesDao();
    Ingredientes itemAcuca = ingredientesDao.findById(13);
    Ingredientes itemCoraVermelho = ingredientesDao.findById(14);
    Ingredientes itemCoraAzul = ingredientesDao.findById(15);
    Ingredientes itemGoma = ingredientesDao.findById(16);
    Ingredientes itemEssen = ingredientesDao.findById(17);



    public void azedinhoUva(Produto produtoSelecionado, int quantidadeSelecionada) throws DbException {

        if (produtoSelecionado.getId() == 1) { // fabricar Azedinho-arco iris
            if (itemAcuca.getPeso_liquido() > quantidadeSelecionada * 50)
                if (itemCoraAzul.getPeso_liquido() > 50 * quantidadeSelecionada) {
                    // subtrai os ingredientes do estoque

                    out.println("Ingredientes Antes" + itemAcuca.getNome() + ", " + itemAcuca.getPeso_liquido());
                    ingredientesDao.update(itemAcuca.getID(), (float) (3.5 * quantidadeSelecionada));
                    out.println("Peso líquido de " + itemAcuca.getNome() + " após subtração: " + itemAcuca.getPeso_liquido());


                    out.println("Ingredientes Antes" + itemCoraAzul.getNome() + ", " + itemCoraAzul.getPeso_liquido());
                    ingredientesDao.update(itemCoraAzul.getID(), (float) (1.50 * quantidadeSelecionada));
                    out.println("Peso líquido de " + itemCoraAzul.getNome() + " após subtração: " + itemCoraAzul.getPeso_liquido());

                    // atualiza a quantidade de produtos produzidos
                    estoque.setProduto(produtoSelecionado);
                    estoque.setQuantidade(estoque.getQuantidade() + quantidadeSelecionada);
                    estoqueDao.save(estoque);
                    System.out.println("Novo produto criado no estoque");
                } else {
                    out.println("Não há ingredientes suficientes para produzir o produto.");
                }
            else {
                out.println("Não há ingredientes suficientes para produzir o produto.");
            }
        }

    }
    public void azezinhoMorango(Produto produtoSelecionado, int quantidadeSelecionada) throws DbException {

        if (produtoSelecionado.getId() == 3) { // fabricar Azedinho-arco iris
            if (itemAcuca.getPeso_liquido() > quantidadeSelecionada * 50 && itemCoraVermelho.getPeso_liquido() > 50 * quantidadeSelecionada) {
                // subtrai os ingredientes do estoque

                out.println("Ingredientes Antes" + itemAcuca.getNome() + ", " + itemAcuca.getPeso_liquido());
                ingredientesDao.update(itemAcuca.getID(), (float) (3.5 * quantidadeSelecionada));
                out.println("Peso líquido de " + itemAcuca.getNome() + " após subtração: " + itemAcuca.getPeso_liquido());


                out.println("Ingredientes Antes" + itemCoraVermelho.getNome() + ", " + itemCoraVermelho.getPeso_liquido());
                ingredientesDao.update(itemCoraVermelho.getID(), (float) (1.50 * quantidadeSelecionada));
                out.println("Peso líquido de " + itemCoraVermelho.getNome() + " após subtração: " + itemCoraVermelho.getPeso_liquido());

                // atualiza a quantidade de produtos produzidos
                estoque.setProduto(produtoSelecionado);
                estoque.setQuantidade(estoque.getQuantidade() + quantidadeSelecionada);
                estoqueDao.save(estoque);
                out.println("Novo produto criado no estoque");
            } else {
                out.println("Não há ingredientes suficientes para produzir o produto.");
            }
        }

    }
    public void azezinhoArcoIris(Produto produtoSelecionado, int quantidadeSelecionada) throws DbException {

        if (produtoSelecionado.getId() == 2) { // fabricar Azedinho-arco iris
            if (itemAcuca.getPeso_liquido() > quantidadeSelecionada * 50 && itemCoraVermelho.getPeso_liquido() > 50 * quantidadeSelecionada && itemCoraAzul.getPeso_liquido() > 50 * quantidadeSelecionada) {
                // subtrai os ingredientes do estoque

                out.println("Ingredientes Antes" + itemAcuca.getNome() + ", " + itemAcuca.getPeso_liquido());
                ingredientesDao.update(itemAcuca.getID(), (float) (3.5 * quantidadeSelecionada));
                out.println("Peso líquido de " + itemAcuca.getNome() + " após subtração: " + itemAcuca.getPeso_liquido());


                out.println("Ingredientes Antes" + itemCoraVermelho.getNome() + ", " + itemCoraVermelho.getPeso_liquido());
                ingredientesDao.update(itemCoraVermelho.getID(), (float) (1.50 * quantidadeSelecionada));
                out.println("Peso líquido de " + itemCoraVermelho.getNome() + " após subtração: " + itemCoraVermelho.getPeso_liquido());

                out.println("Ingredientes Antes" + itemCoraAzul.getNome() + ", " + itemCoraAzul.getPeso_liquido());
                ingredientesDao.update(itemCoraAzul.getID(), (float) (1.50 * quantidadeSelecionada));
                out.println("Peso líquido de " + itemCoraAzul.getNome() + " após subtração: " + itemCoraAzul.getPeso_liquido());





                // atualiza a quantidade de produtos produzidos
                estoque.setProduto(produtoSelecionado);
                estoque.setQuantidade(estoque.getQuantidade() + quantidadeSelecionada);
                estoqueDao.save(estoque);
                out.println("Novo produto criado no estoque");
            } else {
                out.println("Não há ingredientes suficientes para produzir o produto.");
            }
        }

    }
    public void alvoradaBanana(Produto produtoSelecionado, int quantidadeSelecionada) throws DbException {

        if (produtoSelecionado.getId() == 4) { // fabricar Azedinho-arco iris
            if (itemAcuca.getPeso_liquido() > quantidadeSelecionada * 50 && itemCoraVermelho.getPeso_liquido() > 50 * quantidadeSelecionada && itemGoma.getPeso_liquido() > 50+ quantidadeSelecionada) {
                // subtrai os ingredientes do estoque

                out.println("Ingredientes Antes" + itemAcuca.getNome() + ", " + itemAcuca.getPeso_liquido());
                ingredientesDao.update(itemAcuca.getID(), (float) (1.5 * quantidadeSelecionada));
                out.println("Peso líquido de " + itemAcuca.getNome() + " após subtração: " + itemAcuca.getPeso_liquido());


                out.println("Ingredientes Antes" + itemCoraVermelho.getNome() + ", " + itemCoraVermelho.getPeso_liquido());
                ingredientesDao.update(itemCoraVermelho.getID(), (float) (2.50 * quantidadeSelecionada));
                out.println("Peso líquido de " + itemCoraVermelho.getNome() + " após subtração: " + itemCoraVermelho.getPeso_liquido());

                out.println("Ingredientes Antes" + itemGoma.getNome() + ", " + itemGoma.getPeso_liquido());
                ingredientesDao.update(itemGoma.getID(), (float) (3.10 * quantidadeSelecionada));
                out.println("Peso líquido de " + itemGoma.getNome() + " após subtração: " + itemGoma.getPeso_liquido());

                // atualiza a quantidade de produtos produzidos
                estoque.setProduto(produtoSelecionado);
                estoque.setQuantidade(estoque.getQuantidade() + quantidadeSelecionada);
                estoqueDao.save(estoque);
                out.println("Novo produto criado no estoque");
            } else {
                out.println("Não há ingredientes suficientes para produzir o produto.");
            }
        }

    }
    public void coracaoMorango(Produto produtoSelecionado, int quantidadeSelecionada) throws DbException {

        if (produtoSelecionado.getId() == 5) { // fabricar Azedinho-arco iris
            if (itemAcuca.getPeso_liquido() > quantidadeSelecionada * 50 && itemCoraVermelho.getPeso_liquido() > 50 * quantidadeSelecionada) {
                // subtrai os ingredientes do estoque

                out.println("Ingredientes Antes" + itemAcuca.getNome() + ", " + itemAcuca.getPeso_liquido());
                ingredientesDao.update(itemAcuca.getID(), (float) (1.5 * quantidadeSelecionada));
                out.println("Peso líquido de " + itemAcuca.getNome() + " após subtração: " + itemAcuca.getPeso_liquido());


                out.println("Ingredientes Antes" + itemCoraVermelho.getNome() + ", " + itemCoraVermelho.getPeso_liquido());
                ingredientesDao.update(itemCoraVermelho.getID(), (float) (0.50 * quantidadeSelecionada));
                out.println("Peso líquido de " + itemCoraVermelho.getNome() + " após subtração: " + itemCoraVermelho.getPeso_liquido());

                // atualiza a quantidade de produtos produzidos
                estoque.setProduto(produtoSelecionado);
                estoque.setQuantidade(estoque.getQuantidade() + quantidadeSelecionada);
                estoqueDao.save(estoque);
                out.println("Novo produto criado no estoque");
            } else {
                out.println("Não há ingredientes suficientes para produzir o produto.");
            }
        }

    }
    public void vulcaoRosa(Produto produtoSelecionado, int quantidadeSelecionada) throws DbException {

        if (produtoSelecionado.getId() == 6) { // fabricar Azedinho-arco iris
            if (itemAcuca.getPeso_liquido() > quantidadeSelecionada * 50 && itemCoraVermelho.getPeso_liquido() > 50 * quantidadeSelecionada && itemEssen.getPeso_liquido() > 50 * quantidadeSelecionada) {
                // subtrai os ingredientes do estoque

                out.println("Ingredientes Antes" + itemAcuca.getNome() + ", " + itemAcuca.getPeso_liquido());
                ingredientesDao.update(itemAcuca.getID(), (float) (3.5 * quantidadeSelecionada));
                out.println("Peso líquido de " + itemAcuca.getNome() + " após subtração: " + itemAcuca.getPeso_liquido());


                out.println("Ingredientes Antes" + itemCoraVermelho.getNome() + ", " + itemCoraVermelho.getPeso_liquido());
                ingredientesDao.update(itemCoraVermelho.getID(), (float) (1.50 * quantidadeSelecionada));
                out.println("Peso líquido de " + itemCoraVermelho.getNome() + " após subtração: " + itemCoraVermelho.getPeso_liquido());

                out.println("Ingredientes Antes" + itemEssen.getNome() + ", " + itemEssen.getPeso_liquido());
                ingredientesDao.update(itemCoraVermelho.getID(), (float) (1.50 * quantidadeSelecionada));
                out.println("Peso líquido de " + itemEssen.getNome() + " após subtração: " + itemEssen.getPeso_liquido());

                // atualiza a quantidade de produtos produzidos
                estoque.setProduto(produtoSelecionado);
                estoque.setQuantidade(estoque.getQuantidade() + quantidadeSelecionada);
                estoqueDao.save(estoque);
                out.println("Novo produto criado no estoque");
            } else {
                out.println("Não há ingredientes suficientes para produzir o produto.");
            }
        }

    }
    public void melancia(Produto produtoSelecionado, int quantidadeSelecionada) throws DbException {

        if (produtoSelecionado.getId() == 7) { // fabricar Azedinho-arco iris
            if (itemAcuca.getPeso_liquido() > quantidadeSelecionada * 50 && itemCoraVermelho.getPeso_liquido() > 50 * quantidadeSelecionada
            && itemEssen.getPeso_liquido() > 50 * quantidadeSelecionada
                    && itemGoma.getPeso_liquido() > 50 * quantidadeSelecionada)
            {
                // subtrai os ingredientes do estoque

                out.println("Ingredientes Antes" + itemAcuca.getNome() + ", " + itemAcuca.getPeso_liquido());
                ingredientesDao.update(itemAcuca.getID(), (float) (3.5 * quantidadeSelecionada));
                out.println("Peso líquido de " + itemAcuca.getNome() + " após subtração: " + itemAcuca.getPeso_liquido());


                out.println("Ingredientes Antes" + itemCoraVermelho.getNome() + ", " + itemCoraVermelho.getPeso_liquido());
                ingredientesDao.update(itemCoraVermelho.getID(), (float) (1.50 * quantidadeSelecionada));
                out.println("Peso líquido de " + itemCoraVermelho.getNome() + " após subtração: " + itemCoraVermelho.getPeso_liquido());

                out.println("Ingredientes Antes" + itemGoma.getNome() + ", " + itemGoma.getPeso_liquido());
                ingredientesDao.update(itemCoraVermelho.getID(), (float) (1.50 * quantidadeSelecionada));
                out.println("Peso líquido de " + itemGoma.getNome() + " após subtração: " + itemGoma.getPeso_liquido());

                out.println("Ingredientes Antes" + itemEssen.getNome() + ", " + itemEssen.getPeso_liquido());
                ingredientesDao.update(itemCoraVermelho.getID(), (float) (1.50 * quantidadeSelecionada));
                out.println("Peso líquido de " + itemEssen.getNome() + " após subtração: " + itemEssen.getPeso_liquido());

                // atualiza a quantidade de produtos produzidos
                estoque.setProduto(produtoSelecionado);
                estoque.setQuantidade(estoque.getQuantidade() + quantidadeSelecionada);
                estoqueDao.save(estoque);
                out.println("Novo produto criado no estoque");
            } else {
                out.println("Não há ingredientes suficientes para produzir o produto.");
            }
        }

    }
    public void ovosDino(Produto produtoSelecionado, int quantidadeSelecionada) throws DbException {

        if (produtoSelecionado.getId() == 8) { // fabricar Azedinho-arco iris
            if (itemAcuca.getPeso_liquido() > quantidadeSelecionada * 50) {
                // subtrai os ingredientes do estoque

                out.println("Ingredientes Antes" + itemAcuca.getNome() + ", " + itemAcuca.getPeso_liquido());
                ingredientesDao.update(itemAcuca.getID(), (float) (3.5 * quantidadeSelecionada));
                out.println("Peso líquido de " + itemAcuca.getNome() + " após subtração: " + itemAcuca.getPeso_liquido());


                // atualiza a quantidade de produtos produzidos
                estoque.setProduto(produtoSelecionado);
                estoque.setQuantidade(estoque.getQuantidade() + quantidadeSelecionada);
                estoqueDao.save(estoque);
                out.println("Novo produto criado no estoque");
            } else {
                out.println("Não há ingredientes suficientes para produzir o produto.");
            }
        }

    }
    public void azedinnn(Produto produtoSelecionado, int quantidadeSelecionada) throws DbException {

        if (produtoSelecionado.getId() == 11) { // fabricar Azedinho-arco iris
            if (itemCoraVermelho.getPeso_liquido() > 50 * quantidadeSelecionada) {          // subtrai os ingredientes do estoque

                out.println("Ingredientes Antes" + itemCoraVermelho.getNome() + ", " + itemCoraVermelho.getPeso_liquido());
                ingredientesDao.update(itemCoraVermelho.getID(), (float) (1.50 * quantidadeSelecionada));
                out.println("Peso líquido de " + itemCoraVermelho.getNome() + " após subtração: " + itemCoraVermelho.getPeso_liquido());

                // atualiza a quantidade de produtos produzidos
                estoque.setProduto(produtoSelecionado);
                estoque.setQuantidade(estoque.getQuantidade() + quantidadeSelecionada);
                estoqueDao.save(estoque);
                out.println("Novo produto criado no estoque");
            } else {
                out.println("Não há ingredientes suficientes para produzir o produto.");
            }
        }

    }


}

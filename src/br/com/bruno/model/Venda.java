package br.com.bruno.model;

import java.time.LocalDate;
import java.util.List;

public class Venda {
    private int ID;
    private Cliente cliente;
    private Vendedor vendedor;
    private List<ItemVenda> itens;
    private double valorTotal;
    private LocalDate data;

    public Venda() {
    }

    public Venda(int ID, Cliente cliente, Vendedor vendedor, List<ItemVenda> itens, double valorTotal, LocalDate data) {
        this.ID = ID;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.itens = itens;
        this.valorTotal = valorTotal;
        this.data = data;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }


}





package br.com.bruno.model;

public class Ingredientes {

    private int ID;
    private String nome;
    private float peso_liquido;

    public Ingredientes() {
    }

    public Ingredientes(int ID, String nome, float peso_liquido) {
        this.ID = ID;
        this.nome = nome;
        this.peso_liquido = peso_liquido;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPeso_liquido() {
        return peso_liquido;
    }

    public void setPeso_liquido(float peso_liquido) {
        this.peso_liquido = peso_liquido;
    }
}

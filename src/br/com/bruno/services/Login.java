package br.com.bruno.services;


import java.util.Objects;

public class Login {

    private int ID;

    private String usuario;
    private String senha;
    private String tipoUsuario;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Login login)) return false;
        return Objects.equals(usuario, login.usuario) && Objects.equals(senha, login.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, senha);
    }
}
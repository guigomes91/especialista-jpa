package com.algaworks.model;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Cliente {
    @Id
    private int id;
    private String nome;

    public Cliente(){}

    public Cliente(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    @Override
    public String toString() {
        return String.format("Código %s - nome %s", this.id, this.nome);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getId() {
        return this.id;
    }
    public String getNome() {
        return this.nome;
    }
}

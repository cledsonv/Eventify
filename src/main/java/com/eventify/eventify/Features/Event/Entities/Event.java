package com.eventify.eventify.Features.Event.Entities;

import com.eventify.eventify.Features.Registration.Entities.Registration;
import com.eventify.eventify.Features.User.Entities.User;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Date data;
    private String local;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "organizador_id")
    private User organizador;

    @OneToMany(mappedBy = "evento")
    private List<Registration> inscricoes;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public User getOrganizador() {
        return organizador;
    }

    public void setOrganizador(User organizador) {
        this.organizador = organizador;
    }

    public List<Registration> getInscricoes() {
        return inscricoes;
    }

    public void setInscricoes(List<Registration> inscricoes) {
        this.inscricoes = inscricoes;
    }
}
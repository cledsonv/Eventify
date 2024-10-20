package com.eventify.eventify.Features.Registration.Entities;

import com.eventify.eventify.Features.Event.Entities.Event;
import com.eventify.eventify.Features.User.Entities.User;
import jakarta.persistence.*;


import java.util.Date;

@Entity
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Event evento;

    private Date dataInscricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Event getEvento() {
        return evento;
    }

    public void setEvento(Event evento) {
        this.evento = evento;
    }

    public Date getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(Date dataInscricao) {
        this.dataInscricao = dataInscricao;
    }
}
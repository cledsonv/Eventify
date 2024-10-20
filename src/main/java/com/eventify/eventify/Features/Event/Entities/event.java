package com.eventify.eventify.Features.Event.Entities;

import com.eventify.eventify.Features.Registration.Entities.Registration;
import com.eventify.eventify.Features.User.Entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
public class event {

    @Setter
    @Getter
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


}
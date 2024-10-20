package com.eventify.eventify.Features.Registration.Entities;

import com.eventify.eventify.Features.Event.Entities.event;
import com.eventify.eventify.Features.User.Entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
public class Registration {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private event evento;

    private Date dataInscricao;
    }
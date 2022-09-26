package com.example.server.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "user_id")
    private User user;

    public Like(int id, User user) {
        this.id = id;
        this.user = user;
    }

    public Like() {
    }
}

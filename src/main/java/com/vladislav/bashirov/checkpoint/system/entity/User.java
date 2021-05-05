package com.vladislav.bashirov.checkpoint.system.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class User {

    public User(Long keyId, @NonNull String login) {
        this.keyId = keyId;
        this.login = login;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keyId;
    @Column(unique = true)
    @NonNull
    private String login;
    @ManyToOne
    private Room room;

}

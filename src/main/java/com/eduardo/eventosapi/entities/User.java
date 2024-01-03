package com.eduardo.eventosapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false,unique = true)
    private String email;
    @Column(name = "cpf", nullable = false,unique = true)
    private String cpf;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Registration> registrations;


    public User(Long id, String name, String password, String email, String cpf) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.cpf = cpf;
        this.registrations = new ArrayList<>();
    }

    public User(long l) {
    }
}

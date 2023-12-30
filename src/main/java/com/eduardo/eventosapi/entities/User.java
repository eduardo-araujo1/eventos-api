package com.eduardo.eventosapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

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
    @OneToMany(mappedBy = "user")
    private List<Registration> registrations;


    public User(long l, String johnDoe, String password123, String mail, String number) {

    }

    public User(long l) {
    }
}

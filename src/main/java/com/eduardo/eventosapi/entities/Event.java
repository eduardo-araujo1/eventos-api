package com.eduardo.eventosapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "location")
    private String location;
    @Column(name = "date")
    private LocalDate date;
    @OneToMany(mappedBy = "event",cascade = CascadeType.ALL)
    private List<Registration> registrations;


    public Event(long l, String eventName, String description, String location, LocalDate localDate) {
    }

    public Event(long l) {
    }
}

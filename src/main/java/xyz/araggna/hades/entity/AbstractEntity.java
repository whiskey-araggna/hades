package xyz.araggna.hades.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "createddate")
    private LocalDateTime createdDate;

    @Column(name = "updateddate")
    private LocalDateTime updatedDate;


}

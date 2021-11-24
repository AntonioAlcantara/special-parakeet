package com.parakeet.lol.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "studentIdGenerator", sequenceName = "student_id_seq", allocationSize = 1)
public class Student implements Serializable {
    @Id
    @GeneratedValue(generator = "studentIdGenerator", strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "passport_number", nullable = false)
    private String passportNumber;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String address;
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    @CreatedDate
    @Column(name = "creation_date")
    private Timestamp creationDate;
    @LastModifiedDate
    @Column(name = "modification_date")
    private Timestamp modificationDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentUniversityState> studentUniversityStateList;
}

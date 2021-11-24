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
import java.util.List;

@Data
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "universityIdGenerator", sequenceName = "university_id_seq", allocationSize = 1)
public class University implements Serializable {

    @Id
    @GeneratedValue(generator = "universityIdGenerator", strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String address;
    @CreatedDate
    @Column(name = "creation_date")
    private Timestamp creationDate;
    @LastModifiedDate
    @Column(name = "modification_date")
    private Timestamp modificationDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentUniversityState> studentUniversityStateList;
}

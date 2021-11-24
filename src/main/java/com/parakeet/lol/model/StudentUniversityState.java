package com.parakeet.lol.model;

import com.parakeet.lol.dto.State;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Entity
@Table(
        name = "student_university_state",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "student_id",
                                "university_id",
                                "state",
                                "start_date",
                                "end_date"
                        }
                )
        }
)
@SequenceGenerator(
        name = "susGenerator",
        sequenceName = "student_university_state_id_seq",
        allocationSize = 1
)
@EntityListeners(AuditingEntityListener.class)
public class StudentUniversityState implements Serializable {

    @Id
    @GeneratedValue(generator = "susGenerator", strategy = GenerationType.SEQUENCE)
    private long id;
    @Enumerated(EnumType.STRING)
    private State state;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @CreatedDate
    @Column(name = "creation_date")
    private Timestamp creationDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "university_id", referencedColumnName = "id")
    private University university;
}

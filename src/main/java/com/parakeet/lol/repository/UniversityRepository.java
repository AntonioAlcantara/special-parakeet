package com.parakeet.lol.repository;

import com.parakeet.lol.model.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {

    @Query(
            value = """
                        SELECT u.id FROM university u
                        INNER JOIN student_university_state sus ON u.id = sus.university_id
                        WHERE sus.student_id = :studentId
                        AND sus.state = :state
                        AND sus.end_date IS NULL
                        LIMIT 1
                    """,
            nativeQuery = true
    )
    Long findIdCurrentStudentUniversity(long studentId, String state);
}

package com.parakeet.lol.repository;

import com.parakeet.lol.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByPassportNumber(String passportNumber);

    @Query(
            value = """
                            SELECT count(std.id) > 0
                            FROM public.student std
                            INNER JOIN public.student_university_state sus
                            ON std.id = sus.student_id
                            INNER JOIN public.university u
                            ON sus.university_id = u.id
                            WHERE std.id = :studentId
                            AND u.id = :universityId
                            AND sus.state = :state
                            AND sus.end_date is null;
                    """,
            nativeQuery = true
    )
    boolean existsByIdAndUniversityIdAndStateAndEndDateIsNull(
            long studentId,
            long universityId,
            String state
    );

    @Query(
            value = """
                            SELECT count(std.id) > 0
                            FROM public.student std
                            INNER JOIN public.student_university_state sus
                            ON std.id = sus.student_id
                            INNER JOIN public.university u
                            ON sus.university_id = u.id
                            WHERE std.id = :studentId
                            AND u.id != :universityId
                            AND sus.state = :state
                            AND sus.end_date is null;
                    """,
            nativeQuery = true
    )
    boolean existsByIdAndUniversityIdNotAndStateAndEndDateIsNull(
            long studentId,
            long universityId,
            String state
    );

    Page<Student> findAllDistinctByStudentUniversityStateList_UniversityIdOrderByCreationDate(long universityId, Pageable pageable);
}

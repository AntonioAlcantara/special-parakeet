package com.parakeet.lol.repository;

import com.parakeet.lol.model.StudentUniversityState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentUniversityStateRepository extends JpaRepository<StudentUniversityState, Long> {

    @Query(
            value = """
                            INSERT INTO public.student_university_state
                            (state, start_date, creation_date, student_id, university_id)
                            VALUES(:state, now(), now(), :studentId, :universityId)
                    """,
            nativeQuery = true
    )
    @Modifying
    void insertNewSubscription(String state, long studentId, long universityId);

    @Query(
            value = """
                            INSERT INTO public.student_university_state
                            (state, start_date, end_date, creation_date, student_id, university_id)
                            VALUES(:state, now(), now(), now(), :studentId, :universityId)
                    """,
            nativeQuery = true
    )
    @Modifying
    void insertFinalizedState(String state, long studentId, long universityId);

    @Query(
            value = """
                            UPDATE public.student_university_state
                            SET end_date = now()
                            WHERE student_id = :studentId
                            AND state = :state
                    """,
            nativeQuery = true
    )
    @Modifying
    void finalizeState(String state, long studentId);
}

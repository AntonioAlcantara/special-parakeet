package com.parakeet.lol.util;

import com.parakeet.lol.dto.State;
import com.parakeet.lol.repository.StudentRepository;
import com.parakeet.lol.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckerComponent {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UniversityRepository universityRepository;

    public boolean checkIfStudentExistsByPassportNumber(String passportNumber) {
        return studentRepository.existsByPassportNumber(passportNumber);
    }

    public boolean checkIfStudentIsSubscribedAlready(long studentId, long universityId) {
        return studentRepository.existsByIdAndUniversityIdAndStateAndEndDateIsNull(
                studentId,
                universityId,
                State.SUBSCRIBED.name()
        );
    }

    public boolean checkIfStudentIsSubscribedAlreadyToOtherUniversity(long studentId, long universityId) {
        return studentRepository.existsByIdAndUniversityIdNotAndStateAndEndDateIsNull(
                studentId,
                universityId,
                State.SUBSCRIBED.name()
        );
    }

    public boolean checkIfUniversityExists(long universityId) {
        return universityRepository.existsById(universityId);
    }
}

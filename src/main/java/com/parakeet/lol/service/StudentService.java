package com.parakeet.lol.service;

import com.parakeet.lol.dto.*;
import com.parakeet.lol.exception.StudentExistsException;
import com.parakeet.lol.exception.StudentIsAlreadySubscribedException;
import com.parakeet.lol.exception.StudentNotExistsException;
import com.parakeet.lol.exception.UniversityNotExistsException;
import com.parakeet.lol.mapper.StudentMapper;
import com.parakeet.lol.model.Student;
import com.parakeet.lol.model.StudentUniversityState;
import com.parakeet.lol.model.University;
import com.parakeet.lol.repository.StudentRepository;
import com.parakeet.lol.repository.StudentUniversityStateRepository;
import com.parakeet.lol.repository.UniversityRepository;
import com.parakeet.lol.util.CheckerComponent;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository sRepo;
    private final UniversityRepository uRepo;
    private final StudentUniversityStateRepository susRepo;
    private final StudentMapper sMapper;
    private final CheckerComponent checker;

    public StudentService(
            StudentRepository sRepo,
            UniversityRepository uRepo,
            StudentUniversityStateRepository susRepo,
            StudentMapper sMapper,
            CheckerComponent checker
    ) {
        this.sRepo = sRepo;
        this.uRepo = uRepo;
        this.susRepo = susRepo;
        this.sMapper = sMapper;
        this.checker = checker;
    }

    @Transactional
    public long insert(StudentWriterDto swd) throws UniversityNotExistsException, StudentExistsException {
        if (checker.checkIfStudentExistsByPassportNumber(swd.getPassportNumber())) {
            throw new StudentExistsException(swd.getPassportNumber());
        }
        var university = uRepo.findById(swd.getUniversityId())
                .orElseThrow(UniversityNotExistsException::new);

        var student = sMapper.map(swd);
        var sus = getStudentUniversityState(university, student);
        student.setStudentUniversityStateList(List.of(sus));
        return sRepo.saveAndFlush(student).getId();
    }

    public List<StudentSearchResultDto> findAll(Optional<Long> universityId, int pageIndex, int pageSize) {
        var studentList = universityId.isPresent() ?
                sRepo.findAllDistinctByStudentUniversityStateList_UniversityIdOrderByCreationDate(
                        universityId.get(),
                        PageRequest.of(pageIndex, pageSize)
                ) :
                sRepo.findAll(PageRequest.of(pageIndex, pageSize));
        return studentList.map(sMapper::map).getContent();
    }

    @Modifying
    @Transactional
    public boolean delete(long studentId) {
        sRepo.deleteById(studentId);
        sRepo.flush();
        return sRepo.existsById(studentId);
    }

    private StudentUniversityState getStudentUniversityState(University university, Student student) {
        var sus = new StudentUniversityState();
        sus.setStudent(student);
        sus.setUniversity(university);
        sus.setState(State.SUBSCRIBED);
        sus.setStartDate(LocalDate.now());
        return sus;
    }

    @Transactional
    public void updateUserUniversity(
            long studentId,
            long universityId
    ) throws UniversityNotExistsException, StudentIsAlreadySubscribedException {
        if (!checker.checkIfUniversityExists(universityId)) {
            throw new UniversityNotExistsException();
        } else if (checker.checkIfStudentIsSubscribedAlready(studentId, universityId)) {
            throw new StudentIsAlreadySubscribedException();
        } else if (checker.checkIfStudentIsSubscribedAlreadyToOtherUniversity(studentId, universityId)) {
            var currentUniversityId = Optional.ofNullable(
                    uRepo.findIdCurrentStudentUniversity(studentId, State.SUBSCRIBED.name())
            );
            currentUniversityId.ifPresent(id -> {
                susRepo.finalizeState(State.SUBSCRIBED.name(), studentId);
                susRepo.insertFinalizedState(State.LEFT.name(), studentId, id);
                sRepo.flush();
            });
        }

        susRepo.insertNewSubscription(State.SUBSCRIBED.name(), studentId, universityId);
        sRepo.flush();
    }

    @Modifying
    public void updateUserInfo(
            long studentId,
            StudentModifierDto studentModifierDto
    ) {
        sRepo.findById(studentId).ifPresent(student -> {
            var studentUpdated = sMapper.updateStudentFromDto(studentModifierDto, student);
            sRepo.saveAndFlush(studentUpdated);
        });
    }

    public StudentUniversityHistoryDto findStudentCollegeHistory(
            long studentId
    ) throws StudentNotExistsException {
        var student = sRepo.findById(studentId);
        if (student.isEmpty()) throw new StudentNotExistsException(studentId);
        return sMapper.entityToSuhDto(student.get());
    }
}

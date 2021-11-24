package com.parakeet.lol.service;

import com.parakeet.lol.dto.UniversityModifierDto;
import com.parakeet.lol.dto.UniversitySearchResultDto;
import com.parakeet.lol.dto.UniversityWriterDto;
import com.parakeet.lol.exception.UniversityNotExistsException;
import com.parakeet.lol.mapper.UniversityMapper;
import com.parakeet.lol.repository.UniversityRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Log4j2
@Service
public class UniversityService {

    private final UniversityRepository uRepo;
    private final UniversityMapper uMapper;

    public UniversityService(
            UniversityRepository uRepo,
            UniversityMapper uMapper
    ) {
        this.uRepo = uRepo;
        this.uMapper = uMapper;
    }

    @Transactional
    public long insert(UniversityWriterDto uwd) {
        if (uwd == null) throw new RuntimeException("University writer cannot be null");
        return uRepo.saveAndFlush(uMapper.map(uwd)).getId();
    }

    public List<UniversitySearchResultDto> findAll(int pageIndex, int pageSize) {
        return uRepo.findAll(PageRequest.of(pageIndex, pageSize))
                .map(uMapper::map)
                .getContent();
    }

    @Modifying
    @Transactional
    public boolean delete(long universityId) {
        try {
            uRepo.deleteById(universityId);
            uRepo.flush();
        } catch (Exception e) {
            log.error(e);
        }

        return uRepo.existsById(universityId);
    }

    public void updateUniversityInfo(
            long universityId,
            UniversityModifierDto universityModifierDto
    ) throws UniversityNotExistsException {
        var optionalUniversity = uRepo.findById(universityId);

        if (optionalUniversity.isEmpty()) throw new UniversityNotExistsException();

        var updatedUniversity = uMapper.updateUniversityFromDto(
                universityModifierDto,
                optionalUniversity.get()
        );
        uRepo.saveAndFlush(updatedUniversity);
    }
}

package com.clicks.crimereportapp.service;

import com.clicks.crimereportapp.dto.CrimeDto;
import com.clicks.crimereportapp.exceptions.InvalidParamsException;
import com.clicks.crimereportapp.exceptions.ResourceNotFoundException;
import com.clicks.crimereportapp.model.Crime;
import com.clicks.crimereportapp.repository.CrimeRepository;
import com.clicks.crimereportapp.utils.DtoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CrimeService {

    private final CrimeRepository crimeRepository;
    private final DtoMapper mapper;

    public void add(CrimeDto crimeDto) {

        String crimeName = crimeDto.name();

        if(crimeRepository.existsByName(crimeName))
            throw new InvalidParamsException("Crime with name " + crimeName + " already exist");

        crimeRepository.save(
                Crime.builder()
                        .code(crimeRepository.count() + 1)
                        .description(crimeDto.description())
                        .name(crimeName)
                        .build()
        );
    }

    public List<CrimeDto> get(Integer page) {

        return crimeRepository.findAll(getPageable(page))
                .map(mapper::crimeDto)
                .toList();
    }

    private PageRequest getPageable(Integer page) {
        return PageRequest.of((Objects.isNull(page) || page <= 0) ? 0 : page, 8);
    }

    public List<CrimeDto> search(Integer page, String name, String description) {
        return crimeRepository
                .findByNameContainingOrDescriptionContaining(name, description, getPageable(page))
                .stream()
                .map(mapper::crimeDto)
                .toList();
    }

    public void update(CrimeDto crime) {

        Crime thisCrime = getCrime(crime);
        thisCrime.setDescription(crime.description());
        thisCrime.setName(crime.name());
    }

    private Crime getCrime(CrimeDto crime) {
        return crimeRepository.findById(crime.id())
                .orElseThrow(() -> new ResourceNotFoundException("Crime with ID " + crime.id() + " NOT FOUND"));
    }

    public void delete(Long id) {
        crimeRepository.findById(id)
                .ifPresent(crimeRepository::delete);
    }

    public Crime getByCode(int crimeCode) {
        return getCode(crimeCode)
                .orElseThrow(() -> new ResourceNotFoundException("Crime with code " + crimeCode + " Not found"));
    }

    public Optional<Crime> getCode(int crimeCode) {
        return crimeRepository.findByCode(crimeCode);
    }
}

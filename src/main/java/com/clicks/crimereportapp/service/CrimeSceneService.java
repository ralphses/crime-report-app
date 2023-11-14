package com.clicks.crimereportapp.service;

import com.clicks.crimereportapp.dto.requests.CrimeSceneDto;
import com.clicks.crimereportapp.exceptions.InvalidParamsException;
import com.clicks.crimereportapp.exceptions.ResourceNotFoundException;
import com.clicks.crimereportapp.model.CrimeScene;
import com.clicks.crimereportapp.repository.CrimeSceneRepository;
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
public class CrimeSceneService {

    private final CrimeSceneRepository crimeSceneRepository;
    private final DtoMapper mapper;

    public CrimeScene add(CrimeSceneDto crimeSceneDto) {
        long newCode = crimeSceneRepository.count() + 1;
        if (crimeSceneRepository.existsByAreaAndLodgeNameAndRoomNo(
                crimeSceneDto.area(),
                crimeSceneDto.lodgeName(),
                crimeSceneDto.roomNumber()
        )) throw new InvalidParamsException("Crime Scene already exists");

        return crimeSceneRepository.save(
                CrimeScene.builder()
                        .code(newCode)
                        .area(crimeSceneDto.area())
                        .lodgeName(crimeSceneDto.lodgeName())
                        .roomNo(crimeSceneDto.roomNumber())
                        .build()
        );
    }

    public List<CrimeSceneDto> get(Integer page) {
        return crimeSceneRepository
                .findAll(getPageable(page))
                .map(mapper::crimeSceneDto)
                .toList();
    }

    private PageRequest getPageable(Integer page) {
        return PageRequest.of((Objects.isNull(page) || page <= 0) ? 0 : page, 8);
    }

    public List<CrimeSceneDto> search(Integer page, String area, String lodge, String room) {

        return crimeSceneRepository.findByAreaContainingOrLodgeNameContainingOrRoomNoContaining(
                        area,
                        lodge,
                        room,
                        getPageable(page))
                .stream()
                .map(mapper::crimeSceneDto)
                .toList();
    }

    public void delete(Long id) {
        crimeSceneRepository.findById(id)
                .ifPresent(crimeSceneRepository::delete);
    }


    public CrimeScene getByCode(int sceneCode) {
        return getCode(sceneCode)
                .orElseThrow(() -> new ResourceNotFoundException("Scene with code " + sceneCode + "  not found"));
    }

    public Optional<CrimeScene> getCode(int sceneCode) {
        return crimeSceneRepository.findByCode(sceneCode);
    }
}

package com.epam.spring.homework3.service.impl;

import com.epam.spring.homework3.dto.StatusDto;
import com.epam.spring.homework3.mapper.StatusMapper;
import com.epam.spring.homework3.model.Status;
import com.epam.spring.homework3.repository.StatusRepository;
import com.epam.spring.homework3.service.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    @Override
    public List<StatusDto> findAll() {
        log.info("find all statuses");
        return statusRepository.findAll()
                .stream()
                .map(StatusMapper.INSTANCE::mapStatusDto)
                .collect(Collectors.toList());
    }

    @Override
    public StatusDto findById(Long id) {
        log.info("find status with id {}", id);
        Status status = statusRepository.findById(id);
        return StatusMapper.INSTANCE.mapStatusDto(status);
    }

    @Override
    public StatusDto save(StatusDto statusDto) {
        log.info("save status");
        Status status = statusRepository.save(StatusMapper.INSTANCE.mapStatus(statusDto));
        return StatusMapper.INSTANCE.mapStatusDto(status);
    }

    @Override
    public StatusDto update(Long id, StatusDto statusDto) {
        log.info("update status with id {}", id);
        Status status = statusRepository.update(id,
                StatusMapper.INSTANCE.mapStatus(statusDto));
        return StatusMapper.INSTANCE.mapStatusDto(status);
    }

    @Override
    public void deleteById(Long id) {
        log.info("delete status with id {}", id);
        statusRepository.deleteById(id);
    }
}

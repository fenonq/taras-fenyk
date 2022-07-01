package com.epam.spring.homework3.service.impl;

import com.epam.spring.homework3.dto.StatusDto;
import com.epam.spring.homework3.model.Status;
import com.epam.spring.homework3.repository.StatusRepository;
import com.epam.spring.homework3.service.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    @Override
    public List<StatusDto> findAll() {
        log.info("find all statuses");

        List<Status> statuses = statusRepository.findAll();
        List<StatusDto> toReturn = new ArrayList<>();

        StatusDto target;
        for (Status source : statuses) {
            target = new StatusDto();
            BeanUtils.copyProperties(source, target);
            toReturn.add(target);
        }
        return toReturn;
    }

    @Override
    public StatusDto findById(Long id) {
        log.info("find status with id {}", id);
        Status source = statusRepository.findById(id);
        StatusDto target = new StatusDto();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    @Override
    public StatusDto save(StatusDto statusDto) {
        log.info("save status with id {}", statusDto.getId());
        Status status = new Status();
        BeanUtils.copyProperties(statusDto, status);
        status = statusRepository.save(status);
        BeanUtils.copyProperties(status, statusDto);
        return statusDto;
    }

    @Override
    public StatusDto update(Long id, StatusDto statusDto) {
        log.info("update status with id {}", id);
        Status status = new Status();
        BeanUtils.copyProperties(statusDto, status);
        status = statusRepository.update(id, status);
        BeanUtils.copyProperties(status, statusDto);
        return statusDto;
    }

    @Override
    public void deleteById(Long id) {
        log.info("delete status with id {}", id);
        statusRepository.deleteById(id);
    }
}

package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.api.StatusApi;
import com.epam.spring.homework3.dto.StatusDto;
import com.epam.spring.homework3.service.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatusController implements StatusApi {

    private final StatusService statusService;

    @Override
    public List<StatusDto> getAllStatuses() {
        log.info("find all statuses");
        return statusService.findAll();
    }

    @Override
    public StatusDto getStatus(Long id) {
        log.info("find status with id {}", id);
        return statusService.findById(id);
    }

    @Override
    public StatusDto createStatus(StatusDto statusDto) {
        log.info("save status");
        return statusService.save(statusDto);
    }

    @Override
    public StatusDto updateStatus(Long id, StatusDto statusDto) {
        log.info("update status with id {}", id);
        return statusService.update(id, statusDto);
    }

    @Override
    public ResponseEntity<Void> deleteStatus(Long id) {
        log.info("delete status with id {}", id);
        statusService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

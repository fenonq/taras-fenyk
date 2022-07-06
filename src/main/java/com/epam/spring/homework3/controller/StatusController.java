package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.dto.StatusDto;
import com.epam.spring.homework3.service.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/statuses")
    public List<StatusDto> getAllStatuses() {
        log.info("find all statuses");
        return statusService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/status/{id}")
    public StatusDto getStatus(@PathVariable Long id) {
        log.info("find status with id {}", id);
        return statusService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/status")
    public StatusDto createStatus(@RequestBody StatusDto statusDto) {
        log.info("save status");
        return statusService.save(statusDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/status/{id}")
    public StatusDto updateStatus(@PathVariable Long id,
                                  @RequestBody StatusDto statusDto) {
        log.info("update status with id {}", id);
        return statusService.update(id, statusDto);
    }

    @DeleteMapping(value = "/status/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable Long id) {
        log.info("delete status with id {}", id);
        statusService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

package com.epam.spring.homework3.controller;

import com.epam.spring.homework3.api.StatusApi;
import com.epam.spring.homework3.controller.assembler.StatusAssembler;
import com.epam.spring.homework3.controller.model.StatusModel;
import com.epam.spring.homework3.dto.StatusDto;
import com.epam.spring.homework3.service.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatusController implements StatusApi {

    private final StatusService statusService;
    private final StatusAssembler statusAssembler;

    @Override
    public List<StatusModel> getAllStatuses() {
        log.info("find all statuses");
        List<StatusDto> outStatusDtoList = statusService.findAll();
        return outStatusDtoList.stream().map(statusAssembler::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public StatusModel getStatus(Long id) {
        log.info("find status with id {}", id);
        StatusDto outStatusDto = statusService.findById(id);
        return statusAssembler.toModel(outStatusDto);
    }

    @Override
    public StatusModel createStatus(StatusDto statusDto) {
        log.info("save status");
        StatusDto outStatusDto = statusService.save(statusDto);
        return statusAssembler.toModel(outStatusDto);
    }

    @Override
    public StatusModel updateStatus(Long id, StatusDto statusDto) {
        log.info("update status with id {}", id);
        StatusDto outStatusDto = statusService.update(id, statusDto);
        return statusAssembler.toModel(outStatusDto);
    }

    @Override
    public ResponseEntity<Void> deleteStatus(Long id) {
        log.info("delete status with id {}", id);
        statusService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

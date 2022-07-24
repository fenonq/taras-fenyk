package com.epam.spring.homework3.service.impl;

import com.epam.spring.homework3.dto.StatusDto;
import com.epam.spring.homework3.exception.EntityNotFoundException;
import com.epam.spring.homework3.model.Status;
import com.epam.spring.homework3.repository.StatusRepository;
import com.epam.spring.homework3.test.util.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class StatusServiceImplTest {

    @InjectMocks
    private StatusServiceImpl statusService;

    @Mock
    private StatusRepository statusRepository;

    @Test
    void findAllTest() {
        List<Status> statuses = List.of(
                TestDataUtil.createStatus(),
                TestDataUtil.createStatus()
        );
        when(statusRepository.findAll()).thenReturn(statuses);

        List<StatusDto> returnedStatuses = statusService.findAll();

        verify(statusRepository).findAll();
        assertThat(returnedStatuses, hasSize(statuses.size()));
    }

    @Test
    void findByIdTest() {
        Status status = TestDataUtil.createStatus();
        when(statusRepository.findById(anyLong())).thenReturn(Optional.of(status));

        StatusDto returnedStatus = statusService.findById(anyLong());

        verify(statusRepository).findById(anyLong());
        assertThat(returnedStatus, allOf(
                hasProperty("id", equalTo(status.getId())),
                hasProperty("name", equalTo(status.getName()))
        ));
    }

    @Test
    void findByIdNotFoundTest() {
        when(statusRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> statusService.findById(anyLong()));
    }

    @Test
    void saveTest() {
        Status status = TestDataUtil.createStatus();
        StatusDto statusDto = TestDataUtil.createStatusDto();
        when(statusRepository.save(any())).thenReturn(status);

        StatusDto returnedStatus = statusService.save(statusDto);

        assertThat(returnedStatus, allOf(
                hasProperty("id", equalTo(statusDto.getId())),
                hasProperty("name", equalTo(statusDto.getName()))
        ));
    }

    @Test
    void updateTest() {
        Status status = TestDataUtil.createStatus();
        StatusDto statusDto = TestDataUtil.createStatusDto();
        when(statusRepository.findById(anyLong())).thenReturn(Optional.of(status));
        when(statusRepository.save(any())).thenReturn(status);

        StatusDto returnedStatus = statusService.update(status.getId(), statusDto);

        assertThat(returnedStatus, allOf(
                hasProperty("id", equalTo(statusDto.getId())),
                hasProperty("name", equalTo(statusDto.getName()))
        ));
    }

    @Test
    void updateNotFoundTest() {
        StatusDto statusDto = TestDataUtil.createStatusDto();

        when(statusRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> statusService.update(statusDto.getId(), statusDto));
    }

    @Test
    void deleteByIdTest() {
        Status status = TestDataUtil.createStatus();
        when(statusRepository.findById(anyLong())).thenReturn(Optional.of(status));

        statusService.deleteById(anyLong());

        verify(statusRepository).delete(status);
    }

    @Test
    void deleteByIdNotFoundTest() {
        when(statusRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> statusService.deleteById(anyLong()));
    }

}

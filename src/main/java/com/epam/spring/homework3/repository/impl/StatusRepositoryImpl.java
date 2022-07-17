package com.epam.spring.homework3.repository.impl;

import com.epam.spring.homework3.exception.EntityNotFoundException;
import com.epam.spring.homework3.model.Status;
import com.epam.spring.homework3.repository.StatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class StatusRepositoryImpl implements StatusRepository {

    private final List<Status> list = new ArrayList<>(
            Arrays
                    .asList(
                            Status.builder().id(1L).name("New").build(),
                            Status.builder().id(2L).name("Accepted").build(),
                            Status.builder().id(3L).name("Cooking").build(),
                            Status.builder().id(4L).name("Delivering").build(),
                            Status.builder().id(5L).name("Done").build(),
                            Status.builder().id(6L).name("Canceled").build()
                    ));

    @Override
    public List<Status> findAll() {
        log.info("find all statuses");
        return new ArrayList<>(list);
    }

    @Override
    public Status findById(Long id) {
        log.info("find status with id {}", id);
        return list.stream()
                .filter(status -> status.getId().equals(id))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Status save(Status status) {
        status.setId((long) (this.list.size() + 1));
        log.info("save status with id {}", status.getId());
        list.add(status);
        return status;
    }

    @Override
    public Status update(Long id, Status status) {
        log.info("update status with id {}", id);
        boolean isDeleted = list.removeIf(s -> s.getId().equals(id));
        if (isDeleted) {
            list.add(status);
        } else {
            throw new EntityNotFoundException();
        }
        return status;
    }

    @Override
    public void deleteById(Long id) {
        log.info("delete status with id {}", id);
        list.removeIf(status -> status.getId().equals(id));
    }

    @Override
    public Status findByName(String name) {
        log.info("find status with name {}", name);
        return list.stream()
                .filter(status -> status.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Status is not found!"));
    }
}

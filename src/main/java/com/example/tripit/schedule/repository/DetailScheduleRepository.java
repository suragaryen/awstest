package com.example.tripit.schedule.repository;

import com.example.tripit.schedule.entity.DetailScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailScheduleRepository extends JpaRepository<DetailScheduleEntity, Long> {

    @Override
    DetailScheduleEntity save(DetailScheduleEntity detailScheduleEntity);

    List<DetailScheduleEntity> findByScheduleId(Long scheduleId);
}

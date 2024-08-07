package com.example.tripit.schedule.repository;

import com.example.tripit.schedule.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    @Override
    ScheduleEntity save(ScheduleEntity scheduleEntity);

    @Query("SELECT s FROM ScheduleEntity s WHERE s.user.id = :userId ORDER BY s.scheduleId DESC")
    List<ScheduleEntity> findByUserId(long userId);

    @Query("SELECT s FROM ScheduleEntity s WHERE s.user.id = :userId AND s.scheduleId = :scheduleId")
    Optional<ScheduleEntity> findByUserIdAndScheduleId(@Param("userId")long userId, @Param("scheduleId") Long scheduleId);

    @Query("SELECT s.scheduleTitle FROM ScheduleEntity s WHERE s.user.id = :userId")
    List<String> findTitlesByUserId(@Param("userId") long userId);

    @Query("SELECT s.scheduleId FROM ScheduleEntity s WHERE s.user.id = :userId")
    List<String> findScheduleIdByUserId(@Param("userId") long userId);


}


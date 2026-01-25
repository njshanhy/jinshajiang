package com.example.leave.repository;

import com.example.leave.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
    List<Leave> findByUserId(String userId);
    List<Leave> findByProcessInstanceId(String processInstanceId);
}
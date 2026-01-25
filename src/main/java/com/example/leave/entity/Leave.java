package com.example.leave.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_leave")
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "leave_type")
    private String leaveType;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "reason")
    private String reason;

    @Column(name = "apply_time")
    private LocalDateTime applyTime;

    @Column(name = "process_instance_id")
    private String processInstanceId;

    @Column(name = "status")
    private String status; // PENDING, APPROVED, REJECTED

    // 构造函数
    public Leave() {}

    public Leave(String userId, String userName, String leaveType, LocalDateTime startTime, 
                 LocalDateTime endTime, String reason) {
        this.userId = userId;
        this.userName = userName;
        this.leaveType = leaveType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
        this.applyTime = LocalDateTime.now();
        this.status = "PENDING";
    }

    // Getter 和 Setter 方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(LocalDateTime applyTime) {
        this.applyTime = applyTime;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
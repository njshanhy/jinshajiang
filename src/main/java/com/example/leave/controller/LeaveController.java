package com.example.leave.controller;

import com.example.leave.dto.ApprovalRequestDTO;
import com.example.leave.dto.LeaveRequestDTO;
import com.example.leave.entity.Leave;
import com.example.leave.service.LeaveService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    /**
     * 提交请假申请
     */
    @PostMapping("/submit")
    public Leave submitLeave(@RequestBody LeaveRequestDTO leaveRequestDTO) {
        return leaveService.submitLeave(leaveRequestDTO);
    }

    /**
     * 获取用户的待办任务
     */
    @GetMapping("/tasks/{userId}")
    public List<Task> getTasksByUser(@PathVariable String userId) {
        return leaveService.getTasksByUser(userId);
    }

    /**
     * 审批请假申请
     */
    @PostMapping("/approve")
    public void approveLeave(@RequestBody ApprovalRequestDTO approvalRequestDTO) {
        leaveService.approveLeave(approvalRequestDTO);
    }

    /**
     * 获取所有请假申请
     */
    @GetMapping("/all")
    public List<Leave> getAllLeaves() {
        return leaveService.getAllLeaves();
    }

    /**
     * 根据用户ID获取请假申请
     */
    @GetMapping("/user/{userId}")
    public List<Leave> getLeavesByUserId(@PathVariable String userId) {
        return leaveService.getLeavesByUserId(userId);
    }

    /**
     * 获取流程定义列表
     */
    @GetMapping("/process-definitions")
    public List<Map<String, Object>> getProcessDefinitions() {
        // 这里可以实现获取流程定义的逻辑
        return null;
    }
}
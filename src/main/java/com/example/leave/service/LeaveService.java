package com.example.leave.service;

import com.example.leave.dto.ApprovalRequestDTO;
import com.example.leave.dto.LeaveRequestDTO;
import com.example.leave.entity.Leave;
import com.example.leave.repository.LeaveRepository;
import org.flowable.engine.*;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LeaveService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private LeaveRepository leaveRepository;

    /**
     * 提交请假申请
     */
    public Leave submitLeave(LeaveRequestDTO leaveRequestDTO) {
        // 保存请假信息到数据库
        Leave leave = new Leave(
                leaveRequestDTO.getUserId(),
                leaveRequestDTO.getUserName(),
                leaveRequestDTO.getLeaveType(),
                leaveRequestDTO.getStartTime(),
                leaveRequestDTO.getEndTime(),
                leaveRequestDTO.getReason()
        );
        leave = leaveRepository.save(leave);

        // 启动流程实例
        Map<String, Object> variables = new HashMap<>();
        variables.put("userId", leave.getUserId());
        variables.put("userName", leave.getUserName());
        variables.put("leaveType", leave.getLeaveType());
        variables.put("startTime", leave.getStartTime());
        variables.put("endTime", leave.getEndTime());
        variables.put("reason", leave.getReason());

        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey("leaveProcess", variables);

        // 更新请假记录中的流程实例ID
        leave.setProcessInstanceId(processInstance.getId());
        leave = leaveRepository.save(leave);

        return leave;
    }

    /**
     * 获取用户的待办任务
     */
    public List<Task> getTasksByUser(String userId) {
        return taskService.createTaskQuery()
                .taskCandidateOrAssigned(userId)
                .orderByTaskCreateTime()
                .desc()
                .list();
    }

    /**
     * 审批请假申请
     */
    public void approveLeave(ApprovalRequestDTO approvalRequestDTO) {
        Task task = taskService.createTaskQuery()
                .taskId(approvalRequestDTO.getTaskId())
                .singleResult();

        if (task != null) {
            // 设置任务处理人
            if (approvalRequestDTO.getAssignee() != null) {
                taskService.setAssignee(task.getId(), approvalRequestDTO.getAssignee());
            }

            // 添加评论
            if (approvalRequestDTO.getComment() != null) {
                taskService.addComment(task.getId(), task.getProcessInstanceId(), approvalRequestDTO.getComment());
            }

            // 设置流程变量
            Map<String, Object> variables = new HashMap<>();
            variables.put("approved", approvalRequestDTO.getApproved());
            variables.put("approver", approvalRequestDTO.getAssignee());

            // 完成任务
            taskService.complete(task.getId(), variables);

            // 更新请假状态
            updateLeaveStatus(task.getProcessInstanceId(), approvalRequestDTO.getApproved());
        }
    }

    /**
     * 根据流程实例ID更新请假状态
     */
    private void updateLeaveStatus(String processInstanceId, Boolean approved) {
        Leave leave = leaveRepository.findByProcessInstanceId(processInstanceId).stream().findFirst().orElse(null);
        if (leave != null) {
            if (approved != null && approved) {
                leave.setStatus("APPROVED");
            } else {
                leave.setStatus("REJECTED");
            }
            leaveRepository.save(leave);
        }
    }

    /**
     * 获取所有请假申请
     */
    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    /**
     * 根据用户ID获取请假申请
     */
    public List<Leave> getLeavesByUserId(String userId) {
        return leaveRepository.findByUserId(userId);
    }

    /**
     * 根据流程实例ID获取请假详情
     */
    public Leave getLeaveByProcessInstanceId(String processInstanceId) {
        List<Leave> leaves = leaveRepository.findByProcessInstanceId(processInstanceId);
        return leaves.isEmpty() ? null : leaves.get(0);
    }
}
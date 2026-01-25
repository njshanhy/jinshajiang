package com.example.leave;

import com.example.leave.dto.LeaveRequestDTO;
import com.example.leave.entity.Leave;
import com.example.leave.service.LeaveService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class LeaveApplicationTests {

    @Autowired
    private LeaveService leaveService;

    @Test
    public void testSubmitLeave() {
        LeaveRequestDTO request = new LeaveRequestDTO();
        request.setUserId("user001");
        request.setUserName("张三");
        request.setLeaveType("年假");
        request.setStartTime(LocalDateTime.now().plusDays(1));
        request.setEndTime(LocalDateTime.now().plusDays(3));
        request.setReason("个人事务");

        Leave leave = leaveService.submitLeave(request);
        assert leave != null;
        assert "PENDING".equals(leave.getStatus());
        System.out.println("请假申请提交成功，ID: " + leave.getId());
    }
}
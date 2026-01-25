package com.example.leave.service;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ProcessManagementService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    /**
     * 初始化流程定义
     */
    @PostConstruct
    public void initProcessDefinition() {
        // 检查是否已经部署了流程定义
        long processCount = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("leaveProcess")
                .count();

        if (processCount == 0) {
            // 部署流程定义
            Deployment deployment = repositoryService.createDeployment()
                    .addClasspathResource("processes/leaveProcess.bpmn20.xml")
                    .name("请假审批流程")
                    .deploy();

            System.out.println("流程定义已部署，部署ID：" + deployment.getId());
        }
    }

    /**
     * 重新部署流程定义
     */
    public void redeployProcessDefinition() {
        // 删除已有的流程定义
        repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("leaveProcess")
                .list()
                .forEach(processDefinition -> {
                    repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true);
                });

        // 重新部署
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("processes/leaveProcess.bpmn20.xml")
                .name("请假审批流程")
                .deploy();

        System.out.println("流程定义已重新部署，部署ID：" + deployment.getId());
    }
}
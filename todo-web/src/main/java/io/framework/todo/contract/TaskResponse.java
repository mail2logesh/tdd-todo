package io.framework.todo.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class TaskResponse {

    @JsonProperty
    private String taskID;

    @JsonProperty
    private String taskName;

    @JsonProperty
    private String taskStatus;

    public TaskResponse(){
    }
    
    public TaskResponse(String userId, String userName, String status) {
        this.taskID = userId;
        this.taskName = userName;
        this.taskStatus = status;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskID() { return taskID; }
}

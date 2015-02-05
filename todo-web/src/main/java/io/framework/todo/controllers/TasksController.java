/*
 * author : Lokesh Kumaran
 */
package io.framework.todo.controllers;

import io.framework.todo.constants.RESTURIConstants;
import io.framework.todo.contract.Task;
import io.framework.todo.contract.TaskResponse;
import io.framework.todo.service.TodoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * The Class TasksController.
 */
@Controller
public class TasksController {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(TasksController.class);
	
	 /** The todo service. */
 	@Autowired
	 private TodoService todoService;
	
    /**
     * Gets the task.
     *
     * @param taskId the task id
     * @return the task
     */
    @RequestMapping(method = RequestMethod.GET, value = RESTURIConstants.GET_TASK)
    public @ResponseBody TaskResponse getTask(@PathVariable String taskId) {
    	Task task = todoService.getTask(Integer.valueOf(taskId));
    	return new TaskResponse(String.valueOf(task.getTaskID()),task.getTaskName(),task.getTaskStatus());
    }
     
    /**
     * Adds the task.
     *
     * @param task the task
     * @return the task response
     */
    @RequestMapping(method = RequestMethod.POST,value=RESTURIConstants.ADD_TASK)
    public @ResponseBody TaskResponse addTask(@RequestBody Task task) {
    	Task newTask = todoService.addTask(task);
    	return new TaskResponse(String.valueOf(newTask.getTaskID()),newTask.getTaskName(),newTask.getTaskStatus());
    }

    /**
     * Update task.
     *
     * @param task the task
     * @return the task response
     */
    @RequestMapping(method = RequestMethod.POST,value=RESTURIConstants.UPDATE_TASK)
    public @ResponseBody TaskResponse updateTask(@RequestBody Task task) {
    	Task updateTask = todoService.updateTask(task);
    	return new TaskResponse(String.valueOf(updateTask.getTaskID()),updateTask.getTaskName(),updateTask.getTaskStatus());   
    }
    
    /**
     * Delete task.
     *
     * @param taskId the task id
     * @return the string
     */
    @RequestMapping(method = RequestMethod.DELETE,value=RESTURIConstants.DELETE_TASK)
    public @ResponseBody String deleteTask(@PathVariable Integer taskId) {
    	todoService.deleteTask(taskId);
        return "TaskID - "+taskId+ " has been deleted Successfully";
    }
    
    /**
     * Removes the task.
     *
     * @param taskId the task id
     * @return the string
     */
    @RequestMapping(method = RequestMethod.DELETE,value=RESTURIConstants.REMOVE_TASK)
    public @ResponseBody String removeTask(@PathVariable Integer taskId) {
    	todoService.removeTask(taskId);
        return "TaskID - "+taskId+ " has been removed Successfully";
    }
    
}

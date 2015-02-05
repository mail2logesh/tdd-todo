/*
 * author : Lokesh Kumaran
 */
package io.framework.todo.service;

import io.framework.todo.contract.Task;


/**
 * The Interface TodoService.
 */
public interface TodoService {
	
	/**
	 * Gets the task.
	 *
	 * @param taskId the task id
	 * @return the task
	 */
	Task getTask(Integer taskId) ;
	
	/**
	 * Adds the task.
	 *
	 * @param task the task
	 * @return the task
	 */
	Task addTask(Task task);
	
	/**
	 * Update task.
	 *
	 * @param task the task
	 * @return the task
	 */
	Task updateTask(Task task);
	
	/**
	 * Delete task.
	 *
	 * @param taskId the task id
	 */
	void deleteTask(Integer taskId);
	
	/**
	 * Removes the task.
	 *
	 * @param taskId the task id
	 */
	void removeTask(Integer taskId);
}

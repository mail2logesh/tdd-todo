/*
 * author : Lokesh Kumaran
 */
package io.framework.todo.service.impl;

import io.framework.todo.contract.Task;
import io.framework.todo.service.TodoService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;


/**
 * The Class TodoServiceImpl.
 */
@Service("todoService")
public class TodoServiceImpl implements TodoService {

	/** The task map. */
	Map<Integer, Task> taskMap = new HashMap<Integer, Task>();
	
	/** The tmp task map. */
	Map<Integer, Task> tmpTaskMap = new HashMap<Integer, Task>();
	
	/** The task no. */
	private static Integer taskNo = 0;

	/* (non-Javadoc)
	 * @see io.framework.todo.service.TodoService#getTask(java.lang.Integer)
	 */
	public Task getTask(Integer taskId) {
		return taskMap.get(taskId);
	}

	/* (non-Javadoc)
	 * @see io.framework.todo.service.TodoService#addTask(io.framework.todo.contract.Task)
	 */
	public Task addTask(Task task) {
		taskNo++;
		task.setTaskID(taskNo);
		taskMap.put(taskNo, task);
		return task;
	}

	/* (non-Javadoc)
	 * @see io.framework.todo.service.TodoService#updateTask(io.framework.todo.contract.Task)
	 */
	public Task updateTask(Task task) {
		taskMap.put(task.getTaskID(), task);
		return taskMap.get(task.getTaskID());
	}

	/* (non-Javadoc)
	 * @see io.framework.todo.service.TodoService#deleteTask(java.lang.Integer)
	 */
	public void deleteTask(Integer taskId) {
		taskMap.remove(taskId);
	}

	/* (non-Javadoc)
	 * @see io.framework.todo.service.TodoService#removeTask(java.lang.Integer)
	 */
	public void removeTask(Integer taskId) {
		Task remTask = taskMap.get(taskId);
		// Move it to the temp Map so that it's not permanently deleted.
		tmpTaskMap.put(taskId, remTask);
		taskMap.remove(taskId);
	}

}

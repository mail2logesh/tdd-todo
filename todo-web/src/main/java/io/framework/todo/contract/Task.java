/*
 * author : Lokesh Kumaran
 */
package io.framework.todo.contract;

/**
 * The Class Task.
 */
public class Task {
	    
    	/** The task id. */
    	private Integer taskID;

	    /** The task name. */
    	private String taskName;

	    /** The task status. */
    	private String taskStatus;

		/**
		 * Gets the task id.
		 *
		 * @return the task id
		 */
		public Integer getTaskID() {
			return taskID;
		}

		/**
		 * Sets the task id.
		 *
		 * @param taskID the new task id
		 */
		public void setTaskID(Integer taskID) {
			this.taskID = taskID;
		}

		/**
		 * Gets the task name.
		 *
		 * @return the task name
		 */
		public String getTaskName() {
			return taskName;
		}

		/**
		 * Sets the task name.
		 *
		 * @param taskName the new task name
		 */
		public void setTaskName(String taskName) {
			this.taskName = taskName;
		}

		/**
		 * Gets the task status.
		 *
		 * @return the task status
		 */
		public String getTaskStatus() {
			return taskStatus;
		}

		/**
		 * Sets the task status.
		 *
		 * @param taskStatus the new task status
		 */
		public void setTaskStatus(String taskStatus) {
			this.taskStatus = taskStatus;
		}

}

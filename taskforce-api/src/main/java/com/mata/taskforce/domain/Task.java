package com.mata.taskforce.domain;

public class Task {
	
	private Integer taskId;
	private Integer userId;
	private String title;
	private String description;
	private String dateOfCreation;
	private TaskStatus status;
	
	public Task(Integer taskId, Integer userId, String title, String description) {
		this(taskId, userId, title, description, "No Date", TaskStatus.valueOf(1));
	}
	
	public Task(Integer taskId, Integer userId, String title, String description, String dateOfCreation, TaskStatus status) {
		super();
		this.taskId = taskId;
		this.userId = userId;
		this.title = title;
		this.description = description;
		this.dateOfCreation = dateOfCreation;
		this.status = status;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDateOfCreation() {
		return dateOfCreation;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Task [taskId = " + taskId + ", userId = " + userId + ", title = " 
				+ title + ", description = " + description + ", dateOfCreation = "
				+ dateOfCreation + ", status = " + status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateOfCreation == null) ? 0 : dateOfCreation.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (dateOfCreation == null) {
			if (other.dateOfCreation != null)
				return false;
		} else if (!dateOfCreation.equals(other.dateOfCreation))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (status != other.status)
			return false;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}

package com.mata.taskforce.services;

import java.util.List;

import com.mata.taskforce.model.Task;

public interface TaskService {
	
	List<Task> fetchAllUserTasks(Integer userId);
	
	Task fetchTaskById(Integer userId, Integer taskId);
	
	Task addTask(Integer userId, Task task);
	
	Task updateTask(Integer userId, Integer taskId, Task updatedTask);
	
	void removeTask(Integer userId, Integer taskId);
}
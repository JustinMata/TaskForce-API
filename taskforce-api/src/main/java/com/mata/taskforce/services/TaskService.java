package com.mata.taskforce.services;

import java.util.List;

import com.mata.taskforce.domain.Task;
import com.mata.taskforce.exceptions.TfBadRequestException;
import com.mata.taskforce.exceptions.TfResourceNotFoundException;

public interface TaskService {
	
	List<Task> fetchallTasks(Integer userId);
	
	Task fetchTaskById(Integer userId, Integer taskId) throws TfResourceNotFoundException;
	
	Task addTask(Integer userId, String title, String description) throws TfBadRequestException;
	
	void updateTask(Integer userId, Integer taskId, Task task) throws TfBadRequestException;
	
	void removeTask(Integer userId, Integer taskId) throws TfResourceNotFoundException;
}
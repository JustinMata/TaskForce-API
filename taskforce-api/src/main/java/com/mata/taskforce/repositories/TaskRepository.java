package com.mata.taskforce.repositories;

import java.util.List;

import com.mata.taskforce.domain.Task;
import com.mata.taskforce.exceptions.TfBadRequestException;
import com.mata.taskforce.exceptions.TfResourceNotFoundException;

public interface TaskRepository {
	
	List<Task> findAll(Integer userId) throws TfResourceNotFoundException;
	
	Task findById(Integer userId, Integer taskId) throws TfResourceNotFoundException;
	
	Integer create(Integer userId, String title, String description) throws TfBadRequestException;
	
	void update(Integer userId, Integer taskId, Task task) throws TfBadRequestException;
	
	void removeById(Integer userId, Integer taskId) throws TfResourceNotFoundException;
}

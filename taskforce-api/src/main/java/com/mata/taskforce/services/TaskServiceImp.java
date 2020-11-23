package com.mata.taskforce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mata.taskforce.domain.Task;
import com.mata.taskforce.exceptions.TfBadRequestException;
import com.mata.taskforce.exceptions.TfResourceNotFoundException;
import com.mata.taskforce.repositories.TaskRepository;

@Service
@Transactional
public class TaskServiceImp implements TaskService {
	
	@Autowired
	TaskRepository taskRepo;

	@Override
	public List<Task> fetchallTasks(Integer userId) {
		return taskRepo.findAll(userId);
	}

	@Override
	public Task fetchTaskById(Integer userId, Integer taskId) throws TfResourceNotFoundException {
		return taskRepo.findById(userId, taskId);
	}

	@Override
	public Task addTask(Integer userId, String title, String description) throws TfBadRequestException {
		int taskId = taskRepo.create(userId, title, description);
		return taskRepo.findById(userId, taskId);
	}

	@Override
	public void updateTask(Integer userId, Integer taskId, Task task)
			throws TfBadRequestException {
		taskRepo.update(userId, taskId, task);
		
	}

	@Override
	public void removeTask(Integer userId, Integer taskId) throws TfResourceNotFoundException {
		this.fetchTaskById(userId, taskId);
		taskRepo.removeById(userId, taskId);
	}
}

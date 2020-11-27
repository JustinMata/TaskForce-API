package com.mata.taskforce.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mata.taskforce.exceptions.TfAuthException;
import com.mata.taskforce.exceptions.TfBadRequestException;
import com.mata.taskforce.exceptions.TfResourceNotFoundException;
import com.mata.taskforce.model.Task;
import com.mata.taskforce.repositories.TaskRepository;
import com.mata.taskforce.repositories.UserRepository;

@Service
@Transactional
public class TaskServiceImp implements TaskService {
	
	@Autowired
	TaskRepository taskRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Task> fetchAllUserTasks(Integer userId) {
		if (userId == null) throw new TfBadRequestException("All fields must be provided");
		if (!userRepo.existsById(userId)) throw new TfResourceNotFoundException("No account associated with this id");
		return taskRepo.findByUserId(userId);
	}

	@Override
	public Task fetchTaskById(Integer userId, Integer taskId) {
		Task task = taskRepo.findById(taskId).orElseThrow(() -> new TfResourceNotFoundException("Task not found"));
		if (task.getUserId() != userId) throw new TfAuthException("Task does not belong to user");
		return task;
	}

	@Override
	public Task addTask(Integer userId, Task task) {
		if (task.getTitle() == null || task.getDescription() == null) 
			throw new TfBadRequestException("All fields must be provided");
		if (!userRepo.existsById(userId)) throw new TfResourceNotFoundException("No account associated with this id");
		task.setUserId(userId);
		Task updatedTask = taskRepo.save(task);
		entityManager.refresh(updatedTask);
		return updatedTask;
	}

	@Override
	public Task updateTask(Integer userId, Integer taskId, Task updatedTask) {
		Task task = taskRepo.findById(taskId).orElseThrow(() -> new TfResourceNotFoundException("Task not found"));
		if (task.getUserId() != userId) throw new TfAuthException("Account unauthorized to update this task");
		if (updatedTask.getTitle() != null) task.setTitle(updatedTask.getTitle());
		if (updatedTask.getDescription() != null) task.setDescription(updatedTask.getDescription());
		if (updatedTask.getStatus() != null) task.setStatus(updatedTask.getStatus());
		return taskRepo.save(task);
	}

	@Override
	public void removeTask(Integer userId, Integer taskId) {
		Task task = taskRepo.findById(taskId).orElseThrow(() -> new TfResourceNotFoundException("Task not found"));
		if (task.getUserId() != userId) throw new TfAuthException("Account unauthorized to delete this task");
		taskRepo.delete(task);
	}
}
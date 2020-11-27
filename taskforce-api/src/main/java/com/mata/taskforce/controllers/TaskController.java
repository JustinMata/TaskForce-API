package com.mata.taskforce.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mata.taskforce.model.Task;
import com.mata.taskforce.services.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
	
	@Autowired
	TaskService taskService;
	
	@GetMapping("")
	public ResponseEntity<List<Task>> getAllUserTasks(HttpServletRequest request) {
		int userId = (Integer) request.getAttribute("userId");
		return ResponseEntity.ok().body(taskService.fetchAllUserTasks(userId));
	}
	
	@GetMapping("/{taskId}")
	public ResponseEntity<Task> getTaskById(HttpServletRequest request, @PathVariable("taskId") Integer taskId) {
		int userId = (Integer) request.getAttribute("userId");
		return ResponseEntity.ok().body(taskService.fetchTaskById(userId, taskId));
	}
	
	@PostMapping("")
	public ResponseEntity<Task> addTask(HttpServletRequest request, @RequestBody Task task) {
		int userId = (Integer) request.getAttribute("userId");
		return new ResponseEntity<Task>(taskService.addTask(userId, task), HttpStatus.CREATED);
	}
	
	@PutMapping("/{taskId}")
	public ResponseEntity<Task> updateTask(HttpServletRequest request, @PathVariable("taskId") Integer taskId, @RequestBody Task task) {
		int userId = (Integer) request.getAttribute("userId");
		return ResponseEntity.ok().body(taskService.updateTask(userId, taskId, task));
	}
	
	@DeleteMapping("/{taskId}")
	public ResponseEntity<Map<String, Boolean>> deleteTask(HttpServletRequest request, @PathVariable("taskId") Integer taskId) {
		int userId = (Integer) request.getAttribute("userId");
		taskService.removeTask(userId, taskId);
		Map<String, Boolean> map = new HashMap<>();
		map.put("Success", true);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
}
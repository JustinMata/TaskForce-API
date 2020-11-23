package com.mata.taskforce.resources;

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

import com.mata.taskforce.domain.Task;
import com.mata.taskforce.domain.TaskStatus;
import com.mata.taskforce.services.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskResource {
	
	@Autowired
	TaskService taskService;
	
	@GetMapping("")
	public ResponseEntity<List<Task>> getAllCategories(HttpServletRequest request) {
		int userId = (Integer) request.getAttribute("userId");
		List<Task> tasks = taskService.fetchallTasks(userId);
		return new ResponseEntity<>(tasks, HttpStatus.OK);
	}
	
	@GetMapping("{taskId}")
	public ResponseEntity<Task> getTaskById(HttpServletRequest request, @PathVariable("taskId") Integer taskId) {
		int userId = (Integer) request.getAttribute("userId");
		Task task = taskService.fetchTaskById(userId, taskId);
		return new ResponseEntity<>(task, HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<Task> addTask(HttpServletRequest request, @RequestBody Map<String, Object> taskMap) {
		int userId = (Integer) request.getAttribute("userId");
		String title = (String) taskMap.get("title");
		String description = (String) taskMap.get("description");
		Task task = taskService.addTask(userId, title, description);
		return new ResponseEntity<Task>(task, HttpStatus.CREATED);
	}
	
	@PutMapping("/{taskId}")
	public ResponseEntity<Map<String, Boolean>> updateTask(HttpServletRequest request, 
			@PathVariable("taskId") Integer taskId, @RequestBody Map<String, Object> taskMap) {
		int userId = (Integer) request.getAttribute("userId");
		Task task = taskService.fetchTaskById(userId, taskId);
		task.setDescription((String) taskMap.get("description"));
		task.setTitle((String) taskMap.get("title"));
		task.setStatus(TaskStatus.valueOf(Integer.parseInt((String) taskMap.get("status"))));
		taskService.updateTask(userId, taskId, task);
		Map<String, Boolean> map = new HashMap<>();
		map.put("success", true);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@DeleteMapping("/{taskId}")
	public ResponseEntity<Map<String, Boolean>> deleteTask(HttpServletRequest request, @PathVariable("taskId") Integer taskId) {
		int userId = (Integer) request.getAttribute("userId");
		taskService.removeTask(userId, taskId);
		Map<String, Boolean> map = new HashMap<>();
		map.put("success", true);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
}

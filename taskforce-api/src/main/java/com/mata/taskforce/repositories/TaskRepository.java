package com.mata.taskforce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mata.taskforce.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{
	
	List<Task> findByUserId(Integer userId);
}
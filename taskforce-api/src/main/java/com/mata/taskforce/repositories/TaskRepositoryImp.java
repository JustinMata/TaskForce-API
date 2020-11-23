package com.mata.taskforce.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mata.taskforce.domain.Task;
import com.mata.taskforce.domain.TaskStatus;
import com.mata.taskforce.exceptions.TfBadRequestException;
import com.mata.taskforce.exceptions.TfResourceNotFoundException;

@Repository
public class TaskRepositoryImp implements TaskRepository {
	
	private static final String SQL_FIND_ALL = "SELECT * FROM TASK_FORCE.TASKS WHERE USER_ID = ?";
	
	private static final String SQL_FIND_BY_ID = "SELECT * FROM TASK_FORCE.TASKS WHERE TASK_ID = ? AND USER_ID = ?";
	
	private static final String SQL_CREATE = "INSERT INTO TASK_FORCE.TASKS (TASK_ID, USER_ID, TITLE, DESCRIPTION, "
			+ "CREATEDON, TASKSTATUS) VALUES(DEFAULT, ?, ?, ?, DEFAULT, DEFAULT)";

	private static final String SQL_UPDATE = "UPDATE TASK_FORCE.TASKS SET TITLE = ?, DESCRIPTION = ?, TASKSTATUS = ? "
			+ "WHERE TASK_ID = ? AND USER_ID = ?";
	
	private static final String SQL_DELETE = "DELETE FROM TASK_FORCE.TASKS WHERE TASK_ID = ? AND USER_ID = ?";
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Task> findAll(Integer userId) throws TfResourceNotFoundException {
		return jdbcTemplate.query(SQL_FIND_ALL, new Object[]{userId}, taskRowMapper);
	}

	@Override
	public Task findById(Integer userId, Integer taskId) throws TfResourceNotFoundException {
		try {
			return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{taskId, userId}, taskRowMapper);
		} catch (Exception e) {
			throw new TfResourceNotFoundException("Task not found");
		}
	}

	@Override
	public Integer create(Integer userId, String title, String description) throws TfBadRequestException {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, userId);
				ps.setString(2, title);
				ps.setString(3, description);
				return ps;
			}, keyHolder);
			return (Integer) keyHolder.getKeys().get("TASK_ID");
		} catch (Exception e) {
			throw new TfBadRequestException("Invalid request");
		}
	}

	@Override
	public void update(Integer userId, Integer taskId, Task task) throws TfBadRequestException {
		try {
			jdbcTemplate.update(SQL_UPDATE, new Object[] {task.getTitle(), task.getDescription(), task.getStatus().getValue(), taskId, userId});
		} catch (Exception e) {
			throw new TfBadRequestException("Invalid request");
		}
		
	}

	@Override
	public void removeById(Integer userId, Integer taskId) throws TfResourceNotFoundException {
		jdbcTemplate.update(SQL_DELETE, new Object[]{taskId, userId});
	}
	
	private RowMapper<Task> taskRowMapper = ((rs, rowNum) -> {
		return new Task(rs.getInt("TASK_ID"),
				rs.getInt("USER_ID"),
				rs.getString("TITLE"),
				rs.getString("DESCRIPTION"),
				rs.getString("CREATEDON"),
				TaskStatus.valueOf(rs.getInt("TASKSTATUS")));
	});
}

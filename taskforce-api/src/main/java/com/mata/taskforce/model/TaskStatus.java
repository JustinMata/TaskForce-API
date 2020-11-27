package com.mata.taskforce.model;

import java.util.HashMap;

public enum TaskStatus {
	PLANNED(1), IN_PROGRESS(2), TESTING(3), COMPLETED(4);
	
	private final int value;
	private static HashMap<Integer, TaskStatus> map = new HashMap<Integer, TaskStatus>();
	
	private TaskStatus(int value) {
		this.value = value;
	}
	
	static {
		for (TaskStatus status : TaskStatus.values()) {
			map.put(status.value, status);
		}
	}
	
	public static TaskStatus valueOf(int status) {
		return (TaskStatus) map.get(status);
	}
	
	public int getValue() {
		return value;
	}
}
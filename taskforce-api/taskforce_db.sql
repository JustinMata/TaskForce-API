-- ENVIRONMENT

-- In case of environment reset
-- DROP database taskforcedb;
-- DROP USER taskforce;
-- DROP SCHEMA task_force;

-- Initialize environment
CREATE USER taskforce WITH password 'yourpasswordhere';
CREATE database taskforcedb WITH template=template0 owner=taskforce;
\CONNECT taskforcedb;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA task_force TO taskforce;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA task_force TO taskforce;
CREATE SCHEMA task_force;

-- DATA DEFINITIONS

-- Hard reset tables
-- DROP TABLE task_status;
-- DROP TABLE tasks;
-- DROP TABLE users;

-- Initialize tables
CREATE TABLE IF NOT EXISTS users(
	user_id serial PRIMARY KEY,
	first_name varchar(20) NOT NULL,
	last_name varchar(20) NOT NULL,
	email varchar(50) UNIQUE NOT NULL,
	password varchar(256) NOT NULL
);

CREATE TABLE IF NOT EXISTS task_status(
	id serial PRIMARY KEY,
	taskStatus varchar UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS tasks(
	task_id serial PRIMARY KEY,
	user_id integer NOT NULL,
	title varchar(50) NOT NULL,
	description varchar(255) NOT NULL,
	createdOn date NOT NULL DEFAULT current_date,
	taskStatus integer NOT NULL
);

ALTER TABLE tasks ADD CONSTRAINT tasks_users_fk
FOREIGN KEY(user_id) REFERENCES users(user_id);

ALTER TABLE tasks ADD CONSTRAINT tasks_taskStatus_fk
FOREIGN KEY(taskStatus) REFERENCES task_status(id);

ALTER TABLE tasks ALTER COLUMN taskStatus SET DEFAULT 1;

-- DATA MANIPULATION

-- Soft Reset tables
-- DELETE FROM users;
-- DELETE FROM task_status;
-- DELETE FROM tasks;
-- ALTER SEQUENCE users_user_id_seq RESTART WITH 1;
-- ALTER SEQUENCE task_status_id_seq RESTART WITH 1;
-- ALTER SEQUENCE tasks_task_id_seq RESTART WITH 1;

-- Intialize all values for task statuses
INSERT INTO task_status VALUES(DEFAULT, 'Planned');

INSERT INTO task_status VALUES(DEFAULT, 'In Progress');

INSERT INTO task_status VALUES(DEFAULT, 'Testing');

INSERT INTO task_status VALUES(DEFAULT, 'Completed');
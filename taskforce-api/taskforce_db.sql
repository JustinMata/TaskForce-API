-- ENVIRONMENT

-- In case of environment reset
-- DROP database taskforcedb;
-- DROP USER taskforce;
-- DROP SCHEMA task_force;

-- Initialize environment
CREATE USER taskforce WITH password 'mamacita123';
CREATE database taskforcedb WITH template=template0 owner=taskforce;
-- \CONNECT taskforcedb;
CREATE SCHEMA task_force;
GRANT ALL ON SCHEMA task_force TO taskforce;

-- Only use these if permissions are still not working after creating tables
-- GRANT ALL ON TABLE task_force.task_status TO taskforce;
-- GRANT ALL ON TABLE task_force.tasks TO taskforce;
-- GRANT ALL ON TABLE task_force.users TO taskforce;
-- GRANT ALL ON SEQUENCE task_force.task_status_id_seq TO postgres;
-- GRANT ALL ON SEQUENCE task_force.tasks_task_id_seq TO postgres;
-- GRANT ALL ON SEQUENCE task_force.users_user_id_seq TO postgres;

-- DATA DEFINITIONS

-- Hard reset tables
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

CREATE TABLE IF NOT EXISTS tasks(
	task_id serial PRIMARY KEY,
	user_id integer NOT NULL,
	title varchar(50) NOT NULL,
	description varchar(255) NOT NULL,
	createdOn date NOT NULL DEFAULT current_date,
	taskStatus varchar(20) NOT NULL
);

ALTER TABLE tasks ADD CONSTRAINT tasks_users_fk
FOREIGN KEY(user_id) REFERENCES users(user_id);

ALTER TABLE tasks ALTER COLUMN taskStatus SET DEFAULT 'PLANNED';

-- DATA MANIPULATION

-- Soft Reset tables
-- DELETE FROM users;
-- DELETE FROM tasks;
-- ALTER SEQUENCE users_user_id_seq RESTART WITH 1;
-- ALTER SEQUENCE tasks_task_id_seq RESTART WITH 1;
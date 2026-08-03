-- "user" is a reserved keyword in PostgreSQL, must be quoted
ALTER TABLE users RENAME TO "user";
ALTER TABLE roles RENAME TO role;


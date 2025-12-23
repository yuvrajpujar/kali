


INSERT INTO users (username, email, password_hash, full_name, role, bio, metadata, created_at) VALUES ('student1', 'student1@example.com', 'hash1', 'Student One', 'student', 'Bio for student', '{"grade": "A"}', CURRENT_TIMESTAMP);
INSERT INTO users (username, email, password_hash, full_name, role, bio, metadata, created_at) VALUES ('instructor1', 'instructor1@example.com', 'hash2', 'Instructor One', 'instructor', 'Bio for instructor', '{"department": "Math"}', CURRENT_TIMESTAMP);
INSERT INTO users (username, email, password_hash, full_name, role, bio, metadata, created_at) VALUES ('admin1', 'admin1@example.com', 'hash3', 'Admin One', 'admin', 'Bio for admin', '{"accessLevel": "super"}', CURRENT_TIMESTAMP);

-- Sample categories
INSERT INTO categories (name, description, created_at) VALUES ('Programming', 'All programming courses', CURRENT_TIMESTAMP);
INSERT INTO categories (name, description, created_at) VALUES ('Mathematics', 'All math courses', CURRENT_TIMESTAMP);
INSERT INTO categories (name, description, created_at) VALUES ('Java', 'Java programming courses', CURRENT_TIMESTAMP);
INSERT INTO categories (name, description, parent_id, created_at) VALUES ('Spring Boot', 'Spring Boot framework', 3, CURRENT_TIMESTAMP);

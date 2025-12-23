


INSERT INTO users (username, email, password_hash, full_name, role, bio, metadata, created_at) VALUES ('student1', 'student1@example.com', 'hash1', 'Student One', 'student', 'Bio for student', '{"grade": "A"}', CURRENT_TIMESTAMP);
INSERT INTO users (username, email, password_hash, full_name, role, bio, metadata, created_at) VALUES ('instructor1', 'instructor1@example.com', 'hash2', 'Instructor One', 'instructor', 'Bio for instructor', '{"department": "Math"}', CURRENT_TIMESTAMP);
INSERT INTO users (username, email, password_hash, full_name, role, bio, metadata, created_at) VALUES ('admin1', 'admin1@example.com', 'hash3', 'Admin One', 'admin', 'Bio for admin', '{"accessLevel": "super"}', CURRENT_TIMESTAMP);

-- Sample categories
INSERT INTO categories (name, description, created_at) VALUES ('Programming', 'All programming courses', CURRENT_TIMESTAMP);
INSERT INTO categories (name, description, created_at) VALUES ('Mathematics', 'All math courses', CURRENT_TIMESTAMP);
INSERT INTO categories (name, description, created_at) VALUES ('Java', 'Java programming courses', CURRENT_TIMESTAMP);
INSERT INTO categories (name, description, parent_id, created_at) VALUES ('Spring Boot', 'Spring Boot framework', 3, CURRENT_TIMESTAMP);

-- Sample courses
INSERT INTO courses (title, slug, description, category_id, instructor_id, price, status, metadata, updated_at) VALUES ('Java Basics', 'java-basics', 'Intro to Java', 3, 2, 49.99, 'published', '{"level": "beginner"}', CURRENT_TIMESTAMP);
INSERT INTO courses (title, slug, description, category_id, instructor_id, price, status, metadata, updated_at) VALUES ('Spring Boot Fundamentals', 'spring-boot-fundamentals', 'Spring Boot course', 4, 2, 59.99, 'draft', '{"level": "intermediate"}', CURRENT_TIMESTAMP);

-- Sample modules
INSERT INTO modules (course_id, title, order_index) VALUES (1, 'Getting Started', 1);
INSERT INTO modules (course_id, title, order_index) VALUES (1, 'Java Syntax', 2);
INSERT INTO modules (course_id, title, order_index) VALUES (2, 'Spring Boot Setup', 1);

-- Sample lessons
INSERT INTO lessons (module_id, title, content_type, content_url, body_content, is_preview, duration_seconds, order_index) VALUES (1, 'Welcome', 'video', 'http://example.com/welcome.mp4', 'Welcome to Java', TRUE, 300, 1);
INSERT INTO lessons (module_id, title, content_type, content_url, body_content, is_preview, duration_seconds, order_index) VALUES (2, 'Variables', 'text', NULL, 'Java variables explained', FALSE, 0, 1);
INSERT INTO lessons (module_id, title, content_type, content_url, body_content, is_preview, duration_seconds, order_index) VALUES (3, 'Spring Boot Installation', 'video', 'http://example.com/springboot.mp4', 'Install Spring Boot', TRUE, 400, 1);

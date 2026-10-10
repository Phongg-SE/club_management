DROP DATABASE IF EXISTS swp391;
CREATE DATABASE swp391;
USE swp391;

		CREATE TABLE user (
			user_id INT AUTO_INCREMENT PRIMARY KEY,
			full_name NVARCHAR(30) NOT NULL,
			email VARCHAR(50) NOT NULL UNIQUE,
			username NVARCHAR(30) NOT NULL UNIQUE,
			password NVARCHAR(255) NOT NULL,
			gender NVARCHAR(10),
			phone_number NVARCHAR(11),
			avatar_url TEXT,
			date_of_birth date,
			address NVARCHAR(100),
            city NVARCHAR(50),
            district NVARCHAR(50),
			facebook TEXT,
			instargram TEXT,
            token NVARCHAR(12),
            token_expiry DATE
		);

	CREATE TABLE setting (
		setting_id INT AUTO_INCREMENT PRIMARY KEY,
		name NVARCHAR(50) NOT NULL,
		type_id INT NOT NULL,
		value NVARCHAR(50) NOT NULL unique,
		priority INT NOT NULL,
		status NVARCHAR(20) NOT NULL,
		description TEXT,
		FOREIGN KEY (type_id) REFERENCES setting(setting_id)
	);

	CREATE TABLE club (
		club_id INT AUTO_INCREMENT PRIMARY KEY,
		name NVARCHAR(50) NOT NULL UNIQUE,
		slogan NVARCHAR(150),
		club_email VARCHAR(50) UNIQUE,
		club_fanpage_url TEXT,
		club_logo_url TEXT,
		is_public VARCHAR(3) CHECK (is_public IN ('Yes', 'No')) NOT NULL,
		category_id INT NOT NULL,
		status NVARCHAR(20) NOT NULL,
		description TEXT,
		FOREIGN KEY (category_id) REFERENCES setting(setting_id)
	);

	CREATE TABLE club_member (
		club_id INT NOT NULL,
		user_id INT NOT NULL,
		role_id INT NOT NULL,
		status VARCHAR(20) NOT NULL,
		PRIMARY KEY (club_id, user_id, role_id),
        note TEXT,
		FOREIGN KEY (club_id) REFERENCES club(club_id),
		FOREIGN KEY (user_id) REFERENCES user(user_id),
		FOREIGN KEY (role_id) REFERENCES setting(setting_id)
	);

	CREATE TABLE event (
		event_id INT AUTO_INCREMENT PRIMARY KEY,
		club_id INT NOT NULL,
		leader_id INT NOT NULL,
		name VARCHAR(50) NOT NULL UNIQUE,
		avatar_url TEXT,
		guest VARCHAR(50),
		location VARCHAR(255) NOT NULL,
		start_time DATETIME NOT NULL,
		end_time DATETIME NOT NULL,
		created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
		is_public VARCHAR(3) CHECK (is_public IN ('Yes', 'No')) NOT NULL,
		status VARCHAR(20) NOT NULL,
		description TEXT,
		type ENUM('Event', 'Project') NOT NULL DEFAULT 'Event',
		FOREIGN KEY (club_id) REFERENCES club(club_id),
		FOREIGN KEY (leader_id) REFERENCES user(user_id)
);


	CREATE TABLE user_role (
		user_id INT NOT NULL,
		role_id INT NOT NULL,
		status NVARCHAR(20) NOT NULL,
		PRIMARY KEY (user_id, role_id),
		FOREIGN KEY (user_id) REFERENCES user(user_id),
		FOREIGN KEY (role_id) REFERENCES setting(setting_id)
	);

	CREATE TABLE post (
		post_id INT AUTO_INCREMENT PRIMARY KEY,
		user_id INT NOT NULL,
        category_id INT NOT NULL,
		club_id INT NOT NULL,
        tittle NVARCHAR(50) NOT NULL,
		image_url TEXT,
        post_at DATETIME NOT NULL,
		status NVARCHAR(20) NOT NULL, 
		description TEXT,
		FOREIGN KEY (user_id) REFERENCES user(user_id),
		FOREIGN KEY (club_id) REFERENCES club(club_id),
        FOREIGN KEY (category_id) REFERENCES setting(setting_id)
	);


	CREATE TABLE finance (
		finance_id INT AUTO_INCREMENT PRIMARY KEY,
		finance_name VARCHAR(50) NOT NULL,
		event_id INT,
		club_id INT NOT NULL,
		member_id INT NOT NULL,
		approver_id INT DEFAULT NULL,
		is_expense ENUM('Yes', 'No') NOT NULL, 
		amount DECIMAL(15, 2) NOT NULL,
		status VARCHAR(10) NOT NULL,
		finance_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
		last_updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
		details TEXT,
		FOREIGN KEY (club_id) REFERENCES club(club_id) ON DELETE CASCADE,
		FOREIGN KEY (event_id) REFERENCES event(event_id) ON DELETE SET NULL,
		FOREIGN KEY (member_id) REFERENCES user(user_id) ON DELETE CASCADE,
		FOREIGN KEY (approver_id) REFERENCES user(user_id) ON DELETE SET NULL
);

	CREATE TABLE task (
		task_id INT AUTO_INCREMENT PRIMARY KEY,
		event_id INT NOT NULL,
		club_id INT NOT NULL,
		title NVARCHAR(50) NOT NULL,
		assigner_id INT NOT NULL,
		assignee_id INT NOT NULL,
		deadline DATETIME NOT NULL,
		status NVARCHAR(20) NOT NULL,
		description TEXT NOT NULL,
        note TEXT,
		FOREIGN KEY (club_id) REFERENCES club(club_id),
		FOREIGN KEY (event_id) REFERENCES event(event_id),
		FOREIGN KEY (assigner_id) REFERENCES user(user_id),
		FOREIGN KEY (assignee_id) REFERENCES user(user_id)
	);

	CREATE TABLE registration (
		registration_id INT AUTO_INCREMENT PRIMARY KEY,
		event_id INT NOT NULL,
		register_id INT,
		full_name NVARCHAR(30) NOT NULL,
		email VARCHAR(50) NOT NULL,
		mobile NVARCHAR(11),
		status NVARCHAR(20) NOT NULL,
        note NVARCHAR(255),
        reason NVARCHAR(255),
		FOREIGN KEY (event_id) REFERENCES event(event_id),
		FOREIGN KEY (register_id) REFERENCES user(user_id)
	);
    
    
    Create table project_event_member (
		member_id INT PRIMARY KEY AUTO_INCREMENT,
		user_id INT,
		event_id INT NULL,
        project_id INT NULL,
		status NVARCHAR(20) NOT NULL,
		FOREIGN KEY (user_id) REFERENCES user(user_id),
		FOREIGN KEY (event_id) REFERENCES event(event_id),
        FOREIGN KEY (project_id) REFERENCES event(event_id),
        CHECK (
        (event_id IS NOT NULL AND project_id IS NULL) 
        OR 
        (event_id IS NULL AND project_id IS NOT NULL)
		)
    );
    

INSERT INTO user (full_name, email, username, password, gender, phone_number, avatar_url) VALUES
('Nguyễn Văn A', 'nguyenvana@example.com', 'nguyenvana', 'hashed_password_1', 'Male', '0912345678', NULL),
('Trần Thị B', 'tranthib@example.com', 'tranthib', 'hashed_password_2', 'Female', '0923456789', NULL),
('Lê Văn C', 'levanc@example.com', 'levanc', 'hashed_password_3', 'Male', '0934567890', NULL),
('Phạm Thị D', 'phamthid@example.com', 'phamthid', 'hashed_password_4', 'Female', '0945678901', NULL),
('Hoàng Văn E', 'hoangvane@example.com', 'hoangvane', 'hashed_password_5', 'Male', '0956789012', NULL),
('Đặng Thị F', 'dangthif@example.com', 'dangthif', 'hashed_password_6', 'Female', '0967890123', NULL),
('Bùi Văn G', 'buivang@example.com', 'buivang', 'hashed_password_7', 'Male', '0978901234', NULL),
('Võ Thị H', 'vothih@example.com', 'vothih', 'hashed_password_8', 'Female', '0989012345', NULL),
('Đỗ Văn I', 'dovani@example.com', 'dovani', 'hashed_password_9', 'Male', '0990123456', NULL),
('Ngô Thị J', 'ngothij@example.com', 'ngothij', 'hashed_password_10', 'Female', '0901234567', NULL),
('Lý Văn K', 'lyvank@example.com', 'lyvank', 'hashed_password_11', 'Male', '0912233445', NULL),
('Tô Thị L', 'tothil@example.com', 'tothil', 'hashed_password_12', 'Female', '0922334455', NULL),
('Vương Văn M', 'vuongvanm@example.com', 'vuongvanm', 'hashed_password_13', 'Male', '0933445566', NULL),
('Dương Thị N', 'duongthin@example.com', 'duongthin', 'hashed_password_14', 'Female', '0944556677', NULL),
('Trịnh Văn O', 'trinhvano@example.com', 'trinhvano', 'hashed_password_15', 'Male', '0955667788', NULL),
('Lâm Thị P', 'lamthip@example.com', 'lamthip', 'hashed_password_16', 'Female', '0966778899', NULL),
('Hà Văn Q', 'havanh@example.com', 'havanh', 'hashed_password_17', 'Male', '0977889900', NULL),
('Tạ Thị R', 'tathir@example.com', 'tathir', 'hashed_password_18', 'Female', '0988990011', NULL),
('Triệu Văn S', 'trieuvans@example.com', 'trieuvans', 'hashed_password_19', 'Male', '0999001122', NULL),
('Chu Thị T', 'chuthit@example.com', 'chuthit', 'hashed_password_20', 'Female', '0900112233', NULL),
('Phan Văn U', 'phanvanu@example.com', 'phanvanu', 'hashed_password_21', 'Male', '0911122233', NULL),
('Lương Thị V', 'luongthiv@example.com', 'luongthiv', 'hashed_password_22', 'Female', '0922233344', NULL),
('Cao Văn W', 'caovanw@example.com', 'caovanw', 'hashed_password_23', 'Male', '0933344455', NULL),
('Tống Thị X', 'tongthix@example.com', 'tongthix', 'hashed_password_24', 'Female', '0944455566', NULL),
('Hứa Văn Y', 'huavany@example.com', 'huavany', 'hashed_password_25', 'Male', '0955566677', NULL),
('Thái Thị Z', 'thaithiz@example.com', 'thaithiz', 'hashed_password_26', 'Female', '0966677788', NULL),
('Trương Văn AA', 'truongvanaa@example.com', 'truongvanaa', 'hashed_password_27', 'Male', '0977788899', NULL),
('Đinh Thị BB', 'dinhthibb@example.com', 'dinhthibb', 'hashed_password_28', 'Female', '0988899900', NULL),
('Văn Văn CC', 'vanvancc@example.com', 'vanvancc', 'hashed_password_29', 'Male', '0999900112', NULL),
('Châu Thị DD', 'chauthidd@example.com', 'chauthidd', 'hashed_password_30', 'Female', '0900011223', NULL),
('Kiều Văn EE', 'kieuvanee@example.com', 'kieuvanee', 'hashed_password_31', 'Male', '0911122334', NULL),
('Quách Thị FF', 'quachthiff@example.com', 'quachthiff', 'hashed_password_32', 'Female', '0922233445', NULL),
('Từ Văn GG', 'tuvangg@example.com', 'tuvangg', 'hashed_password_33', 'Male', '0933344556', NULL),
('Lục Thị HH', 'lucthihh@example.com', 'lucthihh', 'hashed_password_34', 'Female', '0944455667', NULL),
('Tôn Văn II', 'tonvanii@example.com', 'tonvanii', 'hashed_password_35', 'Male', '0955566778', NULL),
('Lã Thị JJ', 'lathijj@example.com', 'lathijj', 'hashed_password_36', 'Female', '0966677889', NULL),
('Đoàn Văn KK', 'doanvankk@example.com', 'doanvankk', 'hashed_password_37', 'Male', '0977788990', NULL),
('Khúc Thị LL', 'khucthill@example.com', 'khucthill', 'hashed_password_38', 'Female', '0988899001', NULL),
('Thạch Văn MM', 'thachvanmm@example.com', 'thachvanmm', 'hashed_password_39', 'Male', '0999900123', NULL),
('Tạ Thị NN', 'tatinn@example.com', 'tatinn', 'hashed_password_40', 'Female', '0900012345', NULL),
('Nguyễn Thị OO', 'nguyenthioo@example.com', 'nguyenthioo', 'hashed_password_41', 'Female', '0912233445', NULL),
('Phạm Văn PP', 'phamvanpp@example.com', 'phamvanpp', 'hashed_password_42', 'Male', '0923344556', NULL),
('Trần Thị QQ', 'tranthiquq@example.com', 'tranthiquq', 'hashed_password_43', 'Female', '0934455667', NULL),
('Lê Văn RR', 'levanrr@example.com', 'levanrr', 'hashed_password_44', 'Male', '0945566778', NULL),
('Bùi Thị SS', 'buithiss@example.com', 'buithiss', 'hashed_password_45', 'Female', '0956677889', NULL),
('Đỗ Văn TT', 'dovantt@example.com', 'dovantt', 'hashed_password_46', 'Male', '0967788990', NULL),
('Hoàng Thị UU', 'hoangthiuu@example.com', 'hoangthiuu', 'hashed_password_47', 'Female', '0978899001', NULL),
('Đặng Văn VV', 'dangvanvv@example.com', 'dangvanvv', 'hashed_password_48', 'Male', '0989900112', NULL),
('Ngô Thị WW', 'ngothiww@example.com', 'ngothiww', 'hashed_password_49', 'Female', '0990011223', NULL),
('Tô Văn XX', 'tovanxx@example.com', 'tovanxx', 'hashed_password_50', 'Male', '0901122334', NULL);

INSERT INTO setting (value, type_id, name, priority, status, description) VALUES
('Admin', 1, 'Administrator Role', 1, 'Active', 'Quyền quản trị viên'),
('Event Leader', 1, 'Event Leader Role', 2, 'Active', 'Trưởng sự kiện'),
('Club Member', 1, 'Club Member Role', 3, 'Active', 'Thành viên câu lạc bộ'),
('Club Leader', 1, 'Club Leader Role', 4, 'Active', 'Chủ nhiệm câu lạc bộ'),
('Project Leader', 1, 'Project Leader Role', 5, 'Active', 'Trưởng dự án'),
('Web User', 1, 'Web User Role', 7, 'Active', 'Người dùng trên website'),
('Sports Club', 2, 'Category', 8, 'Active', 'Câu lạc bộ thể thao'),
('Music Club', 2, 'Category', 9, 'Active', 'Câu lạc bộ âm nhạc'),
('Technology Club', 2, 'Category', 10, 'Active', 'Câu lạc bộ công nghệ'),
('Art Club', 2, 'Category', 11, 'Active', 'Câu lạc bộ nghệ thuật'),
('Business Club', 2, 'Category', 12, 'Active', 'Câu lạc bộ kinh doanh'),
('Science Club', 2, 'Category', 13, 'Active', 'Câu lạc bộ khoa học'),
('Language Club', 2, 'Category', 14, 'Active', 'Câu lạc bộ ngôn ngữ'),
('Gaming Club', 2, 'Category', 15, 'Active', 'Câu lạc bộ game'),
('Photography Club', 2, 'Category', 16, 'Active', 'Câu lạc bộ nhiếp ảnh'),
('Drama Club', 2, 'Category', 17, 'Active', 'Câu lạc bộ kịch');

INSERT INTO club (name, slogan, club_email, club_fanpage_url, club_logo_url, is_public, category_id, status, description) VALUES
('Champions FC', 'Together we win!', 'championsfc@email.com', 'https://facebook.com/championsfc', NULL, 'Yes', 7, 'Active', 'Câu lạc bộ bóng đá dành cho mọi người yêu thể thao.'),
('Melody Hub', 'Feel the music!', 'melodyhub@email.com', 'https://facebook.com/melodyhub', NULL, 'Yes', 8, 'Active', 'Câu lạc bộ âm nhạc với các buổi biểu diễn hàng tuần.'),
('Code Warriors', 'Innovation through coding', 'codewarriors@email.com', 'https://facebook.com/codewarriors', NULL, 'Yes', 9, 'Active', 'Câu lạc bộ lập trình và công nghệ.'),
('Creative Minds', 'Art for everyone', 'creativeminds@email.com', 'https://facebook.com/creativeminds', NULL, 'Yes', 10, 'Active', 'Câu lạc bộ nghệ thuật sáng tạo.'),
('Young Entrepreneurs', 'Building future leaders', 'youngentrepreneurs@email.com', 'https://facebook.com/youngentrepreneurs', NULL, 'Yes', 11, 'Active', 'Câu lạc bộ kinh doanh dành cho sinh viên.'),
('Future Scientists', 'Discovering the unknown', 'futurescientists@email.com', 'https://facebook.com/futurescientists', NULL, 'Yes', 12, 'Active', 'Câu lạc bộ nghiên cứu khoa học và sáng tạo.'),
('Polyglot Club', 'Speak the world', 'polyglotclub@email.com', 'https://facebook.com/polyglotclub', NULL, 'Yes', 13, 'Active', 'Câu lạc bộ học ngoại ngữ và giao lưu văn hóa.'),
('Pro Gamers', 'Level up together', 'progamers@email.com', 'https://facebook.com/progamers', NULL, 'Yes', 14, 'Active', 'Câu lạc bộ dành cho những người đam mê game.'),
('Shutter Squad', 'Capture the moment', 'shuttersquad@email.com', 'https://facebook.com/shuttersquad', NULL, 'Yes', 15, 'Active', 'Câu lạc bộ nhiếp ảnh và chỉnh sửa ảnh.'),
('Stage Stars', 'Bringing stories to life', 'stagestars@email.com', 'https://facebook.com/stagestars', NULL, 'Yes', 16, 'Active', 'Câu lạc bộ kịch nghệ và diễn xuất.');


INSERT INTO club_member (club_id, user_id, role_id, status) VALUES
(1,  1, 4, 'Active'), (1,  2, 3, 'Active'), (1,  2, 5, 'Active'), (1, 3, 2, 'Active'), (1,  4, 3, 'Active'), (1, 5, 5, 'Active'), (1, 5, 2, 'Active'),
(1,  6, 3, 'Active'), (1,  7, 2, 'Active'),(1,  7, 5, 'Active'), (1, 8, 2, 'Active'), (1,  9, 2, 'Active'),(1,  9, 5, 'Active'), (1, 10, 3, 'Active'),

(2, 11, 4, 'Active'), (2, 12, 2, 'Active'), (2, 13, 3, 'Active'),  (2, 13, 5, 'Active'), (2, 14, 2, 'Active'), (2, 15, 3, 'Active'),(2, 15, 5, 'Active'),
(2, 16, 2, 'Active'), (2, 17, 3, 'Active'), (2, 17, 5, 'Active'), (2, 18, 2, 'Active'), (2, 19, 3, 'Active'), (2, 20, 3, 'Active'),

(3, 21, 4, 'Active'), (3, 22, 2, 'Active'), (3, 23, 2, 'Active'), (3, 24, 2, 'Active'),(3, 24, 5, 'Active'), (3, 25, 2, 'Active'),
(3, 26, 3, 'Active'), (3, 27, 5, 'Active'), (3, 27, 2, 'Active'), (3, 28, 3, 'Active'), (3, 28, 5, 'Active'), (3, 29, 2, 'Active'), (3, 30, 3, 'Active'),

(4, 31, 4, 'Active'), (4, 32, 3, 'Active'), (4, 33, 3, 'Active'), (4, 34, 3, 'Active'), (4, 35, 3, 'Active'),
(4, 36, 3, 'Active'), (4, 37, 3, 'Active'), (4, 38, 3, 'Active'), (4, 39, 3, 'Active'), (4, 40, 3, 'Active'),

(5, 41, 4, 'Active'), (5, 42, 3, 'Active'), (5, 43, 2, 'Active'), (5, 44, 2, 'Active'), (5, 45, 5, 'Active'),(5, 45, 2, 'Active'),
(5, 46, 3, 'Active'), (5, 46, 5, 'Active'), (5, 47, 2, 'Active'), (5, 47, 5, 'Active'), (5, 48, 3, 'Active'), (5, 49, 2, 'Active'), (5, 50, 3, 'Active'),

(6, 2, 4, 'Active'),
(7, 3, 4, 'Active'),
(8, 4, 4, 'Active'),
(9, 5, 4, 'Active'),
(10, 6, 4, 'Active');

INSERT INTO event (club_id, leader_id, name, avatar_url, guest, location, start_time, end_time, created_date, is_public, status, description) VALUES
-- Champions FC (Bóng đá)
(1, 3, 'Giải đấu nội bộ Champions Cup', NULL, NULL, 'Sân vận động A1', '2025-04-10 08:00:00', '2025-04-10 18:00:00', NOW(), 'Yes', 'Planned', 'Giải đấu giao hữu giữa các thành viên CLB.'),
(1, 5, 'Giao lưu với cầu thủ chuyên nghiệp', NULL, 'Cầu thủ B', 'Hội trường B2', '2025-04-15 09:00:00', '2025-04-15 12:00:00', NOW(), 'Yes', 'Planned', 'Gặp gỡ và học hỏi kinh nghiệm từ cầu thủ nổi tiếng.'),
(1, 7, 'Workshop: Chiến thuật bóng đá hiện đại', NULL, NULL, 'Phòng họp C3', '2025-04-20 14:00:00', '2025-04-20 17:00:00', NOW(), 'No', 'Planned', 'Tìm hiểu các chiến thuật bóng đá mới nhất.'),
(1, 9, 'Trại hè bóng đá trẻ', NULL, NULL, 'Sân vận động TP', '2025-03-27 08:00:00', '2025-04-2 17:00:00', NOW(), 'Yes', 'Ongoing', 'Chương trình huấn luyện bóng đá cho trẻ em.'),
(1, 8, 'Hoạt động thiện nguyện: Đá bóng gây quỹ', NULL, NULL, 'Sân vận động B4', '2024-05-10 16:00:00', '2024-05-10 19:00:00', NOW(), 'Yes', 'Completed', 'Tổ chức đá bóng gây quỹ cho trẻ em nghèo.'),

-- Melody Hub (Âm nhạc)
(2, 18, 'Đêm nhạc Acoustic', NULL, NULL, 'Nhà hát TP', '2025-04-13 19:00:00', '2025-04-13 21:00:00', NOW(), 'Yes', 'Planned', 'Buổi biểu diễn acoustic với các ca sĩ sinh viên.'),
(2, 16, 'Workshop: Sáng tác nhạc cơ bản', NULL, 'Nhạc sĩ C', 'Hội trường D4', '2025-03-28 14:00:00', '2025-03-28 17:00:00', NOW(), 'Yes', 'Ongoing', 'Hướng dẫn sáng tác bài hát cơ bản.'),
(2, 12, 'Cuộc thi hát Melody Voice', NULL, NULL, 'Hội trường E5', '2025-03-27 13:00:00', '2025-03-28 17:00:00', NOW(), 'No', 'Ongoing', 'Thi đấu hát dành cho các thành viên CLB.'),
(2, 14, 'Giao lưu với nghệ sĩ nổi tiếng', NULL, 'Ca sĩ D', 'Hội trường G2', '2025-01-05 18:00:00', '2025-01-05 21:00:00', NOW(), 'Yes', 'Completed', 'Trò chuyện cùng ca sĩ nổi tiếng về sự nghiệp âm nhạc.'),
(2, 12, 'Hòa nhạc từ thiện', NULL, NULL, 'Nhà hát lớn', '2025-02-20 19:00:00', '2025-02-20 22:00:00', NOW(), 'Yes', 'Completed', 'Biểu diễn gây quỹ hỗ trợ trẻ em nghèo.'),

-- Code Warriors (Lập trình)
(3, 25, 'Hackathon 2025 - Code không ngủ', NULL, NULL, 'Hội trường D6', '2025-04-12 09:00:00', '2025-04-12 21:00:00', NOW(), 'Yes', 'Planned', 'Cuộc thi lập trình liên tục 12h.'),
(3, 27, 'Workshop: Lập trình Python từ A-Z', NULL, NULL, 'Phòng máy 101', '2025-04-18 14:00:00', '2025-04-18 17:00:00', NOW(), 'Yes', 'Planned', 'Học lập trình Python từ cơ bản đến nâng cao.'),
(3, 29, 'Thi đấu lập trình thuật toán', NULL, NULL, 'Hội trường C2', '2025-04-22 10:00:00', '2025-04-22 14:00:00', NOW(), 'Yes', 'Planned', 'Cuộc thi thử thách tư duy thuật toán.'),
(3, 22, 'Xây dựng website cá nhân trong 4 giờ', NULL, NULL, 'Phòng máy 202', '2025-03-28 14:00:00', '2025-03-28 18:00:00', NOW(), 'Yes', 'Ongoing', 'Hướng dẫn thiết kế và triển khai website cá nhân.'),
(3, 23, 'Chia sẻ: Hành trình trở thành lập trình viên', NULL, 'Lập trình viên E', 'Hội trường B1', '2025-05-15 16:00:00', '2025-05-15 18:00:00', NOW(), 'Yes', 'Planned', 'Câu chuyện thành công trong ngành lập trình.'),

-- Young Entrepreneurs (Kinh doanh)
(5, 44, 'Hội thảo: Gọi vốn khởi nghiệp', NULL, 'Nhà đầu tư D', 'Hội trường G7', '2025-04-30 18:00:00', '2025-04-30 20:30:00', NOW(), 'Yes', 'Planned', 'Hướng dẫn gọi vốn thành công.'),
(5, 43, 'Cuộc thi: Kế hoạch kinh doanh xuất sắc', NULL, NULL, 'Hội trường B3', '2025-03-28 09:00:00', '2025-03-28 16:00:00', NOW(), 'Yes', 'Ongoing', 'Các nhóm trình bày kế hoạch kinh doanh.'),
(5, 45, 'Giao lưu với CEO khởi nghiệp thành công', NULL, 'CEO E', 'Cà phê Startup', '2025-05-10 18:00:00', '2025-05-10 21:00:00', NOW(), 'Yes', 'Planned', 'Chia sẻ kinh nghiệm khởi nghiệp.'),
(5, 47, 'Xây dựng thương hiệu cá nhân', NULL, NULL, 'Hội trường A2', '2025-05-20 14:00:00', '2025-05-20 17:00:00', NOW(), 'Yes', 'Planned', 'Hướng dẫn xây dựng thương hiệu cá nhân mạnh mẽ.'),
(5, 49, 'Hành trình từ ý tưởng đến startup', NULL, NULL, 'Hội trường F4', '2025-03-25 10:00:00', '2025-03-25 12:00:00', NOW(), 'Yes', 'Completed', 'Cách biến ý tưởng thành startup thành công.');

-- insert project
INSERT INTO event (club_id, leader_id, name, avatar_url, guest, location, start_time, end_time, is_public, status, description, type) VALUES
-- 🏆 Champions FC (CLB Bóng đá)
(1, 5, 'Dự án Học viện Bóng đá Trẻ', NULL, 'HLV Park Hang Seo', 'Sân vận động Quốc gia', 
 '2025-03-25 08:00:00', '2025-06-30 18:00:00', 'No', 'Ongoing', 
 'Xây dựng hệ thống đào tạo trẻ chuyên nghiệp cho câu lạc bộ.', 'Project'),

(1, 7, 'Dự án Giải đấu Giao hữu', NULL, 'Cầu thủ Quang Hải', 'Trung tâm Thể thao Thành phố', 
 '2025-05-10 14:00:00', '2025-07-15 20:00:00', 'No', 'Planned', 
 'Tổ chức các trận giao hữu bóng đá nhằm nâng cao kỹ năng thi đấu.', 'Project'),

(1, 9, 'Dự án Tài trợ Trang phục Thi Đấu', NULL, 'Giám đốc Nike Việt Nam', 'Nhà tài trợ Nike', 
 '2025-06-01 10:00:00', '2025-09-01 18:00:00', 'No', 'Planned', 
 'Hợp tác với các thương hiệu lớn để tài trợ áo đấu.', 'Project'),
 
(1, 2, 'Dự án Xây dựng Cộng đồng Người Hâm Mộ', NULL, 'Fan Club', 'Online & Offline',
 '2024-07-01 10:00:00', '2024-12-31 22:00:00', 'No', 'Completed',
 'Xây dựng cộng đồng fan hâm mộ, tổ chức sự kiện và giao lưu.', 'Project'),


-- 🎵 Melody Hub (CLB Âm nhạc)
(2, 13, 'Dự án Sản xuất Album Nhạc Trẻ', NULL, 'Nhạc sĩ Đức Trí', 'Phòng thu Melody Studio', 
 '2025-03-15 10:00:00', '2025-06-30 22:00:00', 'No', 'Ongoing', 
 'Hỗ trợ các ca sĩ trẻ thu âm và phát hành album nhạc mới.', 'Project'),

(2, 15, 'Dự án Livestream Âm Nhạc', NULL, 'Ca sĩ Sơn Tùng M-TP', 'Hội trường Âm nhạc Đại học ABC', 
 '2025-04-20 19:00:00', '2025-08-01 22:00:00', 'No', 'Planned', 
 'Chuỗi chương trình biểu diễn âm nhạc trực tiếp trên nền tảng số.', 'Project'),

(2, 17, 'Dự án Cuộc Thi Sáng Tác Ca Khúc', NULL, 'Nhạc sĩ Huy Tuấn', 'Nhà hát Thành phố', 
 '2025-07-01 14:00:00', '2025-09-20 18:00:00', 'No', 'Planned', 
 'Tổ chức cuộc thi sáng tác ca khúc dành cho giới trẻ.', 'Project'),

-- 💻 Code Warriors (CLB Lập trình)
(3, 27, 'Dự án Ứng dụng Quản lý Task', NULL, 'Tech Lead Nguyễn Văn A', 'Văn phòng Code Warriors', 
 '2025-03-10 09:00:00', '2025-06-15 18:00:00', 'No', 'Ongoing', 
 'Xây dựng hệ thống quản lý công việc tối ưu cho các nhóm lập trình.', 'Project'),

(3, 28, 'Dự án Open Source Development', NULL, 'CEO Open Source B', 'Trung tâm Công nghệ Đại học XYZ', 
 '2025-05-01 08:30:00', '2025-09-01 19:00:00', 'No', 'Planned', 
 'Phát triển các dự án mã nguồn mở phục vụ cộng đồng lập trình viên.', 'Project'),

(3, 24, 'Dự án Hệ Thống Gợi Ý Code AI', NULL, 'Chuyên gia AI Nguyễn Văn C', 'Viện Nghiên cứu Trí Tuệ Nhân Tạo', 
 '2025-06-10 09:00:00', '2025-12-15 18:00:00', 'No', 'Planned', 
 'Xây dựng AI hỗ trợ lập trình viên viết code hiệu quả hơn.', 'Project'),

-- 🏢 Young Entrepreneurs (CLB Khởi nghiệp)
(5, 45, 'Dự án Startup Hackathon', NULL, 'Shark Phạm Thanh Hưng', 'Tòa nhà Khởi nghiệp A', 
 '2025-03-26 09:00:00', '2025-07-20 21:00:00', 'No', 'Ongoing', 
 'Sân chơi dành cho các startup trẻ tranh tài với ý tưởng kinh doanh.', 'Project'),

(5, 46, 'Dự án Hỗ trợ Doanh Nghiệp Trẻ', NULL, 'Chủ tịch Quỹ Đầu tư C', 'Trung tâm Hội nghị Quốc gia', 
 '2025-05-12 13:00:00', '2025-10-15 18:00:00', 'No', 'Planned', 
 'Chương trình hỗ trợ các doanh nghiệp khởi nghiệp tiếp cận nguồn vốn.', 'Project'),

(5, 47, 'Dự án Xây dựng Mạng Lưới Nhà Đầu Tư', NULL, 'Giám đốc Quỹ Đầu Tư D', 'Hội trường Tập đoàn XYZ', 
 '2025-06-15 10:00:00', '2025-11-30 18:00:00', 'No', 'Planned', 
 'Kết nối các nhà đầu tư và startup tiềm năng.', 'Project');



INSERT INTO finance (finance_name, event_id, club_id, member_id, approver_id, is_expense, amount, status, finance_date, last_updated, details) VALUES
-- Champions FC (Bóng đá)
('Phí đăng ký giải đấu', 1, 1, 3, 5, 'No', 5000000, 'Approve', '2025-04-01 10:00:00', NOW(), 'Thu phí đăng ký giải đấu nội bộ.'),
('Thu tiền bán áo đấu', NULL, 1, 7, 9, 'No', 8000000, 'Approve', '2025-04-05 12:00:00', NOW(), 'Bán áo đấu cho thành viên và cổ động viên.'),
('Chi thuê sân bóng', 2, 1, 3, 5, 'Yes', 3000000, 'Approve', '2025-04-08 15:00:00', NOW(), 'Thanh toán phí thuê sân cho buổi tập luyện.'),
('Chi mua bóng và dụng cụ', NULL, 1, 5, 7, 'Yes', 2000000, 'Approve', '2025-04-12 14:00:00', NOW(), 'Mua bóng và dụng cụ tập luyện mới.'),
('Thu tài trợ từ doanh nghiệp X', NULL, 1, 9, 11, 'No', 10000000, 'Approve', '2025-04-15 09:00:00', NOW(), 'Nhận tài trợ từ doanh nghiệp X.'),
('Chi tổ chức trại hè bóng đá', 4, 1, 11, 13, 'Yes', 6000000, 'Approve', '2025-04-20 14:00:00', NOW(), 'Chi phí tổ chức trại hè bóng đá trẻ.'),

-- Melody Hub (Âm nhạc)
('Thu phí thành viên tháng 4', NULL, 2, 10, 12, 'No', 3000000, 'Approve', '2025-04-02 09:30:00', NOW(), 'Phí thành viên hàng tháng.'),
('Chi thuê thiết bị âm thanh', 6, 2, 12, 14, 'Yes', 5000000, 'Approve', '2025-04-06 11:00:00', NOW(), 'Thuê thiết bị cho buổi biểu diễn.'),
('Thu tiền bán vé hòa nhạc', 8, 2, 14, 16, 'No', 12000000, 'Approve', '2025-04-10 18:00:00', NOW(), 'Bán vé cho buổi hòa nhạc từ thiện.'),
('Chi thuê sân khấu và ánh sáng', NULL, 2, 16, 18, 'Yes', 6000000, 'Approve', '2025-04-14 13:30:00', NOW(), 'Thanh toán chi phí sân khấu và ánh sáng.'),
('Nhận tài trợ từ công ty âm nhạc', NULL, 2, 18, 20, 'No', 15000000, 'Approve', '2025-04-18 16:00:00', NOW(), 'Tài trợ từ công ty âm nhạc lớn.'),
('Chi phí tổ chức đêm nhạc từ thiện', 10, 2, 20, 22, 'Yes', 7000000, 'Approve', '2025-04-25 19:00:00', NOW(), 'Chi phí thuê nhạc công và địa điểm.'),

-- Code Warriors (Lập trình)
('Thu phí tham gia Hackathon', 11, 3, 15, 17, 'No', 7000000, 'Approve', '2025-04-03 14:00:00', NOW(), 'Phí đăng ký Hackathon 2025.'),
('Chi mua hosting và domain', NULL, 3, 17, 19, 'Yes', 2500000, 'Approve', '2025-04-07 10:00:00', NOW(), 'Thanh toán chi phí hosting và domain.'),
('Nhận tài trợ từ công ty phần mềm', NULL, 3, 19, 21, 'No', 20000000, 'Approve', '2025-04-11 11:00:00', NOW(), 'Tài trợ từ công ty phần mềm A.'),
('Chi thuê diễn giả cho hội thảo', 12, 3, 21, 23, 'Yes', 4000000, 'Approve', '2025-04-15 14:00:00', NOW(), 'Trả phí mời diễn giả chuyên gia về AI.'),
('Thu lợi nhuận từ khóa học lập trình', NULL, 3, 23, 25, 'No', 5000000, 'Approve', '2025-04-19 17:00:00', NOW(), 'Lợi nhuận từ khóa học lập trình web.'),
('Chi phí tổ chức cuộc thi thuật toán', 13, 3, 25, 27, 'Yes', 4500000, 'Approve', '2025-04-23 10:00:00', NOW(), 'Tổ chức cuộc thi lập trình thuật toán.'),

-- Young Entrepreneurs (Kinh doanh)
('Thu phí tham gia hội thảo gọi vốn', 16, 5, 21, 23, 'No', 5000000, 'Approve', '2025-04-30 18:00:00', NOW(), 'Phí tham dự hội thảo gọi vốn khởi nghiệp.'),
('Chi phí tổ chức cuộc thi kinh doanh', 17, 5, 23, 25, 'Yes', 8000000, 'Approve', '2025-05-05 09:00:00', NOW(), 'Chi phí tổ chức cuộc thi kế hoạch kinh doanh.'),
('Nhận tài trợ từ doanh nghiệp startup', NULL, 5, 25, 27, 'No', 12000000, 'Approve', '2025-05-08 14:00:00', NOW(), 'Tài trợ từ một doanh nghiệp startup.'),
('Chi phí thuê diễn giả cho workshop', 19, 5, 27, 29, 'Yes', 3000000, 'Approve', '2025-05-12 16:00:00', NOW(), 'Trả phí diễn giả cho hội thảo về thương hiệu cá nhân.'),
('Thu tiền từ khóa học khởi nghiệp', NULL, 5, 29, 31, 'No', 7000000, 'Approve', '2025-05-15 10:00:00', NOW(), 'Doanh thu từ khóa học kinh doanh.'),
('Chi phí tổ chức buổi giao lưu với CEO', 18, 5, 31, 33, 'Yes', 4000000, 'Approve', '2025-05-20 18:00:00', NOW(), 'Tổ chức buổi giao lưu với CEO khởi nghiệp thành công.');

UPDATE finance f
JOIN club_member cm ON cm.club_id = f.club_id AND cm.role_id = 4
SET f.approver_id = cm.user_id
WHERE f.club_id IS NOT NULL;

INSERT INTO user_role (user_id, role_id, status) VALUES
(1, 6, 'Active'),  (2, 6, 'Active'),  (3, 6, 'Active'),  (4, 6, 'Active'),  (5, 6, 'Active'),
(6, 6, 'Active'),  (7, 6, 'Active'),  (8, 6, 'Active'),  (9, 6, 'Active'),  (10, 6, 'Active'),

(11, 6, 'Active'), (12, 6, 'Active'), (13, 6, 'Active'), (14, 6, 'Active'), (15, 6, 'Active'),
(16, 6, 'Active'), (17, 6, 'Active'), (18, 6, 'Active'), (19, 6, 'Active'), (20, 6, 'Active'),

(21, 6, 'Active'), (22, 6, 'Active'), (23, 6, 'Active'), (24, 6, 'Active'), (25, 6, 'Active'),
(26, 6, 'Active'), (27, 6, 'Active'), (28, 6, 'Active'), (29, 6, 'Active'), (30, 6, 'Active'),

(31, 6, 'Active'), (32, 6, 'Active'), (33, 6, 'Active'), (34, 6, 'Active'), (35, 6, 'Active'),
(36, 6, 'Active'), (37, 6, 'Active'), (38, 6, 'Active'), (39, 6, 'Active'), (40, 6, 'Active'),

(41, 6, 'Active'), (42, 6, 'Active'), (43, 6, 'Active'), (44, 6, 'Active'), (45, 6, 'Active'),
(46, 6, 'Active'), (47, 6, 'Active'), (48, 6, 'Active'), (49, 6, 'Active'), (50, 6, 'Active');


INSERT INTO post (user_id, club_id, tittle, image_url, post_at, status, description, category_id) VALUES
(1, 1, 'Chào mừng thành viên mới!', NULL, NOW(), 'Published ', 'Chào mừng các bạn đến với Champions FC. Hãy cùng nhau rèn luyện và phát triển nhé!', 7),
(1, 2, 'Lịch tập tháng 3', 'https://example.com/training_schedule.jpg', NOW(), 'Published ', 'Dưới đây là lịch tập luyện của câu lạc bộ trong tháng 3. Các bạn nhớ theo dõi!', 8),
(11, 2, 'Buổi biểu diễn cuối tuần này!', NULL, NOW(), 'Unpublished ', 'Melody Hub sẽ tổ chức một buổi biểu diễn vào tối thứ 7. Mọi người đừng bỏ lỡ!', 8),
(12, 2, 'Tuyển thành viên mới', NULL, NOW(), 'Unpublished ', 'Câu lạc bộ đang tìm kiếm các giọng ca mới! Đăng ký ngay để tham gia thử giọng.', 8),
(21, 3, 'Workshop lập trình miễn phí', 'https://example.com/python_workshop.jpg', NOW(), 'Published', 'Code Warriors tổ chức workshop lập trình Python vào cuối tuần này. Đăng ký ngay!', 9),
(22, 3, 'Hackathon tháng này', NULL, NOW(), 'Draft', 'Hãy tham gia cuộc thi lập trình lớn nhất tháng này để thử sức và giành giải thưởng!', 9),
(31, 4, 'Lớp học vẽ miễn phí', 'https://example.com/art_class.jpg', NOW(), 'Published', 'Câu lạc bộ Creative Minds mở lớp dạy vẽ cơ bản cho người mới bắt đầu.', 10),
(32, 4, 'Triển lãm tranh cá nhân', NULL, NOW(), 'Draft', 'Một số thành viên của câu lạc bộ sẽ tổ chức triển lãm tranh vào tuần tới. Hãy đến ủng hộ nhé!', 10),
(41, 5, 'Khóa học khởi nghiệp', NULL, NOW(), 'Unpublished ', 'Young Entrepreneurs tổ chức khóa học giúp bạn hiểu rõ về startup và kinh doanh.',11),
(42, 5, 'Gặp gỡ doanh nhân thành công', NULL, NOW(), 'Published', 'Một buổi trò chuyện đặc biệt với doanh nhân nổi tiếng trong ngành công nghệ.',11);



INSERT INTO task (event_id, club_id, title, assigner_id, assignee_id, deadline, status, description) VALUES
(1, 1, 'Chuẩn bị sân vận động', 3, 10, '2025-04-09 18:00:00', 'Pending', 'Chuẩn bị sân bãi, khán đài trước ngày thi đấu.'),
(2, 1, 'Chuẩn bị hội trường', 5, 7, '2025-04-14 18:00:00', 'In Progress', 'Sắp xếp hội trường, kiểm tra âm thanh, ánh sáng.'),
(3, 1, 'Chuẩn bị slide trình bày', 7, 9, '2025-04-19 12:00:00', 'Pending', 'Thiết kế slide cho workshop bóng đá.'),
(4, 1, 'Tổ chức đăng ký tham gia', 9, 6, '2025-04-29 20:00:00', 'Pending', 'Mở form đăng ký và xác nhận danh sách tham gia.'),
(5, 1, 'Chuẩn bị áo đấu gây quỹ', 6, 3, '2025-05-08 18:00:00', 'Pending', 'Chuẩn bị và phân phát áo đấu cho sự kiện thiện nguyện.'),

(6, 2, 'Chuẩn bị sân khấu', 16, 12, '2025-04-12 15:00:00', 'In Progress', 'Set up sân khấu, ánh sáng và âm thanh.'),
(7, 2, 'Chuẩn bị tài liệu workshop', 16, 14, '2025-04-17 18:00:00', 'Pending', 'In ấn tài liệu hướng dẫn sáng tác nhạc.'),
(8, 2, 'Xây dựng tiêu chí chấm thi', 13, 14, '2025-04-21 12:00:00', 'Pending', 'Chuẩn bị tiêu chí đánh giá thí sinh dự thi.'),
(9, 2, 'Chuẩn bị câu hỏi giao lưu', 15, 16, '2025-05-04 14:00:00', 'Pending', 'Soạn câu hỏi để giao lưu với nghệ sĩ.'),
(10, 2, 'Chuẩn bị danh sách bài hát', 15, 17, '2025-05-19 17:00:00', 'Pending', 'Chọn và sắp xếp bài hát cho buổi hòa nhạc.'),

(11, 3, 'Chuẩn bị đề Hackathon', 21, 22, '2025-04-11 20:00:00', 'Pending', 'Tạo danh sách thử thách cho Hackathon.'),
(12, 3, 'Cài đặt môi trường lập trình', 28, 29, '2025-04-17 10:00:00', 'Pending', 'Chuẩn bị hệ thống và phần mềm cho workshop.'),
(13, 3, 'Chuẩn bị tài liệu thi thuật toán', 25, 27, '2025-04-21 14:00:00', 'Pending', 'Xây dựng bộ đề thi lập trình thuật toán.'),
(14, 3, 'Tạo template website', 22, 27, '2025-05-09 18:00:00', 'Pending', 'Tạo bộ giao diện mẫu cho workshop web.'),
(15, 3, 'Chuẩn bị bài thuyết trình', 29, 21, '2025-05-14 12:00:00', 'Pending', 'Soạn nội dung về hành trình lập trình viên.'),

(16, 5, 'Chuẩn bị nội dung hội thảo', 44, 43, '2025-04-29 15:00:00', 'Pending', 'Tổng hợp thông tin về gọi vốn khởi nghiệp.'),
(17, 5, 'Chuẩn bị tài liệu thi kinh doanh', 42, 44, '2025-05-04 17:00:00', 'Pending', 'Xây dựng bộ đề cho cuộc thi kế hoạch kinh doanh.'),
(18, 5, 'Chuẩn bị slide giao lưu', 43, 42, '2025-05-09 16:00:00', 'Pending', 'Chuẩn bị nội dung buổi chia sẻ với CEO.'),
(19, 5, 'Tạo nội dung bài giảng thương hiệu', 43, 49, '2025-05-19 11:00:00', 'Pending', 'Soạn bài hướng dẫn xây dựng thương hiệu cá nhân.'),
(20, 5, 'Chuẩn bị hồ sơ dự án startup', 49, 47, '2025-05-24 14:00:00', 'Pending', 'Tổng hợp tài liệu về các dự án startup.'),

(21, 1, 'Xây dựng giáo trình đào tạo', 5, 6, '2025-05-01 18:00:00', 'In Progress', 'Chuẩn bị nội dung huấn luyện cho học viện.'),
(21, 1, 'Tuyển sinh cầu thủ trẻ', 5, 8, '2025-05-15 18:00:00', 'Pending', 'Tổ chức tuyển sinh cầu thủ tiềm năng.'),
(21, 1, 'Ký kết hợp tác với đối tác', 5, 10, '2025-06-01 12:00:00', 'Pending', 'Liên hệ đối tác để phát triển học viện.'),

(22, 1, 'Lên lịch thi đấu', 7, 9, '2025-06-01 18:00:00', 'Pending', 'Sắp xếp lịch thi đấu giao hữu.'),
(22, 1, 'Chuẩn bị sân bãi', 7, 6, '2025-05-25 17:00:00', 'Completed', 'Dọn dẹp và trang trí sân vận động.'),
(22, 1, 'Mời đội bóng tham gia', 7, 8, '2025-05-20 12:00:00', 'Pending', 'Gửi lời mời đến các đội bóng tham dự.'),

(23, 1, 'Thiết kế mẫu áo đấu', 9, 10, '2025-06-15 18:00:00', 'Pending', 'Làm việc với designer để thiết kế mẫu áo.'),
(23, 1, 'Tìm kiếm nhà tài trợ', 9, 5, '2025-07-01 12:00:00', 'Pending', 'Thương thảo với các công ty tài trợ.'),
(23, 1, 'Phát áo cho đội bóng', 9, 7, '2025-08-01 18:00:00', 'Pending', 'Phát áo đấu mới cho các thành viên đội bóng.'),

-- 🎵 Melody Hub (CLB Âm nhạc)
(24, 2, 'Thu âm bài hát đầu tiên', 2, 3, '2025-04-10 18:00:00', 'In Progress', 'Thu âm ca khúc chủ đề cho album.'),
(24, 2, 'Hoàn thiện mix & master', 2, 4, '2025-05-15 12:00:00', 'Pending', 'Chỉnh sửa âm thanh chuyên sâu cho album.'),
(24, 2, 'Ra mắt album trực tuyến', 2, 6, '2025-06-30 20:00:00', 'Pending', 'Phát hành album trên các nền tảng số.'),

(25, 2, 'Chuẩn bị kịch bản chương trình', 13, 12, '2025-04-25 12:00:00', 'Pending', 'Viết nội dung và flow livestream.'),
(25, 2, 'Cài đặt hệ thống livestream', 13, 18, '2025-05-05 18:00:00', 'In Progress', 'Kiểm tra đường truyền và thiết bị.'),
(25, 2, 'Mời khách mời đặc biệt', 13, 16, '2025-06-01 20:00:00', 'Pending', 'Liên hệ ca sĩ, nhạc sĩ để tham gia chương trình.'),

(26, 2, 'Chuẩn bị đề thi sáng tác', 15, 14, '2025-07-10 12:00:00', 'Pending', 'Tạo bộ đề thi và quy tắc cuộc thi.'),
(26, 2, 'Tổ chức vòng sơ tuyển', 15, 12, '2025-08-01 18:00:00', 'Pending', 'Chấm điểm và chọn ra các bài hát tiềm năng.'),
(26, 2, 'Tổ chức đêm chung kết', 15, 18, '2025-09-15 20:00:00', 'Pending', 'Dàn dựng sân khấu và chương trình biểu diễn.'),

(27, 3, 'Thiết kế giao diện', 17, 19, '2025-04-20 18:00:00', 'In Progress', 'Thiết kế UI/UX cho ứng dụng.'),
(27, 3, 'Phát triển backend', 17, 12, '2025-05-10 12:00:00', 'Pending', 'Xây dựng API và database.'),
(27, 3, 'Test hệ thống', 17, 13, '2025-06-05 18:00:00', 'Pending', 'Kiểm thử hệ thống quản lý task.'),

(28, 3, 'Xây dựng repo GitHub', 29, 27, '2025-05-10 12:00:00', 'Pending', 'Khởi tạo kho lưu trữ dự án.'),
(28, 3, 'Code tính năng chính', 29, 24, '2025-07-01 18:00:00', 'Pending', 'Phát triển các chức năng chính.'),
(28, 3, 'Viết tài liệu hướng dẫn', 29, 23, '2025-08-15 20:00:00', 'Pending', 'Soạn thảo hướng dẫn sử dụng.'),

(29, 3, 'Phát triển model AI', 28, 25, '2025-07-20 18:00:00', 'Pending', 'Xây dựng mô hình AI hỗ trợ lập trình.'),
(29, 3, 'Tích hợp với IDE', 28, 29, '2025-09-10 18:00:00', 'Pending', 'Kết nối AI với Visual Studio Code.'),
(29, 3, 'Đánh giá và tối ưu', 25, 27, '2025-10-20 20:00:00', 'Pending', 'Cải thiện độ chính xác của AI.'),

-- 🏢 Young Entrepreneurs (CLB Khởi nghiệp)
(30, 5, 'Chuẩn bị địa điểm tổ chức', 22, 27, '2025-04-25 18:00:00', 'In Progress', 'Đặt địa điểm hackathon.'),
(30, 5, 'Mời ban giám khảo', 24, 29, '2025-05-10 12:00:00', 'Pending', 'Liên hệ các nhà đầu tư làm giám khảo.'),
(30, 5, 'Tổ chức thi đấu', 27, 24, '2025-07-15 18:00:00', 'Pending', 'Điều phối các đội thi và chấm điểm.'),

(31, 5, 'Xây dựng chương trình hỗ trợ', 47, 49, '2025-06-10 12:00:00', 'Pending', 'Tạo gói hỗ trợ tài chính cho startup.'),
(31, 5, 'Tổ chức workshop khởi nghiệp', 47, 45, '2025-08-01 18:00:00', 'Pending', 'Chuẩn bị nội dung và mời diễn giả.'),
(31, 5, 'Kết nối startup với quỹ đầu tư', 41, 45, '2025-09-15 20:00:00', 'Pending', 'Tổ chức buổi gặp mặt startup và nhà đầu tư.');


INSERT INTO registration (event_id, register_id, full_name, email, mobile, status) VALUES
-- Đăng ký sự kiện của Champions FC
(1, 13, 'Nguyễn Văn A', 'vana@example.com', '0987654321', 'Confirmed'),
(1, NULL, 'Trần Thị B', 'thib@example.com', '0976543210', 'Pending'),
(2, 25, 'Lê Văn C', 'levanc@example.com', '0965432109', 'Confirmed'),
(3, NULL, 'Phạm Văn D', 'phamvand@example.com', '0954321098', 'Pending'),
(4, 39, 'Đặng Thị E', 'dangthie@example.com', '0943210987', 'Confirmed'),
(5, NULL, 'Hoàng Văn F', 'hoangvanf@example.com', '0932109876', 'Pending'),

-- Đăng ký sự kiện của Melody Hub
(6, 8, 'Bùi Thị G', 'buithig@example.com', '0921098765', 'Confirmed'),
(6, NULL, 'Lý Văn H', 'lyvanh@example.com', '0910987654', 'Pending'),
(7, 30, 'Ngô Thị I', 'ngothii@example.com', '0909876543', 'Confirmed'),
(8, NULL, 'Dương Văn J', 'duongvanj@example.com', '0898765432', 'Pending'),
(9, 44, 'Tô Thị K', 'tothik@example.com', '0887654321', 'Confirmed'),
(10, NULL, 'Cao Văn L', 'caovanl@example.com', '0876543210', 'Pending'),

-- Đăng ký sự kiện của Code Warriors
(11, 15, 'Lương Văn M', 'luongvanm@example.com', '0865432109', 'Confirmed'),
(12, NULL, 'Châu Thị N', 'chauthin@example.com', '0854321098', 'Pending'),
(13, 19, 'Đoàn Văn O', 'doanvano@example.com', '0843210987', 'Confirmed'),
(14, NULL, 'Đinh Thị P', 'dinhthip@example.com', '0832109876', 'Pending'),
(15, 3, 'Hồ Văn Q', 'hovanq@example.com', '0821098765', 'Confirmed'),
(15, NULL, 'Phan Thị R', 'phanthir@example.com', '0810987654', 'Pending'),

-- Đăng ký sự kiện của Young Entrepreneurs
(16, 1, 'Võ Văn S', 'vovans@example.com', '0809876543', 'Confirmed'),
(17, NULL, 'Trịnh Thị T', 'trinhthit@example.com', '0798765432', 'Pending'),
(18, 15, 'Quách Văn U', 'quachvanu@example.com', '0787654321', 'Confirmed'),
(19, NULL, 'Kiều Thị V', 'kieuthiv@example.com', '0776543210', 'Pending'),
(20, 29, 'Đỗ Văn X', 'dovanx@example.com', '0765432109', 'Confirmed'),
(20, NULL, 'Bạch Thị Y', 'bachthiy@example.com', '0754321098', 'Pending');

INSERT INTO project_event_member (user_id, project_id, status) VALUES
-- 🏆 Champions FC (CLB Bóng đá)
(5, 21, 'Active'),
(6, 21, 'Active'),
(8, 21, 'Active'),
(10, 21, 'Inactive'),

(7, 22, 'Active'),
(6, 22, 'Active'),
(8, 22, 'Active'),
(9, 22, 'Inactive'),

(9, 23, 'Active'),
(5, 23, 'Active'),
(7, 23, 'Active'),
(10, 23, 'Inactive'),

(2, 24, 'Active'),
(3, 24, 'Active'),
(4, 24, 'Active'),
(6, 24, 'Inactive'),


-- 🎵 Melody Hub (CLB Âm nhạc)

(13, 25, 'Active'),
(12, 25, 'Active'),
(16, 25, 'Active'),
(18, 25, 'Inactive'),

(15, 26, 'Active'),
(12, 26, 'Active'),
(14, 26, 'Active'),
(18, 26, 'Inactive'),

(17, 27, 'Active'),
(19, 27, 'Active'),
(12, 27, 'Active'),
(13, 27, 'Inactive'),

-- 💻 Code Warriors (CLB Lập trình)
(27, 28, 'Active'),
(29, 28, 'Active'),
(24, 28, 'Active'),
(23, 28, 'Inactive'),

(28, 29, 'Active'),
(25, 29, 'Active'),
(29, 29, 'Active'),
(27, 29, 'Inactive'),

(24, 30, 'Active'),
(27, 30, 'Active'),
(29, 30, 'Active'),
(22, 30, 'Inactive'),


-- 🏢 Young Entrepreneurs (CLB Khởi nghiệp)
(45, 31,  'Active'),
(49, 31, 'Active'),
(47, 31,  'Active'),
(41, 31,  'Inactive'),

(46, 32,  'Active'),
(45, 32, 'Active'),
(44, 32,  'Active'),
(43, 32,  'Inactive'),

(47, 33,  'Active'),
(43, 33, 'Active'),
(42, 33,  'Active'),
(48, 33,  'Inactive');

INSERT INTO project_event_member (user_id, event_id, status) VALUES
(3, 1, 'Active'),
(6, 1, 'Active'),
(8, 1, 'Active'),
(10, 1, 'Inactive'),

(5, 2, 'Active'),
(7, 2, 'Active'),
(6, 2, 'Active'),
(8, 2, 'Active'),
(9, 2, 'Inactive'),

(7, 3, 'Active'),
(9, 3, 'Active'),
(5, 3, 'Active'),
(10, 3, 'Inactive'),

(9, 4, 'Active'),
(5, 4, 'Active'),
(7, 4, 'Active'),
(6, 4, 'Active'),
(8, 4, 'Active'),

(8, 5, 'Active'),
(2, 5, 'Active'),
(3, 5, 'Active'),
(7, 5, 'Active'),
(6, 5, 'Inactive'),

(18, 6, 'Active'),
(12, 6, 'Active'),
(16, 6, 'Active'),

(16, 7, 'Active'),
(14, 7, 'Active'),
(12, 7, 'Active'),

(12, 8, 'Active'),
(13, 8, 'Active'),
(14, 8, 'Inactive'),

(14, 9, 'Active'),
(16, 9, 'Active'),
(15, 9, 'Active'),
(17, 9, 'Active'),

(12, 10, 'Active'),
(16, 10, 'Active'),
(15, 10, 'Active'),
(17, 10, 'Active'),

(25, 11, 'Active'),
(22, 11, 'Active'),
(23, 11, 'Active'),
(24, 11, 'Active'),
(21, 11, 'Active'),

(27, 12, 'Active'),
(28, 12, 'Active'),
(29, 12, 'Active'),
(25, 12, 'Active'),
(23, 12, 'Active'),

(29, 13, 'Active'),
(25, 13, 'Active'),
(27, 13, 'Active'),
(21, 13, 'Active'),

(22, 14, 'Active'),
(25, 14, 'Active'),
(27, 14, 'Active'),
(29, 14, 'Active'),

(23, 15, 'Active'),
(21, 15, 'Active'),
(29, 15, 'Active'),
(27, 15, 'Active'),


(44, 16, 'Active'),
(43, 16, 'Active'),
(45, 16, 'Active'),
(47, 16, 'Active'),
(49, 16, 'Active'),

(43, 17, 'Active'),
(42, 17, 'Active'),
(44, 17, 'Active'),
(47, 17, 'Active'),
(49, 17, 'Active'),

(45, 18, 'Active'),
(43, 18, 'Active'),
(41, 18, 'Active'),
(42, 18, 'Active'),
(49, 18, 'Active'),

(47, 19, 'Active'),
(43, 19, 'Active'),
(45, 19, 'Active'),
(49, 19, 'Active'),

(49, 20, 'Active'),
(43, 20, 'Active'),
(45, 20, 'Active'),
(47, 20, 'Active');



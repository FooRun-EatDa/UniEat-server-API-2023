CREATE TABLE IF NOT EXISTS `member_base` (
   `member_id` varchar(200) NOT NULL AUTO_INCREMENT COMMENT '회원 고유 ID',
   `member_password` varchar(255) DEFAULT NULL COMMENT '회원 비밀번호',
   `last_sign_in_at` datetime DEFAULT NULL COMMENT '최종 로그인 일시',
   `manage_status` varchar(10) DEFAULT NULL COMMENT '계정 상태 관리',
   `member_role` varchar(10) DEFAULT 'GUEST' COMMENT '계정 권한 정보',
   `locked_date` datetime DEFAULT NULL COMMENT '계정 잠금 기간',
   `expired_date` datetime DEFAULT NULL COMMENT '계정 만료 기간',
   `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
   `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
   PRIMARY KEY (`member_id`),
   UNIQUE KEY `UK_email` (`member_email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT '회원 기본정보'
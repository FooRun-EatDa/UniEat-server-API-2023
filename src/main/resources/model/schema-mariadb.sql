CREATE TABLE `member_base` (
    `member_provider` varchar(20) NOT NULL COMMENT '회원 소셜로그인 가입 공급자',
    `member_id` varchar(200) NOT NULL COMMENT '회원 고유 ID',
    `refresh_token` varchar(500) DEFAULT NULL COMMENT '회원 refresh token',
    `last_sign_in_at` datetime DEFAULT NULL COMMENT '최종 로그인 일시',
    `manage_status` varchar(10) DEFAULT NULL COMMENT '계정 상태 관리',
    `member_role` varchar(10) DEFAULT 'GUEST' COMMENT '계정 권한 정보',
    `locked_date` datetime DEFAULT NULL COMMENT '계정 잠금 기간',
    `expired_date` datetime DEFAULT NULL COMMENT '계정 만료 기간',
    `created_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
    `modified_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '수정일시',
    PRIMARY KEY (`member_provider`, `member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='회원 기본정보'
;

CREATE TABLE `member_mypage` (
    `member_provider` varchar(20) NOT NULL COMMENT '회원 소셜로그인 가입 공급자',
    `member_id` varchar(200) NOT NULL COMMENT '회원 고유 ID',
    `member_nickname` varchar(255) DEFAULT NULL COMMENT '회원 닉네임',
    `member_profile_image` varchar(255) DEFAULT NULL COMMENT '회원 프로필사진',
    `created_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
    `modified_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '수정일시',
    PRIMARY KEY (`member_provider`, `member_id`),
    UNIQUE KEY `member_nickname_UNIQUE` (`member_nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='회원 마이페이지 정보'
;
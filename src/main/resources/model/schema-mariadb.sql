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
    `member_nickname` varchar(50) DEFAULT NULL COMMENT '회원 닉네임',
    `member_profile_image` varchar(500) DEFAULT NULL COMMENT '회원 프로필사진',
    `member_introduction` varchar(50) DEFAULT NULL COMMENT '자기소개 문구',
    `created_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
    `modified_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '수정일시',
    PRIMARY KEY (`member_provider`,`member_id`),
    UNIQUE KEY `member_nickname_UNIQUE` (`member_nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='회원 마이페이지 정보'
;

CREATE TABLE `category_food` (
    `category_id` varchar(3) NOT NULL COMMENT '분류 ID',
    `category_name` varchar(50) DEFAULT NULL COMMENT '항목 이름',
    PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='음식 분류항목'
;

CREATE TABLE `restaurant_base` (
    `restaurant_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '식당 ID',
    `business_number` varchar(10) DEFAULT NULL COMMENT '사업자번호',
    `restaurant_name` varchar(200) DEFAULT NULL COMMENT '식당 이름',
    `restaurant_title_image_seq` int(11) DEFAULT NULL COMMENT '식당 대표이미지 sequence',
    `restaurant_introduction` varchar(100) DEFAULT NULL COMMENT '식당 한줄 소개글',
    `restaurant_content` varchar(500) DEFAULT NULL COMMENT '식당 상세 설명',
    `restaurant_address_base` varchar(100) DEFAULT NULL COMMENT '식당 주소 기본',
    `restaurant_address_detail` varchar(100) DEFAULT NULL COMMENT '식당 주소 상세',
    `restaurant_latitude` decimal(13,10) DEFAULT NULL COMMENT '식당위치 위도',
    `restaurant_longitude` decimal(13,10) DEFAULT NULL COMMENT '식당위치 경도',
    `restaurant_call_number` varchar(11) DEFAULT NULL COMMENT '식당 전화번호',
    `restaurant_operation_time` varchar(300) DEFAULT NULL COMMENT '식당 운영시간 설명',
    `manage_status` varchar(10) DEFAULT NULL COMMENT '식당 상태 관리',
    `created_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
    `modified_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '수정일시',
    PRIMARY KEY (`restaurant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='식당 기본정보'
;

CREATE TABLE `restaurant_category_food` (
    `restaurant_id` bigint(20) NOT NULL COMMENT '식당 ID',
    `food_category_id` varchar(3) NOT NULL COMMENT '음식 분류항목',
    PRIMARY KEY (`restaurant_id`, `food_category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='식당 보유 음식 분류항목'
;

CREATE TABLE `restaurant_image` (
    `restaurant_id` bigint(20) NOT NULL COMMENT '식당 ID',
    `seq` int(11) DEFAULT NULL COMMENT '이미지 순서',
    `file_url` varchar(500) DEFAULT NULL COMMENT '파일 경로',
    `created_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
    `modified_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '수정일시',
    PRIMARY KEY (`restaurant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='식당 이미지 관리'
;

CREATE TABLE `menu_base` (
    `restaurant_id` bigint(20) NOT NULL COMMENT '식당 ID',
    `menu_id` bigint(20) NOT NULL COMMENT '메뉴 ID',
    `seq` int(11) DEFAULT NULL COMMENT '노출 순서',
    `menu_name` varchar(50) DEFAULT NULL COMMENT '메뉴 이름',
    `menu_image_url` varchar(500) DEFAULT NULL COMMENT '메뉴 이미지',
    `menu_price` bigint(20) DEFAULT NULL COMMENT '메뉴 가격(단위: 원)',
    `menu_category_id` varchar(3) DEFAULT NULL COMMENT '메뉴 항목 ID',
    `menu_description` varchar(200) DEFAULT NULL COMMENT '메뉴 설명',
    `menu_recommend` tinyint(1) DEFAULT NULL COMMENT '메뉴 추천 여부',
    `created_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
    `modified_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '수정일시',
    PRIMARY KEY (`restaurant_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='음식 메뉴 기본정보'
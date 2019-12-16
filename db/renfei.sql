SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `CALENDAR_NAME` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('clusteredScheduler', 'UpdatePostPageRankTrigger', 'PostTriggerGroup', '0 0/3 * * * ? ', 'Asia/Shanghai');
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ENTRY_ID` varchar(95) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_NAME` varchar(190) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `JOB_GROUP` varchar(190) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_NAME` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_GROUP` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `DESCRIPTION` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) COLLATE utf8mb4_unicode_ci NOT NULL,
  `IS_DURABLE` varchar(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of QRTZ_JOB_DETAILS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('clusteredScheduler', 'UpdatePostPageRankJob', 'Post', NULL, 'net.renfei.core.task.UpdatePostPageRankJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F40000000000010770800000010000000007800);
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `LOCK_NAME` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_LOCKS` VALUES ('clusteredScheduler', 'STATE_ACCESS');
INSERT INTO `QRTZ_LOCKS` VALUES ('clusteredScheduler', 'TRIGGER_ACCESS');
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of QRTZ_SCHEDULER_STATE
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_SCHEDULER_STATE` VALUES ('clusteredScheduler', 'NeilMacBookPro1576224543965', 1576225585987, 10000);
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `STR_PROP_1` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `STR_PROP_2` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `STR_PROP_3` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_NAME` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_GROUP` varchar(190) COLLATE utf8mb4_unicode_ci NOT NULL,
  `DESCRIPTION` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_TYPE` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_TRIGGERS` VALUES ('clusteredScheduler', 'UpdatePostPageRankTrigger', 'PostTriggerGroup', 'UpdatePostPageRankJob', 'Post', NULL, 1576225620000, 1576225440000, 5, 'WAITING', 'CRON', 1575262409000, 0, NULL, 0, '');
COMMIT;

-- ----------------------------
-- Table structure for t_account
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `account` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账户',
  `password` varchar(71) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `totp` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '两步验证',
  `is_open_otp` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否启用两步验证',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态',
  `registration_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `tries` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '密码尝试次数',
  `lock_time` datetime DEFAULT NULL COMMENT '锁定时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_account` (`account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_account
-- ----------------------------
BEGIN;
INSERT INTO `t_account` VALUES (1, 'renfei', 'sha1:64000:18:rZNN9/MlvYHaiVoKioG7G0E7h1gbqUj/:NI4eEfylEvrElpkKIq8Atf9g', 'EV3RYRYEKY7RD4PZ', 0, 1, '2019-07-11 19:29:08', 0, '2019-08-10 13:14:43');
COMMIT;

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `type_id` bigint(20) unsigned NOT NULL,
  `en_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `zh_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `featured_image` text COLLATE utf8mb4_unicode_ci COMMENT '特色图像',
  PRIMARY KEY (`id`),
  KEY `fk_category_type` (`type_id`),
  CONSTRAINT `fk_category_type` FOREIGN KEY (`type_id`) REFERENCES `t_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_category
-- ----------------------------
BEGIN;
INSERT INTO `t_category` VALUES (1, 1, 'default', '文章默认分类', 'https://cdn.neilren.com/neilren4j/upload/557e9977103248b789c81f1cb082c091.jpeg');
INSERT INTO `t_category` VALUES (2, 1, 'develop', '编程开发', 'https://cdn.neilren.com/neilren4j/upload/557e9977103248b789c81f1cb082c091.jpeg');
INSERT INTO `t_category` VALUES (3, 1, 'servers', '服务器端', 'https://cdn.neilren.com/neilren4j/upload/557e9977103248b789c81f1cb082c091.jpeg');
INSERT INTO `t_category` VALUES (4, 1, 'bitcoin', 'bitcoin', 'https://cdn.neilren.com/neilren4j/upload/557e9977103248b789c81f1cb082c091.jpeg');
INSERT INTO `t_category` VALUES (5, 1, 'news', '业界新闻', 'https://cdn.neilren.com/neilren4j/upload/557e9977103248b789c81f1cb082c091.jpeg');
INSERT INTO `t_category` VALUES (6, 2, 'page', '页面默认分类', 'https://cdn.neilren.com/neilren4j/upload/557e9977103248b789c81f1cb082c091.jpeg');
INSERT INTO `t_category` VALUES (7, 3, 'vlog', 'Vlog', 'https://cdn.neilren.com/neilren4j/upload/557e9977103248b789c81f1cb082c091.jpeg');
INSERT INTO `t_category` VALUES (8, 4, 'album', '默认相册分类', 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0881.JPG');
INSERT INTO `t_category` VALUES (9, 5, 'thriller', '恐怖电影', NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_code
-- ----------------------------
DROP TABLE IF EXISTS `t_code`;
CREATE TABLE `t_code` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `file_name` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件名称',
  `code_type` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '代码类型',
  `description` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '描述',
  `code` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '代码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for t_comments
-- ----------------------------
DROP TABLE IF EXISTS `t_comments`;
CREATE TABLE `t_comments` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `target_id` bigint(20) unsigned NOT NULL COMMENT '目标ID',
  `type_id` bigint(20) unsigned NOT NULL COMMENT '目标的分类ID',
  `author` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '作者名称',
  `author_email` text COLLATE utf8mb4_unicode_ci COMMENT '作者邮箱',
  `author_url` text COLLATE utf8mb4_unicode_ci COMMENT '作者链接',
  `author_IP` text COLLATE utf8mb4_unicode_ci COMMENT '作者IP',
  `author_address` text COLLATE utf8mb4_unicode_ci COMMENT '作者物理地址',
  `addtime` datetime NOT NULL COMMENT '评论时间',
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '评论内容',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `parent_id` bigint(20) unsigned DEFAULT NULL COMMENT '父级评论ID',
  `is_owner` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否是官方回复',
  PRIMARY KEY (`id`),
  KEY `fk_comment_type` (`type_id`),
  KEY `fk_comments` (`parent_id`),
  CONSTRAINT `fk_comment_type` FOREIGN KEY (`type_id`) REFERENCES `t_type` (`id`),
  CONSTRAINT `fk_comments` FOREIGN KEY (`parent_id`) REFERENCES `t_comments` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for t_library
-- ----------------------------
DROP TABLE IF EXISTS `t_library`;
CREATE TABLE `t_library` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `menu_id` bigint(20) unsigned NOT NULL COMMENT '所属菜单ID',
  `resource_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资源名称',
  PRIMARY KEY (`id`),
  KEY `fk_library_menu` (`menu_id`),
  CONSTRAINT `fk_library_menu` FOREIGN KEY (`menu_id`) REFERENCES `t_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for t_library_details
-- ----------------------------
DROP TABLE IF EXISTS `t_library_details`;
CREATE TABLE `t_library_details` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `library_id` bigint(20) unsigned NOT NULL,
  `file_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件名',
  `lang` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `down_load` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '下载链接',
  `post_date_string` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发布日期',
  `SHA1` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'MD5校验码',
  `size` text COLLATE utf8mb4_unicode_ci COMMENT '文件大小',
  PRIMARY KEY (`id`),
  KEY `fk_library_details` (`library_id`),
  CONSTRAINT `fk_library_details` FOREIGN KEY (`library_id`) REFERENCES `t_library` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2106 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for t_link
-- ----------------------------
DROP TABLE IF EXISTS `t_link`;
CREATE TABLE `t_link` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `sitename` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '站点名称',
  `sitelink` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '站点链接',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `addtime` datetime NOT NULL COMMENT '添加时间',
  `audit_pass` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否审核通过',
  `link_type` int(11) NOT NULL DEFAULT '1' COMMENT '交换类型：1对等交换，2交叉交换',
  `in_site_link` text COLLATE utf8mb4_unicode_ci COMMENT '在对方的链接位置',
  `contact_name` text COLLATE utf8mb4_unicode_ci COMMENT '联系人姓名',
  `contact_email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系邮箱',
  `contact_qq` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系QQ',
  `remarks` text COLLATE utf8mb4_unicode_ci COMMENT '备注',
  `order_id` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_link
-- ----------------------------
BEGIN;
INSERT INTO `t_link` VALUES (1, '苏岳宁博客', 'http://suyuening.com/', 0, '2015-07-26 00:00:00', 1, 1, NULL, '苏岳宁', 'infoplat@163.com', '714682444', NULL, 1);
INSERT INTO `t_link` VALUES (2, '码农在线', 'https://www.netcode.cn/', 0, '2015-07-26 00:00:00', 1, 1, '', '阿水', 'admin@ashui.net', NULL, NULL, 2);
INSERT INTO `t_link` VALUES (3, '辰云博客', 'https://www.itzcy.com/', 0, '2017-06-24 00:00:00', 1, 1, NULL, '辰云', 'itzcy@itzcy.com', '369322948', NULL, 3);
INSERT INTO `t_link` VALUES (4, '逐梦博客', 'https://www.deanhan.cn/', 0, '2017-11-29 17:50:22', 1, 1, NULL, '逐梦博客', 'info@deanhan.cn', '826554003', NULL, 5);
INSERT INTO `t_link` VALUES (5, '杨小羽宠物资讯网', 'http://www.anlandy.com/', 0, '2015-07-26 00:00:00', 1, 1, '', '杨小羽宠物资讯网', '2966273843@qq.com', '2966273843', NULL, 8);
INSERT INTO `t_link` VALUES (6, '杜临风博客', 'http://www.linfeng.net/', 0, '2015-07-26 00:00:00', 1, 1, '', '杜临风', '281766668@qq.com', '281766668', NULL, 6);
INSERT INTO `t_link` VALUES (7, '刘兴刚博客', 'https://www.liuxinggang.com/', 0, '2016-04-29 00:00:00', 1, 2, 'https://www.liuxinggang.com/links.html', '刘兴刚', '2367017122@qq.com', '2367017122', NULL, 7);
INSERT INTO `t_link` VALUES (8, 'Qicents', 'http://qicent.net/', 0, '2015-07-26 00:00:00', 1, 1, '', '奇讯网', 'me@lzq.me', '875858719', NULL, 4);
INSERT INTO `t_link` VALUES (9, '任霏博客', 'https://www.renfei.net/', 0, '2019-09-25 09:40:36', 1, 1, NULL, '任霏', 'i@renfei.net', '16076276', NULL, -1);
COMMIT;

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) unsigned NOT NULL DEFAULT '0',
  `menu_text` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `menu_link` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '链接',
  `is_new_win` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否新窗口打开',
  `is_enable` tinyint(1) DEFAULT '1' COMMENT '是否启用',
  `menu_type` int(10) unsigned NOT NULL COMMENT '菜单类型，1：页头菜单；2：页尾菜单；3：页脚菜单；4：页顶菜单；10：资源库',
  `order_number` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
BEGIN;
INSERT INTO `t_menu` VALUES (1, 0, 'Home', '/', 0, 1, 1, 0);
INSERT INTO `t_menu` VALUES (2, 0, 'Posts', '/posts', 0, 1, 1, 1);
INSERT INTO `t_menu` VALUES (3, 0, '社会化互动', 'JavaScript:void(0)', 0, 1, 2, 0);
INSERT INTO `t_menu` VALUES (4, 0, '公益与捐赠', 'JavaScript:void(0)', 0, 1, 2, 1);
INSERT INTO `t_menu` VALUES (5, 0, '推荐厂商', 'JavaScript:void(0)', 0, 1, 2, 2);
INSERT INTO `t_menu` VALUES (6, 0, '其他内容', 'JavaScript:void(0)', 0, 1, 2, 3);
INSERT INTO `t_menu` VALUES (7, 3, '开发者交流QQ群', 'https://shang.qq.com/wpa/qunwpa?idkey=bfbde7e5dec79fd3cdb23c7cf590ca698e3da8b48a71369139ed6aa52f9a7513', 1, 1, 2, 1);
INSERT INTO `t_menu` VALUES (8, 3, 'Github', 'https://github.com/neilren', 1, 1, 2, 0);
INSERT INTO `t_menu` VALUES (9, 0, '隐私与 Cookie', '/page/1', 1, 1, 3, 0);
INSERT INTO `t_menu` VALUES (10, 0, '© RENFEI.NET 2019', 'javascript:void(0)', 0, 1, 3, 9999999);
INSERT INTO `t_menu` VALUES (11, 0, 'Video', '/video', 0, 1, 1, 2);
INSERT INTO `t_menu` VALUES (12, 0, 'Photo', '/photo', 0, 1, 1, 3);
INSERT INTO `t_menu` VALUES (14, 0, 'KitBox', '/kitbox', 0, 1, 1, 4);
INSERT INTO `t_menu` VALUES (15, 0, 'More', 'JavaScript:void(0);', 0, 1, 1, 5);
INSERT INTO `t_menu` VALUES (16, 15, 'About', '/page/3', 0, 1, 1, 1);
INSERT INTO `t_menu` VALUES (17, 0, '使用条款', '/page/2', 1, 1, 3, 1);
INSERT INTO `t_menu` VALUES (18, 0, 'About', '/page/3', 1, 1, 3, 2);
INSERT INTO `t_menu` VALUES (19, 5, '阿里云计算', 'https://promotion.aliyun.com/ntms/yunparter/invite.html?userCode=mqe0f1u0', 1, 1, 2, 0);
INSERT INTO `t_menu` VALUES (20, 5, '腾讯云计算', 'https://cloud.tencent.com/redirect.php?redirect=1005&cps_key=1e6697c30d61f2919ab51bce34ffd8dc', 1, 1, 2, 1);
INSERT INTO `t_menu` VALUES (21, 5, '东路互联', 'https://www.donglu.net/user/aff.php?aff=114', 1, 1, 2, 2);
INSERT INTO `t_menu` VALUES (22, 6, '关于更多信息', '/page/3', 0, 1, 2, 0);
INSERT INTO `t_menu` VALUES (23, 6, '隐私与 Cookie', '/page/1', 0, 1, 2, 1);
INSERT INTO `t_menu` VALUES (24, 6, '使用条款', '/page/2', 0, 1, 2, 2);
INSERT INTO `t_menu` VALUES (25, 4, '我的公益404页面', '/error/404', 1, 1, 2, 0);
INSERT INTO `t_menu` VALUES (26, 4, '中国小动物保护协会', 'http://www.csapa.org/', 1, 1, 2, 1);
INSERT INTO `t_menu` VALUES (27, 4, '联合国儿童基金会', 'https://www.unicef.org/zh', 1, 1, 2, 2);
INSERT INTO `t_menu` VALUES (28, 0, 'fa fa-github', 'https://github.com/NeilRen', 1, 1, 4, 0);
INSERT INTO `t_menu` VALUES (29, 0, 'fa fa-facebook', 'https://www.facebook.com/renfeii', 1, 1, 4, 1);
INSERT INTO `t_menu` VALUES (30, 0, 'fa fa-twitter', 'https://twitter.com/renfeii', 1, 1, 4, 2);
INSERT INTO `t_menu` VALUES (31, 0, 'fa fa-youtube', 'https://www.youtube.com/channel/UCPsjiVvFMS7piLgC-RHBWxg', 1, 1, 4, 3);
INSERT INTO `t_menu` VALUES (32, 0, 'fa fa-weibo', 'https://weibo.com/renfeii', 1, 1, 4, 4);
INSERT INTO `t_menu` VALUES (33, 0, 'fa fa-qq', 'https://shang.qq.com/wpa/qunwpa?idkey=bfbde7e5dec79fd3cdb23c7cf590ca698e3da8b48a71369139ed6aa52f9a7513', 1, 1, 4, 5);
COMMIT;

-- ----------------------------
-- Table structure for t_movie
-- ----------------------------
DROP TABLE IF EXISTS `t_movie`;
CREATE TABLE `t_movie` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) unsigned NOT NULL COMMENT '电影分类',
  `featured_image` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '电影封面',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '电影名称',
  `region` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '电影地区',
  `years` int(11) NOT NULL COMMENT '年代',
  `director` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '导演',
  `lead` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主演',
  `synopsis` text COLLATE utf8mb4_unicode_ci COMMENT '简介',
  `views` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '浏览量',
  `score` double DEFAULT NULL COMMENT '评分',
  `resource` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资源地址',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `fk_movie_cat` (`category_id`),
  CONSTRAINT `fk_movie_cat` FOREIGN KEY (`category_id`) REFERENCES `t_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_movie
-- ----------------------------
BEGIN;
INSERT INTO `t_movie` VALUES (1, 9, '//cdn.renfei.net/movie/cover/2kbug5e3cec5wjr.jpg', '恐怖蜡像馆', '美国', 2005, '佐米·希尔拉', '伊丽莎·库斯伯特,查德·迈克尔·墨瑞,布莱恩·范·霍尔特,帕丽斯·希尔顿,贾德·帕达里克', '几个好友结伴前往观看美国大学美式足球冠军赛，他们在前往比赛地时决定顺便在野外进行一次野营。于是，卡莉（伊丽莎•库斯伯特 Elisha Cuthbert 饰）、佩兹（帕丽斯•希尔顿 Paris Hilton 饰）等几个年轻人在了野外一处荒地扎了营。他们的恐怖经历就此展开。 　　首先，一名神秘的司机和这群年轻人起了争执，令大家游玩的兴致全无；接着第二天他们起床后发现，他们的汽车被人动了手脚发动不了了。在一名当地人的指引下，他们只能徒步前往附近的偏僻小镇安布罗斯找人帮忙。安布罗斯由于太过偏远，以至地图上根本没有标出它的所在。一行人来到小镇，发现这里人迹罕至。好奇心下，他们走进了镇上的杜蒂蜡像馆，这里的蜡像做得栩栩如生。原来，这是一个杀人狂魔将镇上的人杀了用真人做到蜡像！此时那杀人狂魔正在暗处幽幽的盯着他们……', 82, 6.9, 'https://v.qq.com/txp/iframe/player.html?vid=p0022bw2oxy', '2019-11-20 22:13:39');
INSERT INTO `t_movie` VALUES (2, 9, '//cdn.renfei.net/movie/cover/0516000051FF714A675839534907BAE4.jpeg', '我的恐怖女友', '韩国', 2006, '孙在坤', '崔江熙,朴勇宇,曹恩智', '尽管大学老师黄大宇（朴勇宇 饰）仪表堂堂，满腹经纶，堪称好男人的典范，但仿佛命中注定缺少女人缘似的，他一辈子一次正正经经的恋爱都没有谈过。眼看着自己的年龄即将跨越三十大关，依旧形单影只的他只能在心里暗暗的着急。一次偶然中，黄大宇结识了美丽知性的新邻居李美娜（崔江熙 饰），他鼓起勇气提出了约会，没想到李美娜兴然应允。 　　一来二往之间，黄大宇和李美娜之间的距离越来越近，也就在这期间，黄大宇开始觉得李美娜的身份变得刻意起来，自称文学艺术爱好者的她竟然连《罪与罚》和蒙德里安都不知道，甚至连“李美娜”这个名字都是捏造而成的。这个突然出现的女人究竟是谁？她接近黄大宇又到底有何目的呢？', 23, 5.8, 'http://open.iqiyi.com/developer/player_js/coopPlayerIndex.html?vid=b68e1579a4944903cd3de4b9a61d0296&tvId=338005700&accessToken=2.f22860a2479ad60d8da7697274de9346&appKey=3955c3425820435e86d0f4cdfe56f5e7&appId=1368&height=100%&width=100%', '2019-11-20 22:16:40');
INSERT INTO `t_movie` VALUES (3, 9, '//cdn.renfei.net/movie/cover/WX20191121-224535@2x.png', '僵尸先生', '中国', 1985, '刘观伟', '林正英,许冠英,钱小豪,李赛凤,楼南光', '富贵乡绅任发先父当年威逼利诱求得一块风水宝地，经风水先生指点，其父下葬二十年后起坟迁葬，以利子孙。然九叔察看墓穴得知，当年风水先生与任家私怨在身，在墓穴中做下手脚，二十年后任老太爷尸体已生恶变。九叔提议就地火化，在任老爷请求下才将尸骨移往义庄。虽经小心看护，但任老太爷仍化作僵尸，将其子任发杀害。九叔断定任老太爷和任发的僵尸将再次出现，于是命令徒弟秋生和文才小心应付……', 2, 8.2, 'http://open.iqiyi.com/developer/player_js/coopPlayerIndex.html?vid=fe6320eefc141536d534af5e7f8c47bd&tvId=730261500&accessToken=2.f22860a2479ad60d8da7697274de9346&appKey=3955c3425820435e86d0f4cdfe56f5e7&appId=1368&height=100%&width=100%', '2019-11-21 22:47:28');
COMMIT;

-- ----------------------------
-- Table structure for t_page
-- ----------------------------
DROP TABLE IF EXISTS `t_page`;
CREATE TABLE `t_page` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) unsigned NOT NULL,
  `title` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '页面标题',
  `featured_image` text COLLATE utf8mb4_unicode_ci COMMENT '特色图像',
  `content` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '页面内容',
  `views` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '浏览量',
  `release_time` datetime NOT NULL COMMENT '发布时间',
  `add_time` datetime NOT NULL COMMENT '添加时间',
  `describes` text COLLATE utf8mb4_unicode_ci COMMENT '简介用于SEO',
  `keyword` text COLLATE utf8mb4_unicode_ci COMMENT '关键字用于SEO',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '软删除',
  PRIMARY KEY (`id`),
  KEY `fk_page_category` (`category_id`),
  FULLTEXT KEY `ft_index` (`title`,`content`) /*!50100 WITH PARSER `ngram` */ ,
  CONSTRAINT `fk_page_category` FOREIGN KEY (`category_id`) REFERENCES `t_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_page
-- ----------------------------
BEGIN;
INSERT INTO `t_page` VALUES (1, 6, '隐私声明和 Cookie', 'https://cdn.neilren.com/neilren4j/upload/b8bfb737a838450ca2e373ad4dd264f7.jpeg', '            <article class=\"d-flex\">\n                <div class=\"row content d-flex\">\n                    <div class=\"col-md-3 px-3\" style=\"font-size: 12px;\">\n                        <div>\n                            <ul style=\"list-style: none;font-size: 14px;\">\n                                <li><a href=\"#1\">我们收集的个人数据</a></li>\n                                <li><a href=\"#2\">我们如何使用个人数据</a></li>\n                                <li><a href=\"#3\">Cookie 和类似技术</a></li>\n                                <li><a href=\"#4\">搜索和人工智能</a></li>\n                            </ul>\n                        </div>\n                        <div style=\"margin-top: 40px;\">\n                            <h4>Cookie</h4>\n                            <p>\n                                大多数 RENFEI.NET 站点使用 Cookie - Cookie 是放置在设备上的小文本文件，域中放置 Cookie 的 Web\n                                服务器稍后可以检索这些小文本文件。 我们使用 Cookie 来存储你的首选项和设置、帮助登录、提供定向广告，以及分析站点运行。\n                            </p>\n                        </div>\n                    </div>\n                    <div class=\"col-md-7 px-3\">\n                        <h2 style=\"margin-bottom: 20px;\" id=\"1\">我们收集的个人数据</h2>\n                        <div class=\"gray-bar\" style=\"margin-bottom: 20px;\"></div>\n                        <p>\n                            我们会收集你的数据作为分析来源，其中包括但不限于：你的设备信息、IP地址、访问行为信息。我们收集的数据取决于你与 RENFEI.NET 互动的环境、你所做的选择。\n                        </p>\n                        <p>\n                            在你使用 RENFEI.NET 提供的服务时，将默认为您已经阅读并同意了我们的隐私条款。\n                        </p>\n                        <h2 style=\"margin-bottom: 20px;\" id=\"2\">我们如何使用个人数据</h2>\n                        <div class=\"gray-bar\" style=\"margin-bottom: 20px;\"></div>\n                        <p>\n                            RENFEI.NET 使用我们收集的数据为你提供丰富的交互式体验。给您提供更有价值的信息。\n                        </p>\n                        <p>\n                            我们还有可能根据您的信息进行广告推荐、性能分析调查。\n                        </p>\n                        <p></p>\n                        <h2 style=\"margin-bottom: 20px;\" id=\"3\">Cookie 和类似技术</h2>\n                        <div class=\"gray-bar\" style=\"margin-bottom: 20px;\"></div>\n                        <p>\n                            Cookie 是放置在设备上的小型文本文件，用于存储数据，域中放置 Cookie 的 Web 服务器可以调用这些数据。 我们使用 Cookie\n                            和类似技术来存储和遵守你的偏好和设置、使你能够登录、提供基于兴趣的广告、打击欺诈行为、分析我们服务的性能以及实现其他合法目的。\n                        </p>\n                        <p>我们也使用“Web 信号”帮助提供 Cookie 和收集用法和性能数据。 我们的网站可能包含来自第三方服务提供商的 Web 信号、Cookie 或类似技术。</p>\n                        <p>\n                            你有各种用于控制 Cookie、Web 信号和类似技术所收集的数据的工具。 例如，你可以使用 Internet 浏览器中的控件来限制你所访问的网站可如何使用\n                            Cookie，并通过清除或阻止 Cookie 来撤消同意。\n                        </p>\n                        <h2 style=\"margin-bottom: 20px;\" id=\"4\">搜索和人工智能</h2>\n                        <div class=\"gray-bar\" style=\"margin-bottom: 20px;\"></div>\n                        <p>搜索和人工智能产品会将你与信息联系起来，并智能地感知、处理和处置信息—随着时间的推移进行学习和适应。</p>\n                    </div>\n                </div>\n            </article>', 0, '2019-06-15 22:32:51', '2019-06-15 22:32:56', '我们十分重视你的隐私。 本隐私声明阐述了 RENFEI.NET 处理的个人数据以及 RENFEI.NET 处理个人数据的方式和目的。', NULL, 0);
INSERT INTO `t_page` VALUES (2, 6, '使用条款', 'https://cdn.neilren.com/neilren4j/upload/b8bfb737a838450ca2e373ad4dd264f7.jpeg', '<article>\n        <div class=\"row content\">\n            <div class=\"col-md-3\" style=\"font-size: 12px;\">\n                <div>\n                    <ul style=\"list-style: none;font-size: 14px;\">\n                        <li><a href=\"#1\">服务说明</a></li>\n                        <li><a href=\"#2\">个人和非商业用途限制</a></li>\n                        <li><a href=\"#3\">隐私和个人信息保护</a></li>\n                        <li><a href=\"#4\">有关本网站提供的服务/软件的声明</a></li>\n                        <li><a href=\"#5\">有关本网站提供的软件、文档和服务的声明</a></li>\n                        <li><a href=\"#6\">成员帐户、密码和安全性</a></li>\n                        <li><a href=\"#7\">不得用于非法或禁止的用途</a></li>\n                        <li><a href=\"#8\">到第三方站点的链接</a></li>\n                    </ul>\n                </div>\n                <div style=\"margin-top: 40px;\">\n                    <h4>接受条款</h4>\n                    <p>\n                        RENFEI.NET为您提供的服务须受以下使用条款（“TOU”）的约束。RENFEI.NET保留随时更新 TOU 的权利，恕不另行通知。单击网页底部的“使用条款”超文本链接，可查看最新版本的 TOU。\n                    </p>\n                </div>\n            </div>\n            <div class=\"col-md-7\">\n                <h2 style=\"margin-bottom: 20px;\" id=\"1\">服务说明</h2>\n                <div class=\"gray-bar\" style=\"margin-bottom: 20px;\"></div>\n                <p>\n                    通过其 Web 资产网络，RENFEI.NET允许您访问各种资源，包括文章资讯区域、下载区域、交流论坛和服务信息（以下统称为“服务”）。服务（包括任何更新、增强、新功能和/或增加任何新 Web 资产）都须受 TOU 约束。\n                </p>\n                <h2 style=\"margin-bottom: 20px;\" id=\"2\">个人和非商业用途限制</h2>\n                <div class=\"gray-bar\" style=\"margin-bottom: 20px;\"></div>\n                <p>\n                    除非另有说明，服务只适用于个人与非商业用途。对于从服务取得的任何信息、软件、产品或服务，您不得对其修改、复制、分发、传送、显示、执行、复制、公布、许可、转让、销售或基于以上内容创建衍生作品。\n                </p>\n                <h2 style=\"margin-bottom: 20px;\" id=\"3\">隐私和个人信息保护</h2>\n                <div class=\"gray-bar\" style=\"margin-bottom: 20px;\"></div>\n                <p>\n                    请参见<a href=\"/page/1\" target=\"_blank\">隐私声明</a>，了解有关收集和使用个人信息的披露规则。\n                </p>\n                <h2 style=\"margin-bottom: 20px;\" id=\"4\">有关本网站提供的服务/软件的声明</h2>\n                <div class=\"gray-bar\" style=\"margin-bottom: 20px;\"></div>\n                <p>从服务（以下简称“软件”）下载使用的任何软件如未标注开源许可均是RENFEI.NET和/或其供应商的版权作品。软件的使用受限于软件随附或包含的最终用户许可协议（以下简称“许可协议”）条款（如果有的话）。如果最终用户没有事先同意许可协议条款，则最终用户将无法安装随附或包括许可协议的任何软件。链接到本网站或通过本网站引用的第三方脚本或代码由拥有此类代码的第三方而不是RENFEI.NET向您授予许可。</p>\n                <p>软件只能由最终用户根据许可协议下载并使用。法律明确禁止用户违反许可协议对软件进行任何复制或再分发的行为，这种行为可能导致严重的民事和刑事处罚。违约者将受到最大可能的起诉。</p>\n                <p>在不影响前述规定的情况下，严禁将软件复制到任何其他服务器或位置以便进一步复制或再分发，但软件随附的许可协议明确允许进行此类复制或再分发的情况除外。</p>\n                <p>软件的保证（如果有的话）仅依照许可协议条款提供。除了许可协议上的保证，RENFEI.NET拒绝承担有关软件的所有其他明示、默示或法定的保证和为了方便起见，RENFEI.NET可能作为服务的一部分或在其软件产品内提供各种工具和实用程序，供用户使用和/或下载。RENFEI.NET不能保证使用此类工具和实用程序所获得的结果或输出的准确性。在使用服务或RENFEI.NET软件产品提供的工具和实用程序时，请尊重其他人的知识产权。</p>\n                <h2 style=\"margin-bottom: 20px;\" id=\"5\">有关本网站提供的软件、文档和服务的声明</h2>\n                <div class=\"gray-bar\" style=\"margin-bottom: 20px;\"></div>\n                <p>\n                    对于因使用或执行软件、文档、提供或未能提供服务，或者服务提供的信息引起的或与其相关的使用中断、数据或利润损失，无论是由合同行为、过失或其他侵权行为导致，RENFEI.NET和/或其供应商对由此造成的任何特别的、间接的、后果性损害或任何其他损害概不负责。\n                </p>\n                <h2 style=\"margin-bottom: 20px;\" id=\"6\">成员帐户、密码和安全性</h2>\n                <div class=\"gray-bar\" style=\"margin-bottom: 20px;\"></div>\n                <p>\n                    如果任何服务要求您开设帐户，您必须根据相应注册表上的说明，向我们提供最新的、完整的、准确的相关信息，以便完成注册过程。您还要选择一个密码和用户名。您必须对维护密码和帐户的机密性承担全部责任。此外，您需要对帐户中发生的所有活动承担全部责任。您同意，一旦发现帐户存在未经授权的使用或任何其他不安全的行为，马上向RENFEI.NET通知相关情况。如果其他人在您知情或不知情的情况下使用您的密码或帐户，RENFEI.NET对您因此可能蒙受的任何损失不承担任何责任。然而，如RENFEI.NET或第三方因其他人使用您的帐户或密码而蒙受损失，您则应承担相应责任。在任何时候，未经帐户持有人的同意，您都不得使用其他人的帐户。\n                </p>\n                <h2 style=\"margin-bottom: 20px;\" id=\"7\">不得用于非法或禁止的用途</h2>\n                <div class=\"gray-bar\" style=\"margin-bottom: 20px;\"></div>\n                <p>\n                    您在使用服务时，不得将服务用于任何非法用途或这些条款、条件和声明禁止的用途。在使用服务时，不得以任何方式损坏、禁用、过载或妨碍任何RENFEI.NET服务器或连接到任何RENFEI.NET服务器的网络，也不得干扰任何其他用户使用和享有任何服务。您不得尝试通过入侵、密码盗取或任何其他手段擅自使用任何服务、其他帐户、计算机系统或连接到任何RENFEI.NET服务器或任何服务的网络。您不得通过任何手段获取或尝试获取服务未明确提供的任何资料或信息。\n                </p>\n                <h2 style=\"margin-bottom: 20px;\" id=\"8\">到第三方站点的链接</h2>\n                <div class=\"gray-bar\" style=\"margin-bottom: 20px;\"></div>\n                <p>\n                    此处的一些链接可能会引导您离开RENFEI.NET站点。链接的站点不在RENFEI.NET的控制之内，RENFEI.NET不对任何链接站点的内容、链接站点中包含的任何链接或此类站点的任何更改或更新负责。RENFEI.NET不对源自任何链接站点的RENFEI.NET仅为了提供便利的目的而向您提供这些链接，并且包括任何链接的并不暗示RENFEI.NET认可相应的第三方站点。\n                </p>\n            </div>\n        </div>\n    </article>', 0, '2019-07-27 16:28:04', '2019-07-27 16:28:09', 'RENFEI.NET为您提供的服务须受以下使用条款（“TOU”）的约束。RENFEI.NET保留随时更新 TOU 的权利，恕不另行通知。单击网页底部的“使用条款”超文本链接，可查看最新版本的 TOU。', NULL, 0);
INSERT INTO `t_page` VALUES (3, 6, 'About', NULL, '<section id=\"company-information\" class=\"padding-top\">\n        <div class=\"container\">\n            <div class=\"row\">\n                <div class=\"about-us\">\n                    <div class=\"col-sm-7 wow fadeInLeft\" data-wow-duration=\"1000ms\" data-wow-delay=\"300ms\">\n                        <h2 class=\"bold\">About Author</h2>\n                        <div class=\"row\">\n                            <div class=\"col-sm-5\">\n                                <img src=\"https://renfei.oncdn.cn/images/neilren.jpg\" class=\"img-responsive\" alt=\"\" width=\"300\">\n                            </div>\n                            <div class=\"col-sm-7\">\n                                <h3 class=\"top-zero\">任霏</h3>\n                                <p>90后程序猿，总喜欢尝试新鲜事物，对新奇的东西充满了好奇。很幸运在我年轻的时候赶上了中国计算机的普及浪潮。 <br> <br>\n                                    小学有了微机课，初中有了网吧，高中有了自己的PC，开启了建站之旅，大学选择了喜欢的计算机专业，开始了我的故事...<br> <br>\n                                    大学是 C#.NET 专业毕业，后自学 Java 进行技术转型。目前计划使用 Scala 向大数据方向转型。  \n                                </p>\n                            </div>\n                        </div>\n                    </div>\n                    <div class=\"col-sm-5 wow fadeInRight\" data-wow-duration=\"1000ms\" data-wow-delay=\"300ms\">\n                        <div class=\"our-skills\">\n                            <h2>Contacts</h2>\n                            <address>\n                                <br>\n                            E-mail: <a href=\"mailto:i@renfei.net\">i@renfei.net</a> <br> \n                            GitHub:<a href=\"https://github.com/NeilRen\" target=\"_blank\">GitHub</a> <br> \n                            Facebook:<a href=\"https://www.facebook.com/renfeii\" target=\"_blank\">任霏</a> <br> \n                            Twitter:<a href=\"https://twitter.com/renfeii\" target=\"_blank\">@renfeii</a> <br> \n                            Sina Weibo:<a href=\"https://weibo.com/renfeii\" target=\"_blank\">@霏总驾到</a> <br> \n                            QQ Group：<a\n                                href=\"https://shang.qq.com/wpa/qunwpa?idkey=bfbde7e5dec79fd3cdb23c7cf590ca698e3da8b48a71369139ed6aa52f9a7513\"\n                                target=\"_blank\">130832168</a><br> \n                            Telegram:<a href=\"https://t.me/neilren\" target=\"_blank\">NeilRen</a>\n                            Tel:+86 13082843041\n                            </address>\n                            <address>\n                            Zhangjiakou - Hebei - China\n                            </address>\n                        </div>\n                    </div>\n                </div>\n            </div>\n        </div>\n    </section>\n\n    <section id=\"team\">\n        <div class=\"container\">\n            <div class=\"row\">\n                <h1 class=\"title text-center wow fadeInDown\" data-wow-duration=\"500ms\" data-wow-delay=\"300ms\">Timeline</h1>\n                <p class=\"text-center wow fadeInDown\" data-wow-duration=\"400ms\" data-wow-delay=\"400ms\">\n                    时间流逝，那些需要纪念的关键转折点\n                </p>\n                <div class=\"row\">\n                <div class=\"timeline-blog overflow padding-top\">\n                    <div class=\"timeline-date text-center\">\n                        <a href=\"#\" class=\"btn btn-common uppercase\">2019</a>\n                    </div>\n                    <div class=\"timeline-divider overflow padding-bottom\">\n                        <div class=\"col-sm-6 padding-right arrow-right wow fadeInLeft\" data-wow-duration=\"1000ms\" data-wow-delay=\"300ms\">\n                            <div class=\"single-blog timeline\">\n                                <div class=\"post-content overflow\">\n                                    <h2 class=\"post-title bold\"><a href=\"blogdetails.html#\">使用前后端分离</a></h2>\n                                    <h3 class=\"post-author\"><a href=\"https://github.com/neilren/RenFei.Net\" target=\"_blank\">RenFei.Net</a></h3>\n                                    <p>使用 SpringBoot 和 Vue.Js 重写网站，开始尝试前后端分离</p>\n                                    <div class=\"post-bottom overflow\">\n                                        <span class=\"post-date pull-left\">2019-5-28</span>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                        <div class=\"col-sm-6 padding-left padding-top arrow-left wow fadeInRight\" data-wow-duration=\"1000ms\" data-wow-delay=\"300ms\">\n                            <div class=\"single-blog timeline\">\n                                <div class=\"post-content overflow\">\n                                    <h2 class=\"post-title bold\"><a href=\"blogdetails.html\">收购 RENFEI.NET 域名</a></h2>\n                                    <h3 class=\"post-author\"><a href=\"https://github.com/neilren/NEILREN4J\" target=\"_blank\">NEILREN4J</a></h3>\n                                    <p>收购 RENFEI.NET 域名</p>\n                                    <div class=\"post-bottom overflow\">\n                                        <span class=\"post-date pull-left\">2019-1-4</span>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                    </div>\n                </div>\n                <div class=\"timeline-blog overflow\">\n                    <div class=\"timeline-date text-center\">\n                        <a href=\"#\" class=\"btn btn-common uppercase\">2018</a>\n                    </div>\n                    <div class=\"timeline-divider overflow padding-bottom\">\n                        <div class=\"col-sm-6 padding-right arrow-right wow fadeInLeft\" data-wow-duration=\"1000ms\" data-wow-delay=\"300ms\">\n                            <div class=\"single-blog timeline\">\n                                <div class=\"post-content overflow\">\n                                    <h2 class=\"post-title bold\"><a href=\"blogdetails.html\">功能新增</a></h2>\n                                    <h3 class=\"post-author\"><a href=\"https://github.com/neilren/NEILREN4J\" target=\"_blank\">NEILREN4J</a></h3>\n                                    <p>功能新增：工具箱小工具-弱密码检测接口，并开放接口调用，新增弱密码数据库</p>\n                                    <div class=\"post-bottom overflow\">\n                                        <span class=\"post-date pull-left\">2018-08-21</span>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                        <div class=\"col-sm-6 padding-left padding-top arrow-left wow fadeInRight\" data-wow-duration=\"1000ms\" data-wow-delay=\"300ms\">\n                            <div class=\"single-blog timeline\">\n                                <div class=\"post-content overflow\">\n                                    <h2 class=\"post-title bold\"><a href=\"blogdetails.html\">功能新增</a></h2>\n                                    <h3 class=\"post-author\"><a href=\"https://github.com/neilren/NEILREN4J\" target=\"_blank\">NEILREN4J</a></h3>\n                                    <p>功能新增：利用AOP切面统计执行时间和执行方法数量，对执行时间过长的方法进行日志记录</p>\n                                    <div class=\"post-bottom overflow\">\n                                        <span class=\"post-date pull-left\">2018-8-1</span>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                        <div class=\"col-sm-6 padding-right arrow-right wow fadeInLeft\" data-wow-duration=\"1000ms\" data-wow-delay=\"300ms\">\n                            <div class=\"single-blog timeline\">\n                                <div class=\"post-content overflow\">\n                                    <h2 class=\"post-title bold\"><a href=\"blogdetails.html\">功能新增</a></h2>\n                                    <h3 class=\"post-author\"><a href=\"https://github.com/neilren/NEILREN4J\" target=\"_blank\">NEILREN4J</a></h3>\n                                    <p>功能新增：网页头部节日主题装扮</p>\n                                    <div class=\"post-bottom overflow\">\n                                        <span class=\"post-date pull-left\">2018-7-28</span>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                        <div class=\"col-sm-6 padding-left padding-top arrow-left wow fadeInRight\" data-wow-duration=\"1000ms\" data-wow-delay=\"300ms\">\n                            <div class=\"single-blog timeline\">\n                                <div class=\"post-content overflow\">\n                                    <h2 class=\"post-title bold\"><a href=\"blogdetails.html\">技术转型Java开发</a></h2>\n                                    <h3 class=\"post-author\"><a href=\"https://github.com/neilren/NEILREN4J\" target=\"_blank\">NEILREN4J</a></h3>\n                                    <p>使用SpringBoot重新构建，项目代号 NEILREN4J</p>\n                                    <div class=\"post-bottom overflow\">\n                                        <span class=\"post-date pull-left\">2018-07-16</span>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                    </div>\n                </div>\n                <div class=\"timeline-blog overflow\">\n                    <div class=\"timeline-date text-center\">\n                        <a href=\"\" class=\"btn btn-common uppercase\">2017</a>\n                    </div>\n                    <div class=\"timeline-divider overflow padding-bottom\">\n                        <div class=\"col-sm-6 padding-right arrow-right wow fadeInLeft\" data-wow-duration=\"1000ms\" data-wow-delay=\"300ms\">\n                            <div class=\"single-blog timeline\">\n                                <div class=\"post-content overflow\">\n                                    <h2 class=\"post-title bold\"><a href=\"blogdetails.html\">开启开源之旅</a></h2>\n                                    <h3 class=\"post-author\"><a href=\"https://github.com/neilren/NEILREN4J\" target=\"_blank\">NEILREN4J</a></h3>\n                                    <p>计划上Github开源管理代码</p>\n                                    <div class=\"post-bottom overflow\">\n                                        <span class=\"post-date pull-left\">2017-06-00</span>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                        <div class=\"col-sm-6 padding-left padding-top arrow-left wow fadeInRight\" data-wow-duration=\"1000ms\" data-wow-delay=\"300ms\">\n                            <div class=\"single-blog timeline\">\n                                <div class=\"post-content overflow\">\n                                    <h2 class=\"post-title bold\"><a href=\"blogdetails.html\">技术转型Java开发</a></h2>\n                                    <h3 class=\"post-author\"><a href=\"https://github.com/neilren/NEILREN4J\" target=\"_blank\">NEILREN4J</a></h3>\n                                    <p>开始Java版开发，项目代号 NEILREN4J</p>\n                                    <div class=\"post-bottom overflow\">\n                                        <span class=\"post-date pull-left\">2017-05-00</span>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                        <div class=\"col-sm-6 padding-right arrow-right wow fadeInLeft\" data-wow-duration=\"1000ms\" data-wow-delay=\"300ms\">\n                            <div class=\"single-blog timeline\">\n                                <div class=\"post-content overflow\">\n                                    <h2 class=\"post-title bold\"><a href=\"blogdetails.html\">计划技术转型Java开发</a></h2>\n                                    <h3 class=\"post-author\"><a href=\"https://github.com/neilren/NeilNT\" target=\"_blank\">NeilNT</a></h3>\n                                    <p>购买搭建Linux服务器</p>\n                                    <div class=\"post-bottom overflow\">\n                                        <span class=\"post-date pull-left\">2017-04-00</span>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                    </div>\n                </div>\n                <div class=\"timeline-blog overflow\">\n                    <div class=\"timeline-date text-center\">\n                        <a href=\"\" class=\"btn btn-common uppercase\">2016</a>\n                    </div>\n                    <div class=\"timeline-divider overflow padding-bottom\">\n                        <div class=\"col-sm-6 padding-right arrow-right wow fadeInLeft\" data-wow-duration=\"1000ms\" data-wow-delay=\"300ms\">\n                            <div class=\"single-blog timeline\">\n                                <div class=\"post-content overflow\">\n                                    <h2 class=\"post-title bold\"><a href=\"blogdetails.html\">计划技术转型Java开发</a></h2>\n                                    <h3 class=\"post-author\"><a href=\"https://github.com/neilren/NeilNT\" target=\"_blank\">NeilNT</a></h3>\n                                    <p>尝试使用 SSM 框架构建新的Java版网站</p>\n                                    <div class=\"post-bottom overflow\">\n                                        <span class=\"post-date pull-left\">2016-12-00</span>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                        <div class=\"col-sm-6 padding-left padding-top arrow-left wow fadeInRight\" data-wow-duration=\"1000ms\" data-wow-delay=\"300ms\">\n                            <div class=\"single-blog timeline\">\n                                <div class=\"post-content overflow\">\n                                    <h2 class=\"post-title bold\"><a href=\"blogdetails.html\">计划技术转型Java开发</a></h2>\n                                    <h3 class=\"post-author\"><a href=\"https://github.com/neilren/NeilNT\" target=\"_blank\">NeilNT</a></h3>\n                                    <p>计划转Java技术，开始筹备Java知识和Linux知识</p>\n                                    <div class=\"post-bottom overflow\">\n                                        <span class=\"post-date pull-left\">2016-10-00</span>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                    </div>\n                </div>\n                <div class=\"timeline-blog overflow\">\n                    <div class=\"timeline-date text-center\">\n                        <a href=\"\" class=\"btn btn-common uppercase\">2015</a>\n                    </div>\n                    <div class=\"timeline-divider overflow padding-bottom\">\n                        <div class=\"col-sm-6 padding-right arrow-right wow fadeInLeft\" data-wow-duration=\"1000ms\" data-wow-delay=\"300ms\">\n                            <div class=\"single-blog timeline\">\n                                <div class=\"post-content overflow\">\n                                    <h2 class=\"post-title bold\"><a href=\"blogdetails.html\">自主独立开发网站，告别开源建站</a></h2>\n                                    <h3 class=\"post-author\"><a href=\"https://github.com/neilren/NeilNT\" target=\"_blank\">NeilNT</a></h3>\n                                    <p>NEILREN.COM上线，使用 ASP.NET MVC 独立开发程序，项目代号 NeilNT</p>\n                                    <div class=\"post-bottom overflow\">\n                                        <span class=\"post-date pull-left\">2015-8-1</span>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                        <div class=\"col-sm-6 padding-left padding-top arrow-left wow fadeInRight\" data-wow-duration=\"1000ms\" data-wow-delay=\"300ms\">\n                            <div class=\"single-blog timeline\">\n                                <div class=\"post-content overflow\">\n                                    <h2 class=\"post-title bold\"><a href=\"blogdetails.html\">数据库迁移准备搬家到自己的程序上</a></h2>\n                                    <h3 class=\"post-author\"><a href=\"https://wordpress.org/\" target=\"_blank\">WordPress</a></h3>\n                                    <p> 数据库迁移，从MySQL迁移到SQL Server</p>\n                                    <a href=\"#\" class=\"read-more\">View More</a>\n                                    <div class=\"post-bottom overflow\">\n                                        <span class=\"post-date pull-left\">2015-7-6</span>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                        <div class=\"col-sm-6 padding-right arrow-right wow fadeInLeft\" data-wow-duration=\"1000ms\" data-wow-delay=\"300ms\">\n                            <div class=\"single-blog timeline\">\n                                <div class=\"post-content overflow\">\n                                    <h2 class=\"post-title bold\"><a href=\"blogdetails.html\">计划自主开发自己的网站程序</a></h2>\n                                    <h3 class=\"post-author\"><a href=\"https://wordpress.org/\" target=\"_blank\">WordPress</a></h3>\n                                    <p>MVC  配置新服务器，新购阿里云服务器</p>\n                                    <div class=\"post-bottom overflow\">\n                                        <span class=\"post-date pull-left\">2015-6-27</span>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                        <div class=\"col-sm-6 padding-left padding-top arrow-left wow fadeInRight\" data-wow-duration=\"1000ms\" data-wow-delay=\"300ms\">\n                            <div class=\"single-blog timeline\">\n                                <div class=\"post-content overflow\">\n                                    <h2 class=\"post-title bold\"><a href=\"blogdetails.html\">大学毕业啦</a></h2>\n                                    <h3 class=\"post-author\"><a href=\"https://wordpress.org/\" target=\"_blank\">WordPress</a></h3>\n                                    <p>大学毕业，在企业实习中，计划独立开发网站程序</p>\n                                    <div class=\"post-bottom overflow\">\n                                        <span class=\"post-date pull-left\">2015-6-21</span>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                        <div class=\"col-sm-6 padding-right arrow-right wow fadeInLeft\" data-wow-duration=\"1000ms\" data-wow-delay=\"300ms\">\n                            <div class=\"single-blog timeline\">\n                                <div class=\"post-content overflow\">\n                                    <h2 class=\"post-title bold\"><a href=\"blogdetails.html\">注册neil.ren域名</a></h2>\n                                    <h3 class=\"post-author\"><a href=\"https://wordpress.org/\" target=\"_blank\">WordPress</a></h3>\n                                    <p>注册neil.ren域名/p>\n                                    <div class=\"post-bottom overflow\">\n                                        <span class=\"post-date pull-left\">2015-1-5</span>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                    </div>\n                </div>\n                <div class=\"timeline-blog overflow\">\n                    <div class=\"timeline-date text-center\">\n                        <a href=\"\" class=\"btn btn-common uppercase\">2013</a>\n                    </div>\n                    <div class=\"timeline-divider overflow padding-bottom\">\n                        <div class=\"col-sm-6 padding-right arrow-right wow fadeInLeft\" data-wow-duration=\"1000ms\" data-wow-delay=\"300ms\">\n                            <div class=\"single-blog timeline\">\n                                <div class=\"post-content overflow\">\n                                    <h2 class=\"post-title bold\"><a href=\"blogdetails.html\">更换域名</a></h2>\n                                    <h3 class=\"post-author\"><a href=\"https://wordpress.org/\" target=\"_blank\">WordPress</a></h3>\n                                    <p>注册ren-fei.com 域名更改为ren-fei.com</p>\n                                    <div class=\"post-bottom overflow\">\n                                        <span class=\"post-date pull-left\">2013-03-01</span>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                        <div class=\"col-sm-6 padding-left padding-top arrow-left wow fadeInRight\" data-wow-duration=\"1000ms\" data-wow-delay=\"300ms\">\n                            <div class=\"single-blog timeline\">\n                                <div class=\"post-content overflow\">\n                                    <h2 class=\"post-title bold\"><a href=\"blogdetails.html\">更换域名</a></h2>\n                                    <h3 class=\"post-author\"><a href=\"https://wordpress.org/\" target=\"_blank\">WordPress</a></h3>\n                                    <p>注册neilren.com，20岁的生日，更换域名</p>\n                                    <div class=\"post-bottom overflow\">\n                                        <span class=\"post-date pull-left\">2013-11-06</span>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                    </div>\n                </div>\n                <div class=\"timeline-blog overflow\">\n                    <div class=\"timeline-date text-center\">\n                        <a href=\"\" class=\"btn btn-common uppercase\">2012</a>\n                    </div>\n                    <div class=\"timeline-divider overflow padding-bottom\">\n                        <div class=\"col-sm-6 padding-right arrow-right wow fadeInLeft\" data-wow-duration=\"1000ms\" data-wow-delay=\"300ms\">\n                            <div class=\"single-blog timeline\">\n                                <div class=\"post-content overflow\">\n                                    <h2 class=\"post-title bold\"><a href=\"blogdetails.html\">开启建站之旅</a></h2>\n                                    <h3 class=\"post-author\"><a href=\"https://wordpress.org/\" target=\"_blank\">WordPress</a></h3>\n                                    <p>注册 任霏.中国，中文域名</p>\n                                    <div class=\"post-bottom overflow\">\n                                        <span class=\"post-date pull-left\">2012-06-04</span>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                        <div class=\"col-sm-6 padding-left padding-top arrow-left wow fadeInRight\" data-wow-duration=\"1000ms\" data-wow-delay=\"300ms\">\n                            <div class=\"single-blog timeline\">\n                                <div class=\"post-content overflow\">\n                                    <h2 class=\"post-title bold\"><a href=\"blogdetails.html\">开启建站之旅</a></h2>\n                                    <h3 class=\"post-author\"><a href=\"https://wordpress.org/\" target=\"_blank\">WordPress</a></h3>\n                                    <p>使用WordPress搭建了独立的个人博客网站bennett-ren.com</p>\n                                    <div class=\"post-bottom overflow\">\n                                        <span class=\"post-date pull-left\">2012-04-08</span>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                    </div>\n                </div>\n\n\n            </div>\n            </div>\n        </div>\n    </section> ', 0, '2019-07-27 16:31:47', '2019-07-27 16:31:51', 'More information about us.', NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资源名称',
  `descritpion` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `url` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资源接口地址',
  `method` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求方法',
  `menu_id` bigint(20) unsigned NOT NULL COMMENT '关联的菜单',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name` (`name`) USING BTREE,
  KEY `fk_permisssion_menu` (`menu_id`),
  CONSTRAINT `fk_permisssion_menu` FOREIGN KEY (`menu_id`) REFERENCES `t_sys_menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_permission
-- ----------------------------
BEGIN;
INSERT INTO `t_permission` VALUES (1, '获取菜单列表', '获取菜单列表', '/api/menu', 'GET', 1);
COMMIT;

-- ----------------------------
-- Table structure for t_permission_role
-- ----------------------------
DROP TABLE IF EXISTS `t_permission_role`;
CREATE TABLE `t_permission_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) unsigned NOT NULL,
  `permission_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_permission_role` (`role_id`),
  KEY `fk_permission_permission` (`permission_id`),
  CONSTRAINT `fk_permission_permission` FOREIGN KEY (`permission_id`) REFERENCES `t_permission` (`id`),
  CONSTRAINT `fk_permission_role` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_permission_role
-- ----------------------------
BEGIN;
INSERT INTO `t_permission_role` VALUES (1, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for t_photo
-- ----------------------------
DROP TABLE IF EXISTS `t_photo`;
CREATE TABLE `t_photo` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) unsigned NOT NULL,
  `title` text COLLATE utf8mb4_unicode_ci COMMENT '标题',
  `featured_image` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '缩略图',
  `describes` text COLLATE utf8mb4_unicode_ci COMMENT '描述',
  `release_time` datetime NOT NULL COMMENT '发布时间',
  `add_time` datetime NOT NULL COMMENT '添加时间',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_photo_cat` (`category_id`),
  FULLTEXT KEY `ft_index` (`title`,`describes`) /*!50100 WITH PARSER `ngram` */ ,
  CONSTRAINT `fk_photo_cat` FOREIGN KEY (`category_id`) REFERENCES `t_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_photo
-- ----------------------------
BEGIN;
INSERT INTO `t_photo` VALUES (1, 8, '我家猫主子', 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0881.JPG', '我家猫主子是在2018年年底捡到的，2019年过年的时候要出去，结果就再也没回来。', '2019-07-29 16:56:21', '2019-07-29 16:56:21', 0);
COMMIT;

-- ----------------------------
-- Table structure for t_photo_img
-- ----------------------------
DROP TABLE IF EXISTS `t_photo_img`;
CREATE TABLE `t_photo_img` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `photo_id` bigint(20) unsigned NOT NULL COMMENT '相册ID',
  `uri` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '照片地址',
  PRIMARY KEY (`id`),
  KEY `fk_photo_img` (`photo_id`),
  CONSTRAINT `fk_photo_img` FOREIGN KEY (`photo_id`) REFERENCES `t_photo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_photo_img
-- ----------------------------
BEGIN;
INSERT INTO `t_photo_img` VALUES (1, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0881.JPG');
INSERT INTO `t_photo_img` VALUES (2, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0172.JPG');
INSERT INTO `t_photo_img` VALUES (3, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0173.JPG');
INSERT INTO `t_photo_img` VALUES (4, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0214.JPG');
INSERT INTO `t_photo_img` VALUES (5, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0217.JPG');
INSERT INTO `t_photo_img` VALUES (6, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0219.JPG');
INSERT INTO `t_photo_img` VALUES (7, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0221.JPG');
INSERT INTO `t_photo_img` VALUES (8, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0225.JPG');
INSERT INTO `t_photo_img` VALUES (9, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0228.JPG');
INSERT INTO `t_photo_img` VALUES (10, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0234.JPG');
INSERT INTO `t_photo_img` VALUES (11, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0235.JPG');
INSERT INTO `t_photo_img` VALUES (12, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0236.JPG');
INSERT INTO `t_photo_img` VALUES (13, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0253.JPG');
INSERT INTO `t_photo_img` VALUES (14, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0254.JPG');
INSERT INTO `t_photo_img` VALUES (15, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0255.JPG');
INSERT INTO `t_photo_img` VALUES (16, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0256.JPG');
INSERT INTO `t_photo_img` VALUES (17, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0261.JPG');
INSERT INTO `t_photo_img` VALUES (18, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0267.JPG');
INSERT INTO `t_photo_img` VALUES (19, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0268.JPG');
INSERT INTO `t_photo_img` VALUES (20, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0272.JPG');
INSERT INTO `t_photo_img` VALUES (21, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0274.JPG');
INSERT INTO `t_photo_img` VALUES (22, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0276.JPG');
INSERT INTO `t_photo_img` VALUES (23, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0278.JPG');
INSERT INTO `t_photo_img` VALUES (24, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0683.JPG');
INSERT INTO `t_photo_img` VALUES (25, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0688.JPG');
INSERT INTO `t_photo_img` VALUES (26, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0743.JPG');
INSERT INTO `t_photo_img` VALUES (27, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0746.JPG');
INSERT INTO `t_photo_img` VALUES (28, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0748.JPG');
INSERT INTO `t_photo_img` VALUES (29, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0753.JPG');
INSERT INTO `t_photo_img` VALUES (30, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0754.JPG');
INSERT INTO `t_photo_img` VALUES (31, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0874.JPG');
INSERT INTO `t_photo_img` VALUES (32, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0875.JPG');
INSERT INTO `t_photo_img` VALUES (33, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0880.JPG');
INSERT INTO `t_photo_img` VALUES (34, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0882.JPG');
INSERT INTO `t_photo_img` VALUES (35, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0884.JPG');
INSERT INTO `t_photo_img` VALUES (36, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0885.JPG');
INSERT INTO `t_photo_img` VALUES (37, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0887.JPG');
INSERT INTO `t_photo_img` VALUES (38, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0888.JPG');
INSERT INTO `t_photo_img` VALUES (39, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0889.JPG');
INSERT INTO `t_photo_img` VALUES (40, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0891.JPG');
INSERT INTO `t_photo_img` VALUES (41, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0898.JPG');
INSERT INTO `t_photo_img` VALUES (42, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0899.JPG');
INSERT INTO `t_photo_img` VALUES (43, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0900.JPG');
INSERT INTO `t_photo_img` VALUES (44, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0973.JPG');
INSERT INTO `t_photo_img` VALUES (45, 1, 'https://renfei.oncdn.cn/upload/photo/2019/IMG_0974.JPG');
COMMIT;

-- ----------------------------
-- Table structure for t_posts
-- ----------------------------
DROP TABLE IF EXISTS `t_posts`;
CREATE TABLE `t_posts` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) unsigned NOT NULL COMMENT '文章分类',
  `featured_image` text COLLATE utf8mb4_unicode_ci COMMENT '特色图像',
  `title` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文章标题',
  `content` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文章内容',
  `is_original` tinyint(1) unsigned NOT NULL COMMENT '是否原创文章',
  `source_url` text COLLATE utf8mb4_unicode_ci COMMENT '原文链接',
  `source_name` text COLLATE utf8mb4_unicode_ci COMMENT '文章来源名称',
  `views` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '浏览量',
  `release_time` datetime NOT NULL COMMENT '发布时间',
  `add_time` datetime NOT NULL COMMENT '添加时间',
  `describes` text COLLATE utf8mb4_unicode_ci COMMENT '文章简介用于SEO',
  `keyword` text COLLATE utf8mb4_unicode_ci COMMENT '关键字用于SEO',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '软删除',
  `is_comment` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否允许评论',
  `page_rank` double NOT NULL DEFAULT '1000' COMMENT '权重排序',
  `page_rank_update_time` datetime DEFAULT NULL COMMENT '权重更新时间',
  PRIMARY KEY (`id`),
  KEY `fk_posts_category` (`category_id`) USING BTREE COMMENT '与类别的外键约束',
  FULLTEXT KEY `ft_index` (`title`,`content`) /*!50100 WITH PARSER `ngram` */ ,
  CONSTRAINT `fk_posts_category` FOREIGN KEY (`category_id`) REFERENCES `t_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1003284 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_posts
-- ----------------------------
BEGIN;
INSERT INTO `t_posts` VALUES (1, 1, 'https://cdn.neilren.com/neilren4j/upload/b8bfb737a838450ca2e373ad4dd264f7.jpeg', '你好！世界程序', '<h2><span class=\"ez-toc-section\" id=\"tocjs\">toc.js</span></h2>\n<p>这个就是那段神奇的代码</p>\n<pre class=\"lang:js decode:true\" title=\"toc.js\"><code class=\"js\">!function(a){a.fn.toc=function(b){var c,d=this,e=a.extend({},jQuery.fn.toc.defaults,b),f=a(e.container),g=a(e.selectors,f),h=[],i=e.prefix+\"-active\",j=function(b){for(var c=0,d=arguments.length;d&gt;c;c++){var e=arguments[c],f=a(e);if(f.scrollTop()&gt;0)return f;f.scrollTop(1);var g=f.scrollTop()&gt;0;if(f.scrollTop(0),g)return f}return[]},k=j(e.container,\"body\",\"html\"),l=function(b){if(e.smoothScrolling){b.preventDefault();var c=a(b.target).attr(\"href\"),f=a(c);k.animate({scrollTop:f.offset().top},400,\"swing\",function(){location.hash=c})}a(\"li\",d).removeClass(i),a(b.target).parent().addClass(i)},m=function(b){c&amp;&amp;clearTimeout(c),c=setTimeout(function(){for(var b,c=a(window).scrollTop(),f=0,g=h.length;g&gt;f;f++)if(h[f]&gt;=c){a(\"li\",d).removeClass(i),b=a(\"li:eq(\"+(f-1)+\")\",d).addClass(i),e.onHighlight(b);break}},50)};return e.highlightOnScroll&amp;&amp;(a(window).bind(\"scroll\",m),m()),this.each(function(){var b=a(this),c=a(\"&lt;ul/&gt;\");g.each(function(d,f){var g=a(f);h.push(g.offset().top-e.highlightOffset);var i=(a(\"&lt;span/&gt;\").attr(\"id\",e.anchorName(d,f,e.prefix)).insertBefore(g),a(\"&lt;a/&gt;\").text(e.headerText(d,f,g)).attr(\"href\",\"#\"+e.anchorName(d,f,e.prefix)).bind(\"click\",function(c){l(c),b.trigger(\"selected\",a(this).attr(\"href\"))})),j=a(\"&lt;li/&gt;\").addClass(e.itemClass(d,f,g,e.prefix)).append(i);c.append(j)}),b.html(c)})},jQuery.fn.toc.defaults={container:\"body\",selectors:\"h1,h2,h3\",smoothScrolling:!0,prefix:\"toc\",onHighlight:function(){},highlightOnScroll:!0,highlightOffset:100,anchorName:function(a,b,c){return c+a},headerText:function(a,b,c){return c.text()},itemClass:function(a,b,c,d){return d+\"-\"+c[0].tagName.toLowerCase()}}}(jQuery);</code></pre>\n<h2><span class=\"ez-toc-section\" id=\"i\">生成目录程序生成目录程序生成目录程序生成目录程序生成目录程序</span></h2>\n<p>下面这两段程序，第一段没明白是做什么的，第二段是用来生成目录的程序。</p>\n<pre class=\"php\"><code class=php>//这个程序没看到有什么作用，可能是考虑了固定导航栏的高度\njQuery(document).on(\"click\", \"#toc a\", function (a) {\n    // #wpadminbar 世界替换了原来的 header\n    $(\"#wpadminbar\").animate({marginBottom: 130}, 200).animate({marginBottom: 30}, 200)\n});\n//这一段是用来生成目录的\njQuery(document).ready(function () {\n    return 0 === $(\".article_content h2\").length ? ($(\"#toc\").remove(), 0) : (jQuery(\"#toc\").toc({\n         selectors: \"h2,h3,h4\",\n          container: \".article_content\"\n     }), jQuery(\"#toc\").before(\"&lt;h2&gt;目录&lt;/h2&gt;\"), \"参考链接\" === $.trim($(\".article_content h2:nth-last-of-type(1)\").text()) &amp;&amp; $(\".article_content h2:nth-last-of-type(1)\").addClass(\"reference\").next(\"ul\").addClass(\"reference-list\"), void $(\"#toc~h2\").wrap(\'&lt;div class=\"chapter\" /&gt;\'))\n});</code></pre>\n<h2><span class=\"ez-toc-section\" id=\"i-2\">美化</span></h2>\n<p>下面的css 就是本页面目录所使用的样式</p>\n<h3><span class=\"ez-toc-section\" id=\"i-2\">H3</span></h3>\n<pre class=\"php\" title=\"css\"><code class=\"php\">    &lt;style&gt;\n        #toc {\n            /*background-color: #111;\n            box-shadow: -5px 0 5px 0 #000 inset;\n            color: #fff;\n            font-family: Consolas,\"Courier New\",Courier,FreeMono,monospace;\n            font-weight: 700;*/\n            margin-bottom: 2em;\n            padding-top: 20px;\n        }\n        #toc ul {\n            /*list-style: outside none none;*/\n            list-style:none;\n            margin: 0;\n            padding: 0;\n        }\n        #toc li {\n            padding: 5px 10px;\n            list-style:none;\n        }\n        #toc a {\n            color: #a6e22e;\n            display: block;\n            text-decoration: none;\n        }\n        #toc li:hover {\n            background: #369 none repeat scroll 0 0;\n            /*box-shadow: -5px 0 10px -5px #000 inset;*/\n        }\n        #toc li a:hover{\n            border:none;\n        }\n        #toc .toc-h2 {\n            padding-left: 2em;\n        }\n        #toc .toc-h3 {\n            padding-left: 4em;\n        }\n        #toc .toc-h4 {\n            padding-left: 6em;\n        }\n    &lt;/style&gt;</code></pre>\n<p>写在最后吧：<br />\n后来，我找到了官方项目地址，就算一次快乐的旅行吧：<br />\nhttp://projects.jga.me/toc/<br />\nhttps://github.com/jgallen23/toc</p>', 1, NULL, NULL, 73, '2019-12-04 13:30:21', '2019-12-04 13:30:21', '这个是简介', NULL, 0, 1, 1.288888645172119, '2019-12-13 16:24:00');
COMMIT;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_role
-- ----------------------------
BEGIN;
INSERT INTO `t_role` VALUES (1, 'admin');
COMMIT;

-- ----------------------------
-- Table structure for t_role_account
-- ----------------------------
DROP TABLE IF EXISTS `t_role_account`;
CREATE TABLE `t_role_account` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `account_id` bigint(20) unsigned NOT NULL,
  `role_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_role_account` (`account_id`),
  KEY `fk_role_role` (`role_id`),
  CONSTRAINT `fk_role_account` FOREIGN KEY (`account_id`) REFERENCES `t_account` (`id`),
  CONSTRAINT `fk_role_role` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_role_account
-- ----------------------------
BEGIN;
INSERT INTO `t_role_account` VALUES (1, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for t_secret_key
-- ----------------------------
DROP TABLE IF EXISTS `t_secret_key`;
CREATE TABLE `t_secret_key` (
  `uid` binary(16) NOT NULL,
  `server_private_key` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `client_public_key` longtext COLLATE utf8mb4_unicode_ci,
  `aes_key` text COLLATE utf8mb4_unicode_ci,
  `expire_time` datetime NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for t_setting
-- ----------------------------
DROP TABLE IF EXISTS `t_setting`;
CREATE TABLE `t_setting` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `keys` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '键',
  `values` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '值',
  `orders` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_setting
-- ----------------------------
BEGIN;
INSERT INTO `t_setting` VALUES (1, 'sitename', 'The RenFei Blog', NULL);
INSERT INTO `t_setting` VALUES (2, 'domain', 'http://localhost:8091', NULL);
INSERT INTO `t_setting` VALUES (3, 'highlight', '/font/highlight/highlight.pack.js', NULL);
INSERT INTO `t_setting` VALUES (4, 'staticdomain', 'localhost:8091', NULL);
INSERT INTO `t_setting` VALUES (5, 'css', '/font/font-awesome/4.7.0/css/font-awesome.min.css', 1);
INSERT INTO `t_setting` VALUES (6, 'css', '/css/bootstrap.min.css', 5);
INSERT INTO `t_setting` VALUES (7, 'jss', '/font/jquery/jquery-3.4.1.min.js', 1);
INSERT INTO `t_setting` VALUES (8, 'description', ' ', NULL);
INSERT INTO `t_setting` VALUES (9, 'script', ' ', NULL);
INSERT INTO `t_setting` VALUES (10, 'analyticscode', '<script>\nvar _hmt = _hmt || [];\n(function() {\n  var hm = document.createElement(\"script\");\n  hm.src = \"https://hm.baidu.com/hm.js?ee1f1987ccfc9bcd61a1d220f5ae41e1\";\n  var s = document.getElementsByTagName(\"script\")[0]; \n  s.parentNode.insertBefore(hm, s);\n})();\n</script>\n<script async src=\"https://www.googletagmanager.com/gtag/js?id=UA-141370164-1\"></script>\n<script>\n    window.dataLayer = window.dataLayer || [];\n    function gtag(){dataLayer.push(arguments);}\n    gtag(\'js\', new Date());\n\n    gtag(\'config\', \'UA-141370164-1\');\n</script>', NULL);
INSERT INTO `t_setting` VALUES (11, 'footermenu', '测试|#', NULL);
INSERT INTO `t_setting` VALUES (13, 'homebanner', '/img/banner/home_banner.jpg', NULL);
INSERT INTO `t_setting` VALUES (14, 'jss', '//qzonestyle.gtimg.cn/qzone/v6/portal/gy/404/data.js', 3);
INSERT INTO `t_setting` VALUES (15, 'sitelogo', '/logo/RF.svg', NULL);
INSERT INTO `t_setting` VALUES (16, 'jss', '/js/bootstrap.min.js', 6);
INSERT INTO `t_setting` VALUES (17, 'css', '/font/jBox/jBox.css', 2);
INSERT INTO `t_setting` VALUES (18, 'css', '/font/jBox/plugins/Notice/jBox.Notice.css', 3);
INSERT INTO `t_setting` VALUES (19, 'css', '/font/jBox/themes/NoticeFancy.css', 4);
INSERT INTO `t_setting` VALUES (20, 'jss', '/font/jBox/jBox.js', 4);
INSERT INTO `t_setting` VALUES (21, 'jss', '/font/jBox/plugins/Notice/jBox.Notice.js', 5);
INSERT INTO `t_setting` VALUES (22, 'global_comment', '1', NULL);
INSERT INTO `t_setting` VALUES (23, 'css', '/css/animate.min.css', 6);
INSERT INTO `t_setting` VALUES (24, 'css', '/css/lightbox.css', 7);
INSERT INTO `t_setting` VALUES (25, 'css', '/css/main.css', 8);
INSERT INTO `t_setting` VALUES (26, 'css', '/css/responsive.css', 9);
INSERT INTO `t_setting` VALUES (27, 'jss', '/js/lightbox.min.js', 7);
INSERT INTO `t_setting` VALUES (28, 'jss', '/js/wow.min.js', 8);
INSERT INTO `t_setting` VALUES (29, 'jss', '/js/main.js', 10);
INSERT INTO `t_setting` VALUES (30, 'jss', '//res.wx.qq.com/open/js/jweixin-1.4.0.js', 9);
COMMIT;

-- ----------------------------
-- Table structure for t_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_menu`;
CREATE TABLE `t_sys_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) unsigned DEFAULT '0' COMMENT '上一级菜单',
  `menu_name` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
  `menu_link` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单链接',
  `orderid` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_menu` VALUES (1, 0, '系统设置', '#', 1);
INSERT INTO `t_sys_menu` VALUES (2, 0, '内容管理', '#', 0);
INSERT INTO `t_sys_menu` VALUES (3, 2, '文章管理', '#', 0);
INSERT INTO `t_sys_menu` VALUES (4, 2, '视频管理', '#', 1);
COMMIT;

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `en_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `zh_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `describe` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_tag
-- ----------------------------
BEGIN;
INSERT INTO `t_tag` VALUES (1, 'domainname', '域名', NULL);
INSERT INTO `t_tag` VALUES (2, 'usb', 'USB', NULL);
INSERT INTO `t_tag` VALUES (3, 'udisk', 'U盘', NULL);
INSERT INTO `t_tag` VALUES (4, 'cable', '网线', NULL);
INSERT INTO `t_tag` VALUES (5, 'device', '硬件', NULL);
INSERT INTO `t_tag` VALUES (6, 'network', '网络', NULL);
INSERT INTO `t_tag` VALUES (7, 'program', '编程', NULL);
INSERT INTO `t_tag` VALUES (8, 'develop', '开发', NULL);
INSERT INTO `t_tag` VALUES (9, 'java', 'Java', NULL);
INSERT INTO `t_tag` VALUES (10, 'code', '代码', NULL);
INSERT INTO `t_tag` VALUES (11, 'dns', 'DNS', NULL);
INSERT INTO `t_tag` VALUES (12, 'nginx', 'Nginx', NULL);
INSERT INTO `t_tag` VALUES (13, 'web', '建站', NULL);
INSERT INTO `t_tag` VALUES (14, 'ops', '运维', NULL);
INSERT INTO `t_tag` VALUES (15, 'server', '服务器', NULL);
INSERT INTO `t_tag` VALUES (16, 'security', '安全', NULL);
INSERT INTO `t_tag` VALUES (17, 'bigdata', '大数据', NULL);
INSERT INTO `t_tag` VALUES (18, 'usual', '日常', NULL);
INSERT INTO `t_tag` VALUES (19, 'database', '数据库', NULL);
INSERT INTO `t_tag` VALUES (20, 'mysql', 'MySQL', NULL);
INSERT INTO `t_tag` VALUES (21, 'idea', 'IDEA', NULL);
INSERT INTO `t_tag` VALUES (22, 'opensource', '开源', NULL);
INSERT INTO `t_tag` VALUES (23, 'github', 'GitHub', NULL);
INSERT INTO `t_tag` VALUES (24, 'git', 'Git', NULL);
INSERT INTO `t_tag` VALUES (25, 'mybatis', 'MyBatis', NULL);
INSERT INTO `t_tag` VALUES (26, 'pm', '项目管理', NULL);
INSERT INTO `t_tag` VALUES (27, 'btc', '比特币', NULL);
INSERT INTO `t_tag` VALUES (28, 'news', '新闻', NULL);
INSERT INTO `t_tag` VALUES (29, 'cloudcomputing', '云计算', NULL);
INSERT INTO `t_tag` VALUES (30, 'host', '主机', NULL);
INSERT INTO `t_tag` VALUES (31, 'sql', 'SQL', NULL);
INSERT INTO `t_tag` VALUES (32, 'dotnet', '.NET', NULL);
INSERT INTO `t_tag` VALUES (33, 'raspberrypi', '树莓派', NULL);
INSERT INTO `t_tag` VALUES (34, 'oracle', 'Oracle', NULL);
INSERT INTO `t_tag` VALUES (35, 'linux', 'Linux', NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_tag_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_tag_relation`;
CREATE TABLE `t_tag_relation` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tag_id` bigint(20) unsigned NOT NULL,
  `type_id` bigint(20) unsigned NOT NULL,
  `target_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tag` (`tag_id`),
  KEY `fk_tag_type` (`type_id`),
  CONSTRAINT `fk_tag` FOREIGN KEY (`tag_id`) REFERENCES `t_tag` (`id`),
  CONSTRAINT `fk_tag_type` FOREIGN KEY (`type_id`) REFERENCES `t_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=533 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_tag_relation
-- ----------------------------
BEGIN;
INSERT INTO `t_tag_relation` VALUES (1, 1, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for t_token
-- ----------------------------
DROP TABLE IF EXISTS `t_token`;
CREATE TABLE `t_token` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `account` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账户名',
  `token` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'TOKEN',
  `is_remember` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否记住我',
  `expiration_time` datetime NOT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for t_type
-- ----------------------------
DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `type_name` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `uri_path` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_type
-- ----------------------------
BEGIN;
INSERT INTO `t_type` VALUES (1, 'Posts', '/posts');
INSERT INTO `t_type` VALUES (2, 'Pages', '/page');
INSERT INTO `t_type` VALUES (3, 'Video', '/video');
INSERT INTO `t_type` VALUES (4, 'Photo', '/photo');
INSERT INTO `t_type` VALUES (5, 'Movie', '/movie');
COMMIT;

-- ----------------------------
-- Table structure for t_video
-- ----------------------------
DROP TABLE IF EXISTS `t_video`;
CREATE TABLE `t_video` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) unsigned NOT NULL,
  `title` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `featured_image` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '特色图像',
  `describes` text COLLATE utf8mb4_unicode_ci COMMENT '描述',
  `release_time` datetime NOT NULL COMMENT '发布时间',
  `add_time` datetime NOT NULL COMMENT '添加时间',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '删除标志',
  `views` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '浏览量',
  `is_comment` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否允许评论',
  `download` text COLLATE utf8mb4_unicode_ci COMMENT '下载链接',
  PRIMARY KEY (`id`),
  FULLTEXT KEY `ft_index` (`title`,`describes`) /*!50100 WITH PARSER `ngram` */ 
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_video
-- ----------------------------
BEGIN;
INSERT INTO `t_video` VALUES (1, 7, '大境门景区-第一次使用DJI OSMO Mobile 2', '//renfei.oncdn.cn/upload/image/a869a9a910f9451996347bac2b81932b.png', '大境门景区-第一次使用DJI OSMO Mobile 2', '2019-07-26 14:28:45', '2019-07-26 14:28:50', 0, 25, 1, '//video.renfei.oncdn.cn/2019/dajingmen-720p.mp4');
COMMIT;

-- ----------------------------
-- Table structure for t_video_source
-- ----------------------------
DROP TABLE IF EXISTS `t_video_source`;
CREATE TABLE `t_video_source` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `video_id` bigint(20) unsigned NOT NULL COMMENT '视频ID',
  `video_type` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '视频类型',
  `video_size` int(10) unsigned NOT NULL COMMENT '视频分辨率',
  `video_src` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '视频源地址',
  PRIMARY KEY (`id`),
  KEY `fk_video` (`video_id`),
  CONSTRAINT `fk_video` FOREIGN KEY (`video_id`) REFERENCES `t_video` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_video_source
-- ----------------------------
BEGIN;
INSERT INTO `t_video_source` VALUES (1, 1, 'video/mp4', 360, '//video.renfei.oncdn.cn/2019/dajingmen-360p.mp4');
INSERT INTO `t_video_source` VALUES (2, 1, 'video/mp4', 480, '//video.renfei.oncdn.cn/2019/dajingmen-480p.mp4');
INSERT INTO `t_video_source` VALUES (3, 1, 'video/mp4', 720, '//video.renfei.oncdn.cn/2019/dajingmen-720p.mp4');
INSERT INTO `t_video_source` VALUES (4, 1, 'video/mp4', 1080, '//video.renfei.oncdn.cn/2019/dajingmen-1080p.mp4');
COMMIT;

-- ----------------------------
-- Table structure for t_video_track
-- ----------------------------
DROP TABLE IF EXISTS `t_video_track`;
CREATE TABLE `t_video_track` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `video_id` bigint(20) unsigned NOT NULL,
  `kind` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `label` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `srclang` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `src` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_video_track` (`video_id`),
  CONSTRAINT `fk_video_track` FOREIGN KEY (`video_id`) REFERENCES `t_video` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for t_ipv4
-- ----------------------------
DROP TABLE IF EXISTS `t_ipv4`;
CREATE TABLE `t_ipv4` (
  `ip_from` int(10) unsigned DEFAULT NULL,
  `ip_to` int(10) unsigned DEFAULT NULL,
  `country_code` char(2) COLLATE utf8_bin DEFAULT NULL,
  `country_name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `region_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `city_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `zip_code` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `time_zone` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  KEY `idx_ip_from` (`ip_from`),
  KEY `idx_ip_to` (`ip_to`),
  KEY `idx_ip_from_to` (`ip_from`,`ip_to`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for t_ipv6
-- ----------------------------
DROP TABLE IF EXISTS `t_ipv6`;
CREATE TABLE `t_ipv6` (
  `ip_from` decimal(39,0) unsigned DEFAULT NULL,
  `ip_to` decimal(39,0) unsigned NOT NULL,
  `country_code` char(2) COLLATE utf8_bin DEFAULT NULL,
  `country_name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `region_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `city_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `zip_code` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `time_zone` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  KEY `idx_ip_from` (`ip_from`),
  KEY `idx_ip_to` (`ip_to`),
  KEY `idx_ip_from_to` (`ip_from`,`ip_to`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;

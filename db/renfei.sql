/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : renfei

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 05/06/2019 19:42:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `type_id` bigint(20) unsigned NOT NULL,
  `en_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `zh_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_category_type` (`type_id`),
  CONSTRAINT `fk_category_type` FOREIGN KEY (`type_id`) REFERENCES `t_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_category
-- ----------------------------
BEGIN;
INSERT INTO `t_category` VALUES (1, 1, 'default', '未分类');
COMMIT;

-- ----------------------------
-- Table structure for t_page
-- ----------------------------
DROP TABLE IF EXISTS `t_page`;
CREATE TABLE `t_page` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) unsigned NOT NULL,
  `title` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '页面标题',
  `content` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '页面内容',
  `views` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '浏览量',
  `release_time` datetime NOT NULL COMMENT '发布时间',
  `add_time` datetime NOT NULL COMMENT '添加时间',
  `describes` text COLLATE utf8mb4_unicode_ci COMMENT '简介用于SEO',
  `keyword` text COLLATE utf8mb4_unicode_ci COMMENT '关键字用于SEO',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '软删除',
  PRIMARY KEY (`id`),
  KEY `fk_page_category` (`category_id`),
  CONSTRAINT `fk_page_category` FOREIGN KEY (`category_id`) REFERENCES `t_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for t_posts
-- ----------------------------
DROP TABLE IF EXISTS `t_posts`;
CREATE TABLE `t_posts` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) unsigned NOT NULL COMMENT '文章分类',
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
  PRIMARY KEY (`id`),
  KEY `fk_posts_category` (`category_id`) USING BTREE COMMENT '与类别的外键约束',
  FULLTEXT KEY `ft_index` (`title`,`content`) COMMENT '全文检索',
  CONSTRAINT `fk_posts_category` FOREIGN KEY (`category_id`) REFERENCES `t_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_posts
-- ----------------------------
BEGIN;
INSERT INTO `t_posts` VALUES (1, 1, '你好！世界', '这是一个示例文章', 1, NULL, NULL, 0, '2019-06-04 13:30:21', '2019-06-04 13:30:27', NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for t_type
-- ----------------------------
DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `type_name` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `uri_path` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_type
-- ----------------------------
BEGIN;
INSERT INTO `t_type` VALUES (1, 'posts', '/posts');
INSERT INTO `t_type` VALUES (2, 'page', '/page');
INSERT INTO `t_type` VALUES (3, 'video', '/video');
INSERT INTO `t_type` VALUES (4, 'photo', '/photo');
COMMIT;

-- ----------------------------
-- Table structure for t_video
-- ----------------------------
DROP TABLE IF EXISTS `t_video`;
CREATE TABLE `t_video` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) unsigned NOT NULL,
  `title` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;

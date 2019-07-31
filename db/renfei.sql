SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
USE renfei;

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
INSERT INTO `t_account` VALUES (1, 'renfei', 'sha1:64000:18:rZNN9/MlvYHaiVoKioG7G0E7h1gbqUj/:NI4eEfylEvrElpkKIq8Atf9g', NULL, 0, 1, '2019-07-11 19:29:08', 2, '2019-07-13 20:01:58');
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
  PRIMARY KEY (`id`),
  KEY `fk_comment_type` (`type_id`),
  KEY `fk_comments` (`parent_id`),
  CONSTRAINT `fk_comment_type` FOREIGN KEY (`type_id`) REFERENCES `t_type` (`id`),
  CONSTRAINT `fk_comments` FOREIGN KEY (`parent_id`) REFERENCES `t_comments` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_comments
-- ----------------------------
BEGIN;
INSERT INTO `t_comments` VALUES (3, 1, 1, '测试名称3', 'lala@alala.af', 'http://www.www.www', '0:0:0:0:0:0:0:1', NULL, '2019-07-15 13:37:43', '这是评论内容', 0, NULL);
INSERT INTO `t_comments` VALUES (4, 1, 1, '测试名称4', 'lala@alala.af', 'http://www.www.www', '0:0:0:0:0:0:0:1', NULL, '2019-07-15 13:37:55', '这是评论内容', 0, 3);
INSERT INTO `t_comments` VALUES (5, 1, 1, '测试名称5', 'lala@alala.af', 'http://www.www.www', '0:0:0:0:0:0:0:1', NULL, '2019-07-15 13:38:04', '这是评论内容', 0, 4);
INSERT INTO `t_comments` VALUES (6, 1, 1, '测试名称6', 'lala@alala.af', 'http://www.www.www', '0:0:0:0:0:0:0:1', NULL, '2019-07-15 13:38:08', '这是评论内容', 0, 5);
INSERT INTO `t_comments` VALUES (7, 1, 1, '测试名称7', 'lala@alala.af', 'http://www.www.www', '0:0:0:0:0:0:0:1', NULL, '2019-07-15 13:38:10', '这是评论内容', 0, NULL);
INSERT INTO `t_comments` VALUES (8, 1, 1, '大丰港', 'gf@dfg', NULL, '0:0:0:0:0:0:0:1', NULL, '2019-07-15 16:00:31', '是东方航空法国红酒的放假的', 0, NULL);
INSERT INTO `t_comments` VALUES (9, 1, 1, 'fg', 'zfb', NULL, '0:0:0:0:0:0:0:1', NULL, '2019-07-15 16:00:45', 'zfdndaehaweg', 0, 4);
INSERT INTO `t_comments` VALUES (10, 1, 1, '吃', 'lala', 'ls', '0:0:0:0:0:0:0:1', NULL, '2019-07-26 19:33:37', '加上大公开', 0, NULL);
INSERT INTO `t_comments` VALUES (11, 1, 1, '测', 'g', 'adg', '0:0:0:0:0:0:0:1', NULL, '2019-07-27 10:46:53', 'adgfa日嘎阿萨德噶', 0, 10);
INSERT INTO `t_comments` VALUES (12, 1, 1, '发的', 'sdg', 'asdg', '0:0:0:0:0:0:0:1', NULL, '2019-07-27 10:53:43', '大风很大方式不', 0, 10);
INSERT INTO `t_comments` VALUES (13, 1, 1, '哈哈', 'aadf', 'https://www.baidu.com/', '0:0:0:0:0:0:0:1', NULL, '2019-07-27 11:03:32', 'https://www.baidu.com/', 0, 5);
INSERT INTO `t_comments` VALUES (14, 1, 3, '测试', 'er', NULL, '0:0:0:0:0:0:0:1', NULL, '2019-07-27 14:50:26', '视频测试', 0, NULL);
COMMIT;

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

-- ----------------------------
-- Table structure for t_link
-- ----------------------------
DROP TABLE IF EXISTS `t_link`;
CREATE TABLE `t_link` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `sitename` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '站点名称',
  `sitelink` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '站点链接',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `addtime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_link
-- ----------------------------
BEGIN;
INSERT INTO `t_link` VALUES (1, '苏岳宁博客', 'http://suyuening.com/', 0, '2019-07-29 19:35:54');
INSERT INTO `t_link` VALUES (2, '码农在线', 'https://www.netcode.cn/', 0, '2019-07-29 19:36:18');
INSERT INTO `t_link` VALUES (3, '辰云博客', 'https://www.itzcy.com/', 0, '2019-07-29 19:37:01');
INSERT INTO `t_link` VALUES (4, '逐梦博客', 'https://www.deanhan.cn/', 0, '2019-07-29 19:37:19');
INSERT INTO `t_link` VALUES (5, '杨小羽宠物资讯网', 'http://www.anlandy.com/', 0, '2019-07-29 19:38:07');
INSERT INTO `t_link` VALUES (6, '杜临风博客', 'http://www.linfeng.net/', 0, '2019-07-29 19:38:46');
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
  `menu_type` int(10) unsigned NOT NULL COMMENT '菜单类型，页头菜单还是页尾菜单',
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for t_permission_role
-- ----------------------------
DROP TABLE IF EXISTS `t_permission_role`;
CREATE TABLE `t_permission_role` (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) unsigned NOT NULL,
  `permission_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_permission_role` (`role_id`),
  KEY `fk_permission_permission` (`permission_id`),
  CONSTRAINT `fk_permission_permission` FOREIGN KEY (`permission_id`) REFERENCES `t_permission` (`id`),
  CONSTRAINT `fk_permission_role` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
  PRIMARY KEY (`id`),
  KEY `fk_posts_category` (`category_id`) USING BTREE COMMENT '与类别的外键约束',
  FULLTEXT KEY `ft_index` (`title`,`content`) /*!50100 WITH PARSER `ngram` */ ,
  CONSTRAINT `fk_posts_category` FOREIGN KEY (`category_id`) REFERENCES `t_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1003284 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_posts
-- ----------------------------
BEGIN;
INSERT INTO `t_posts` VALUES (1, 1, 'https://cdn.neilren.com/neilren4j/upload/b8bfb737a838450ca2e373ad4dd264f7.jpeg', '你好！世界程序', '<h2><span class=\"ez-toc-section\" id=\"tocjs\">toc.js</span></h2>\n<p>这个就是那段神奇的代码</p>\n<pre class=\"lang:js decode:true\" title=\"toc.js\"><code class=\"js\">!function(a){a.fn.toc=function(b){var c,d=this,e=a.extend({},jQuery.fn.toc.defaults,b),f=a(e.container),g=a(e.selectors,f),h=[],i=e.prefix+\"-active\",j=function(b){for(var c=0,d=arguments.length;d&gt;c;c++){var e=arguments[c],f=a(e);if(f.scrollTop()&gt;0)return f;f.scrollTop(1);var g=f.scrollTop()&gt;0;if(f.scrollTop(0),g)return f}return[]},k=j(e.container,\"body\",\"html\"),l=function(b){if(e.smoothScrolling){b.preventDefault();var c=a(b.target).attr(\"href\"),f=a(c);k.animate({scrollTop:f.offset().top},400,\"swing\",function(){location.hash=c})}a(\"li\",d).removeClass(i),a(b.target).parent().addClass(i)},m=function(b){c&amp;&amp;clearTimeout(c),c=setTimeout(function(){for(var b,c=a(window).scrollTop(),f=0,g=h.length;g&gt;f;f++)if(h[f]&gt;=c){a(\"li\",d).removeClass(i),b=a(\"li:eq(\"+(f-1)+\")\",d).addClass(i),e.onHighlight(b);break}},50)};return e.highlightOnScroll&amp;&amp;(a(window).bind(\"scroll\",m),m()),this.each(function(){var b=a(this),c=a(\"&lt;ul/&gt;\");g.each(function(d,f){var g=a(f);h.push(g.offset().top-e.highlightOffset);var i=(a(\"&lt;span/&gt;\").attr(\"id\",e.anchorName(d,f,e.prefix)).insertBefore(g),a(\"&lt;a/&gt;\").text(e.headerText(d,f,g)).attr(\"href\",\"#\"+e.anchorName(d,f,e.prefix)).bind(\"click\",function(c){l(c),b.trigger(\"selected\",a(this).attr(\"href\"))})),j=a(\"&lt;li/&gt;\").addClass(e.itemClass(d,f,g,e.prefix)).append(i);c.append(j)}),b.html(c)})},jQuery.fn.toc.defaults={container:\"body\",selectors:\"h1,h2,h3\",smoothScrolling:!0,prefix:\"toc\",onHighlight:function(){},highlightOnScroll:!0,highlightOffset:100,anchorName:function(a,b,c){return c+a},headerText:function(a,b,c){return c.text()},itemClass:function(a,b,c,d){return d+\"-\"+c[0].tagName.toLowerCase()}}}(jQuery);</code></pre>\n<h2><span class=\"ez-toc-section\" id=\"i\">生成目录程序生成目录程序生成目录程序生成目录程序生成目录程序</span></h2>\n<p>下面这两段程序，第一段没明白是做什么的，第二段是用来生成目录的程序。</p>\n<pre class=\"php\"><code class=php>//这个程序没看到有什么作用，可能是考虑了固定导航栏的高度\njQuery(document).on(\"click\", \"#toc a\", function (a) {\n    // #wpadminbar 世界替换了原来的 header\n    $(\"#wpadminbar\").animate({marginBottom: 130}, 200).animate({marginBottom: 30}, 200)\n});\n//这一段是用来生成目录的\njQuery(document).ready(function () {\n    return 0 === $(\".article_content h2\").length ? ($(\"#toc\").remove(), 0) : (jQuery(\"#toc\").toc({\n         selectors: \"h2,h3,h4\",\n          container: \".article_content\"\n     }), jQuery(\"#toc\").before(\"&lt;h2&gt;目录&lt;/h2&gt;\"), \"参考链接\" === $.trim($(\".article_content h2:nth-last-of-type(1)\").text()) &amp;&amp; $(\".article_content h2:nth-last-of-type(1)\").addClass(\"reference\").next(\"ul\").addClass(\"reference-list\"), void $(\"#toc~h2\").wrap(\'&lt;div class=\"chapter\" /&gt;\'))\n});</code></pre>\n<h2><span class=\"ez-toc-section\" id=\"i-2\">美化</span></h2>\n<p>下面的css 就是本页面目录所使用的样式</p>\n<h3><span class=\"ez-toc-section\" id=\"i-2\">H3</span></h3>\n<pre class=\"php\" title=\"css\"><code class=\"php\">    &lt;style&gt;\n        #toc {\n            /*background-color: #111;\n            box-shadow: -5px 0 5px 0 #000 inset;\n            color: #fff;\n            font-family: Consolas,\"Courier New\",Courier,FreeMono,monospace;\n            font-weight: 700;*/\n            margin-bottom: 2em;\n            padding-top: 20px;\n        }\n        #toc ul {\n            /*list-style: outside none none;*/\n            list-style:none;\n            margin: 0;\n            padding: 0;\n        }\n        #toc li {\n            padding: 5px 10px;\n            list-style:none;\n        }\n        #toc a {\n            color: #a6e22e;\n            display: block;\n            text-decoration: none;\n        }\n        #toc li:hover {\n            background: #369 none repeat scroll 0 0;\n            /*box-shadow: -5px 0 10px -5px #000 inset;*/\n        }\n        #toc li a:hover{\n            border:none;\n        }\n        #toc .toc-h2 {\n            padding-left: 2em;\n        }\n        #toc .toc-h3 {\n            padding-left: 4em;\n        }\n        #toc .toc-h4 {\n            padding-left: 6em;\n        }\n    &lt;/style&gt;</code></pre>\n<p>写在最后吧：<br />\n后来，我找到了官方项目地址，就算一次快乐的旅行吧：<br />\nhttp://projects.jga.me/toc/<br />\nhttps://github.com/jgallen23/toc</p>', 1, NULL, NULL, 17, '2019-06-04 13:30:21', '2019-06-04 13:30:27', '这个是简介', NULL, 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
-- Records of t_secret_key
-- ----------------------------
BEGIN;
INSERT INTO `t_secret_key` VALUES (0x01F352207F344AE5934E6096DEC919FE, 'MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC1rzBa1MMRXUk4msrdqBCFigNPkQHKZDm/pWqlyt8fCaGx1lbgSSY0ReoWkL4y42MH2a0xYFfC3zOwXu8uACt2FgtLGnYa3/gdth+ug0UPR+mvUrCrx33RMgJHl8ufhZekn1BIxVSwOZwUCOje7FA5/ESPiSQtd2ick2lfRJOpMxErZAFZ/7uPr0HinrDCPHphPMTJ+Zip1x0O6zp8lJ56N+MJ2E+/riV0sJhhLH0toP1VtoXiiFS7Eo6po8d3nLJ3ottx4Fg7UBq0QKDQIilpzxdsX/HGBAZmZaG6YT3wIRK79ta2kgYKDQ5HNMdIAVmbuPc8IXpUn7gaQP33k1VfAgMBAAECggEAWFZi43cZlYLPDDevNZD234cYTwKJZVfQAE+0iAIY9GVhei9mNE08xyPLSsd7wxBuYS3EmQJoCXcvEa1HDA3HE/Zp7U1jaXPGluOridWHqYNkqvtArXUkxqdTFm5G8jwpXECd75QKtlh8e/1NBL7PzzHKQCQFNBogAilmjYyxYw2ApFoT4g8kGS0As1o25mYwtB2hAIm5iNIDTiO5bSB2rrN1xNcwCBD9vdMChY6MW9w5/QZr/e1YHkotJmbX55xaMye0NYilHhnXaJhg9m18TfyxN1eJsOTObI8S0KDHwQZrN60Uar+3NrcLz5/mSAn1kuy5nW0d2p0SrXtc54oWAQKBgQDbyF5oO074AZo78AmL/ta48m6iLjckm7S7NVO/IKz7pynPJlVdP4Fmr/jCpnQsa7cFjnmG1ESn9TFYwcwPKBsK2jCMut0OGbbf1OLFTEinfDR5c2BBSKqyUiY3nD0/06Wz8tt+jxMPkhik/V3oGT0YZsgIj95LFPBoxBBsoG7CXwKBgQDTn5+vsauc5sJpZXOuL6U7BfnvXe7U/N+D30232O3dJCHD1irllpK2gMcIP/5hNSW7tPMoGyoBmQYt07GWsXADivQJLfzp2Nobi3yI0NGatXquQ95dU807GUn7729Oqy20ZNuFuVFtTW3jNqQCjnC4rgR9kg7biatlIt5vJBJNAQKBgQDapwzFRbvaoD2BQ907mdylQwst7iyODxpO5U7b5vYxpqzBmbFzg6qJLqidflw1lTQQIx5kNOnY7uaZFrScn5nHwipq17z0OkDljjXNc4cUmUzbRx2L1W982J2M1bNZvIYt8H7N+ilMZFyASFEUh4dwr1BytC7DI6HJmMV+TF3koQKBgQComXLKsj1sariW6bnpG5KDXLaMSwT9cV3OHXk4sL6JQcEY2icg9P58IkrvurQ60/H1Ayv6VvH3hefTWOYSjdkVKX5VVes6j4btDQsxoVRs8osEDZQ6PIq0u+girNa8EUr/fp8agdNqxpZ2wFCYrM0HhbSUKmpl49/GWqpmV5GxAQKBgAC0TPv+lORigfjrqd03YvieMlrbPF08MbSjhp7/hDE8NsFU/TVgwamBKCCKbsk66SaysEkRDGt1U0iPf1aCTGF4x1FUs4THET9xDhgSiwpeezjuT1/w5xbZUBjs/Erudh6laA3YObR1WtJMmTBV3zNK0uHp8nHhq0Lsmg7RDxV/', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCGmJT79G4wMby4gBF70ACL9Bkc\ntnMiXKfOTyUdmNPhD7heVjVmu9XiwW+EQhXlw8e5qbUPjhV/q9vrFCyZC9aeP6Fh\nlEeszfniRXdFSJAZx3YJLtOIGXUBdzqxZCv5tYcU6YIJWMHPruakJ2wLVwqyK269\nS0n9s8RNjbQoslRfxwIDAQAB', '9nlyyggk51lasfxm', '2020-07-08 08:53:36');
INSERT INTO `t_secret_key` VALUES (0x266354D811654722B392894D2B0A9D9B, 'MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCPd7HLO1dys+uDaXDGVobBYxTzIvhUTCl9FpdodsegF91Jlbfg1qSJiCFNyK0iE9ceLtLxliiH6k+GMzP2VyDFjAi+3tYFufFj2jIuRbV92JXum802UwrYzuPLOjTt2PONUq6TjDBgxjQktDKKqrUMvGtA8bGrsUzpD+pJGF1gRSGd2sdT45DXdF0INCCKEBpUaHgW/a9y2YU76jipOh7iUDux0Y/uVOK7FSt+2U5l6T1VAvfMO/TJ9fWxfApmQOwWkXjhE8bijcg4xEGOIkEmLD/GtJVTQ8JYNZIJQQtW7u2MeiSVooMV33jbow3FWvJb7el/uWUZSamatX/8ndMhAgMBAAECggEAUxZsBpp4e7qNhA7VvFgyNN+wK1uuXAu733DtARgBZstLYiluU62bnvcS6px1AI5jAFz31+gx0v01TzBqQJmXJjGCgyBE7Q4SKdUdSMUgZiDAPG9I+tQ/LO8s5+7u5mzGKKug5VzES9eeLT+bIcnZjuW0a+lOD3RgaX3JHsoYkf64xnLdGUL1ijd4XYMnxHh3kDajONR2xTvxbyFJ0FBqdT51PXK8gEbhFsDTjG+vnc/0Lx2uWl2j6shxeTCn+lGp6DOG14Fv2LvY6hB8koD56fhyeTV/LlSwpVR371Q3pmfEHBYOKxDX50iyriOCZqOx8LRU0k46Om+FbC1gYFCwQQKBgQDQW1is/ieJMI0lG78969OhudFwAG3AMlWiuXBDCyOFnjnNVd4+HBltBGvRQl0Dl79ps/7AGG7Q+1RMrV35EeJHGF9/bBA/OAXCfiNP7OMeedSjNEj1BMjYWRI5YYPpmrAJgqQhhgqEzAKEXxhgS6Kjrtx2xbQbCWWMOu7P2dZ4iQKBgQCwRec+oMN6PtSV1rpcoLrvufjo3PhjQGp6sVkczQmYoKQHXcPWG4MkMq+fhi2Nfq2wKE4fz9U34H1G4Zwq+vZxLhnOs46ErVGrDVg1x5nL72BskkI8v076d6Wsw74Ha2f04ZoaSFTGSQy3JLPYLQ0DAMvwa0LOJ7O2/Vmluwyv2QKBgE11aCYH5lLORerBwfsXLPo2xnHI9wQ4sjVokx+ZiH8oL3zyq0iT0bABsMN+1G19gJ+fVtm7O5ddmB1ZZlIuH90mZnpMhk26WgnQMviHrHer+y0hRu4v9SrZEjla01uJMmCWvMXpuwdbgKQNZlaGR9RqMXPGVAqpF6NmVVnYNi9hAoGBAJJAPV+p9OXtUWZMtWz68n7J9yoFhJScsnu/v2b0+3xF9DIkasKvKvySjUI3Cn52V1dr6X3da1OkD20K7Q6AEI7RKBpmwd11C+h29vQP+pvIrWEHbKuIMtf/A1e3RGhVhfgYYkpiLuwhWcpuSkUXA8+1DoAYDmKLcEFoLMQj0J/pAoGBAIqDz3osZ//tAoVLY63GvrlPztxMGnVxLa/A34aiqBi2XcxMRkS9YAtYeol4hZsS+cc0lxZQsmDvJWITnlh3gFQkjDvjHYIB8ABWl+wE/T6NvFlvXPCItBr7iWYr18/e5uFFxHhTo9I4bbjrSD3mBC/+xKX2mIEbULRVYEwLJutQ', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDO1FpLcLWRstlCdlaWUqzBdRK3\n3rqlEyznjnStqDO+sU6hB4qQ3CZ8/dvoBFF/hg0peRl1pDyk0umYxt1pZch+ShTO\nYbso7IqT/swWwSxPx/nJpzMOT/TaZuDfFwSfx9Q5Ae+y3ci0JyD75ZFZnqAFP74a\nUVqx7AN8OUtdli4S9QIDAQAB', '81tlh4zyfwataf8t', '2020-07-13 23:34:12');
INSERT INTO `t_secret_key` VALUES (0xFD20D0AAAB5C43DA82CAD90F0CDDE7D4, 'MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC6LVkwX6gUvp9hYXRkMAE5Y9sHBWbfLIBIP8tiiDQFbYi0TEj6yZEsM1nJv4O9f0WZIb7ttT2l4v2XBKdtOFw4ShFjG2AYO/lfsJBkpLuiVB53AQ5OON+zeO4U+CDimGl5wF9bVi1hcKyfu6UceXffht5a9VvI0/DA4eB9xnNCEyRUSu2XU1qN6um3Ta+YtygNczuTDixZkXcsm+FTE0qieCsCrCYSPVIRkRDm6fRwq0GIYZLB87JcXqnjJ8XaqDQDKb1Ikm68Hb7xTlJql1CCMXDxMmfnCLkHV0WZJot5RJwZahq7NIp+u132NqJAiNG5HAP0KmCiggxxQPTAG0sRAgMBAAECggEABduTaNA5q51rylaUoOpZv4KCy6/B0tfFmYBYV9tNN7aAjJux2CIrbSaYdp6N+JJekDjdlwES1XtU20GMOwWA8UxzS5j65whMClS11lTQioT3CGuCFbf4Yd82Lna4IikFeI0y8t69QsmWxNfUlBbGTMQjjt0VbDYu5wK0W4C486U0oJlWh9hPXPGp8/+nYqqIHkwleRqm02JACH/MMhe5rG3VzeGDOlJzpfEyzY4Av5PpchRmlO+bF/8/hKyYBaspB1KWtGX9KC8K6Qe5TMqQ+wox16Cr9g4PZ4oZuJHp7K0Miqtm+xW5COlZZ4qFkOhqgfWY7CKpfkUEoP5cak/RgQKBgQD/pH4cVIxPxQLdzBXhollTO0ONyOVk2iUZyXovLdt+GFAYuDPXgPiOZ3qPnfT+omlZTW/rd/Yb3es5A4wyApoHdznGGt+N9bptUIeI/QWMotyiBSiDfELmeMykc6BO0FLvjV2pG38/m5UjdL2uJ1C2w8zIu7F3y37MhhdQu+drWQKBgQC6b/2XwY5Hdpc8oManrx0s3ZNG1k+F8QMoKE4OR35YkC1BwH+eSwnrl5vAQqY163ml7zsRpRgnDiz7ryy/gzzEsjk2H/S7waU7RZIbDjzfuy51za6RZM+SyvDGD3iE18Cbcvoc5GaQ1HOo4885sa3fkTsCZHk0NU3gkaacPEo+eQKBgHPyCzCWOD1FwZMPKS15EMJrMqxv3XrsZb9YXg5f4hlCtbFAemLXn+QWksPohGGpD9btBEHr2rew4yqsrT1RaMLSFGYBcjtUpAVIbPB4sbejdiYV6NYZ2c3mJArCVobofXUgzMYSLpiO3AmEvTLrqfP6wjqYxljq9suUVabMUp2hAoGAVGMchfQ2UmJ7UhXz1TKOIiCo6QdYN2g/5Y1Yx13U8oFP+tMG8L9GXjzhYsTINt++0DzYn8P6irL/6WwA9S6R8vY4rycHv3SHNazn1Rc8togjH29Z9LWMvwTctsmJ45dEtxfz3eAEDModD/UNdy/VY7x42TxhICCAf4VtxIoFkQECgYEA+KmyXtRCKawK3fgD4k6Plu7pYW1HM8EwqBBvBNrE9Ih8Sa8YgeZ75KWxoac9KiYVEDiZ2yPmCoEvxP6gzIpzDujm0UnjcPmuyNmXm68qMPz5Nu300ugDpEs2p4Ae4C1yWAFVusJDBlsb6w8xPj7yQBCgtSaVW0qtdcLx1+zk5qU=', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqPTSUrBkPXIsvRtdH8ZhBLU6p\nTmz417Nr7dPj08Dmn+sl82RUcRIznf/dvqwUub0bTqAyIdqSp7A5GTrfBFFlpFV7\nWKWpLZbx+3T/9NvyLrkRELut8gVG+FvE5ZuSnpbubnHy7gQk1woamW0iOcip/tyl\nO6AdMe+hnunFEWejYwIDAQAB', '1im82xmsfwthodqx', '2020-07-13 23:33:12');
COMMIT;

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
INSERT INTO `t_setting` VALUES (22, 'comment_switch', '1', NULL);
INSERT INTO `t_setting` VALUES (23, 'css', '/css/animate.min.css', 6);
INSERT INTO `t_setting` VALUES (24, 'css', '/css/lightbox.css', 7);
INSERT INTO `t_setting` VALUES (25, 'css', '/css/main.css', 8);
INSERT INTO `t_setting` VALUES (26, 'css', '/css/responsive.css', 9);
INSERT INTO `t_setting` VALUES (27, 'jss', '/js/lightbox.min.js', 7);
INSERT INTO `t_setting` VALUES (28, 'jss', '/js/wow.min.js', 8);
INSERT INTO `t_setting` VALUES (29, 'jss', '/js/main.js', 10);
INSERT INTO `t_setting` VALUES (30, 'jss', '//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-5d3ad3355b39d550', 9);
COMMIT;

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `en_name` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `zh_name` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `describe` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_token
-- ----------------------------
BEGIN;
INSERT INTO `t_token` VALUES (1, 'renfei', 'eyJhbGciOiJFUzUxMiJ9.eyJpc3MiOiJSZW5GZWkuTmV0IEFQSSBDZW50ZXIiLCJzdWIiOiJURUpOZWU5NHJCK1FvanpFQnc3eEFOaGtHVGNVZytrTWxaZDltQ080L2kwPSIsImF1ZCI6ImQvOUpkSVZHR29SQ0cweVRlVFR5UlE9PSIsImlhdCI6MTU2MzAzMTQ2OCwianRpIjoiMzBjNWViMWMtMjQ5OC00NjE3LWIyZGYtODAwZjZmMjgzNDE2IiwiZXhwIjoxNTYzNjM2MjY4LCJuYmYiOjE1NjMwMzE0Njh9.ADIwGtZqzMrmi62SWteFZ8zO1pmwfybisZPijd7QRzAcevFkjVq1kvk3tFMwPZv55xSgVPmpiarxekQg-JRlXItaAEdGzg06dxSOqdxTKN_oPHNwk7SEUXt-ZLl5KHSQYkMNmf2kqTpL1djwK-mn5kqQiQgy3TQ2r606OoeW3nGi-6BK', 0, '2019-07-13 23:54:29', NULL);
INSERT INTO `t_token` VALUES (2, 'renfei', 'eyJhbGciOiJFUzUxMiJ9.eyJpc3MiOiJSZW5GZWkuTmV0IEFQSSBDZW50ZXIiLCJzdWIiOiJURUpOZWU5NHJCK1FvanpFQnc3eEFOaGtHVGNVZytrTWxaZDltQ080L2kwPSIsImF1ZCI6ImQvOUpkSVZHR29SQ0cweVRlVFR5UlE9PSIsImlhdCI6MTU2MzAzMTk5OCwianRpIjoiNWI1ZjBiNzAtZjU0ZS00OTg0LWFkOGQtM2Q2ZjMxYWZiNjI1IiwiZXhwIjoxNTYzNjM2Nzk4LCJuYmYiOjE1NjMwMzE5OTh9.AI_4WBLgfeR1yl9TiyCOdc1uej5tyUkXuB0N9gxsl8vHtET_hQUMIEGyECQQYG9Dm5Q7HM6MCE7mr2leIsMpwQVgAC-y_BbDiqcHu42-nKCuaWWuLPlag9rteW4BzDRC9PINQDLTnHNCn4JN0rGXRCaK_EHRJEu-m-MlPQJou7MGVHOX', 0, '2019-07-14 00:03:18', NULL);
INSERT INTO `t_token` VALUES (3, 'renfei', 'eyJhbGciOiJFUzUxMiJ9.eyJpc3MiOiJSZW5GZWkuTmV0IEFQSSBDZW50ZXIiLCJzdWIiOiJURUpOZWU5NHJCK1FvanpFQnc3eEFOaGtHVGNVZytrTWxaZDltQ080L2kwPSIsImF1ZCI6ImQvOUpkSVZHR29SQ0cweVRlVFR5UlE9PSIsImlhdCI6MTU2MzAzMjA1NSwianRpIjoiOGRjNjRlYzQtMDg4Yy00NDEyLWI1MmEtMzRhZmZhMTQ1MTEzIiwiZXhwIjoxNTYzNjM2ODU1LCJuYmYiOjE1NjMwMzIwNTV9.AT4WMswA52qw7_uX5WuTf3XiVva2fnchTSSviyyF_c9XpeFcjV_OvYjL29KalV66vbO2Xkxww24aDnyVPofq4JyZAVKcYf-kDxDX364FYDEjJFpIcf9a_Ks-ElRl96WnaQA_phUN-D8ESpWMSlRYvv4cPVEN0Y9rrFLj_XEe8o4lnAah', 0, '2019-07-14 00:04:15', NULL);
INSERT INTO `t_token` VALUES (4, 'renfei', 'eyJhbGciOiJFUzUxMiJ9.eyJpc3MiOiJSZW5GZWkuTmV0IEFQSSBDZW50ZXIiLCJzdWIiOiJURUpOZWU5NHJCK1FvanpFQnc3eEFOaGtHVGNVZytrTWxaZDltQ080L2kwPSIsImF1ZCI6ImQvOUpkSVZHR29SQ0cweVRlVFR5UlE9PSIsImlhdCI6MTU2MzI0ODE1NywianRpIjoiZmJmOTkxOWQtMjhkMC00YTJmLWI1YTctZDliMmM3OGM2ZGZkIiwiZXhwIjoxNTYzODUyOTU3LCJuYmYiOjE1NjMyNDgxNTd9.AEJPd1PE4BgY8dH5n29zlVc-8qmHukZU62_zL_8Y47oOGT5AKQvjb27b1rK_fN3-p_GNcSiOa4DcuDwlOSyBGzfpAFb3QeBAiv5IS7eJXpHG8Abak2vOCd1g6f2nhvD3mULpRHk03_5HbcFTmyqrSXNID7TG9SAW16g8yPOsyFRLVebz', 0, '2019-07-16 12:05:58', NULL);
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
INSERT INTO `t_type` VALUES (1, 'Posts', '/posts');
INSERT INTO `t_type` VALUES (2, 'Pages', '/page');
INSERT INTO `t_type` VALUES (3, 'Video', '/video');
INSERT INTO `t_type` VALUES (4, 'Photo', '/photo');
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

-- ----------------------------
-- View structure for v_allinfo
-- ----------------------------
DROP VIEW IF EXISTS `v_allinfo`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `v_allinfo` AS select `t_category`.`type_id` AS `type_id`,`t_category`.`zh_name` AS `cat_name`,`t_category`.`en_name` AS `cat_ename`,`t_posts`.`category_id` AS `category_id`,`t_posts`.`id` AS `id`,`t_posts`.`title` AS `title`,`t_posts`.`featured_image` AS `featured_image`,`t_posts`.`describes` AS `describes`,`t_posts`.`release_time` AS `release_time` from (`t_category` join `t_posts` on((`t_category`.`id` = `t_posts`.`category_id`))) where ((`t_posts`.`is_delete` = 0) and (`t_posts`.`release_time` <= now())) union all select `t_category`.`type_id` AS `type_id`,`t_category`.`zh_name` AS `cat_name`,`t_category`.`en_name` AS `cat_ename`,`t_video`.`category_id` AS `category_id`,`t_video`.`id` AS `id`,`t_video`.`title` AS `title`,`t_video`.`featured_image` AS `featured_image`,`t_video`.`describes` AS `describes`,`t_video`.`release_time` AS `release_time` from (`t_category` join `t_video` on((`t_category`.`id` = `t_video`.`category_id`))) where ((`t_video`.`is_delete` = 0) and (`t_video`.`release_time` <= now())) union all select `t_category`.`type_id` AS `type_id`,`t_category`.`zh_name` AS `cat_name`,`t_category`.`en_name` AS `cat_ename`,`t_page`.`category_id` AS `category_id`,`t_page`.`id` AS `id`,`t_page`.`title` AS `title`,`t_page`.`featured_image` AS `featured_image`,`t_page`.`describes` AS `describes`,`t_page`.`release_time` AS `release_time` from (`t_category` join `t_page` on((`t_category`.`id` = `t_page`.`category_id`))) where ((`t_page`.`is_delete` = 0) and (`t_page`.`release_time` <= now())) union all select `t_category`.`type_id` AS `type_id`,`t_category`.`zh_name` AS `cat_name`,`t_category`.`en_name` AS `cat_ename`,`t_photo`.`category_id` AS `category_id`,`t_photo`.`id` AS `id`,`t_photo`.`title` AS `title`,`t_photo`.`featured_image` AS `featured_image`,`t_photo`.`describes` AS `describes`,`t_photo`.`release_time` AS `release_time` from (`t_category` join `t_photo` on((`t_category`.`id` = `t_photo`.`category_id`))) where ((`t_photo`.`is_delete` = 0) and (`t_photo`.`release_time` <= now()));

SET FOREIGN_KEY_CHECKS = 1;

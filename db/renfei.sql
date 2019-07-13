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
INSERT INTO `t_account` VALUES (1, 'renfei', 'sha1:64000:18:rZNN9/MlvYHaiVoKioG7G0E7h1gbqUj/:NI4eEfylEvrElpkKIq8Atf9g', NULL, 0, 1, '2019-07-11 19:29:08', 0, '2019-07-13 20:01:58');
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
INSERT INTO `t_menu` VALUES (9, 0, '测试', '#', 1, 1, 3, 0);
INSERT INTO `t_menu` VALUES (10, 0, '© RENFEI.NET 2019', 'javascript:void(0)', 0, 1, 3, 9999999);
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_page
-- ----------------------------
BEGIN;
INSERT INTO `t_page` VALUES (1, 6, '隐私声明和 Cookie', 'https://cdn.neilren.com/neilren4j/upload/b8bfb737a838450ca2e373ad4dd264f7.jpeg', '<style>\n\n            </style>\n            <div class=\"about\">\n                <div class=\"about-title\">\n                    <h1>隐私声明和 Cookie</h1>\n                    <p>Legal Resources</p>\n                </div>\n                <p>我们十分重视你的隐私。 本隐私声明阐述了 RENFEI.NET 处理的个人数据以及 RENFEI.NET 处理个人数据的方式和目的。</p>\n                <p>请阅读本隐私声明中特定于服务的详细信息，这些详细信息提供了其他相关信息。 此声明适用于 RENFEI.NET 与你之间的互动、 RENFEI.NET 提供的所有服务。</p>\n            </div>\n            <article class=\"d-flex\">\n                <div class=\"row content d-flex\">\n                    <div class=\"col-md-3 px-3\" style=\"font-size: 12px;\">\n                        <div>\n                            <ul style=\"list-style: none;font-size: 14px;\">\n                                <li><a href=\"#1\">我们收集的个人数据</a></li>\n                                <li><a href=\"#2\">我们如何使用个人数据</a></li>\n                                <li><a href=\"#3\">Cookie 和类似技术</a></li>\n                                <li><a href=\"#4\">搜索和人工智能</a></li>\n                            </ul>\n                        </div>\n                        <div style=\"margin-top: 40px;\">\n                            <h4>Cookie</h4>\n                            <p>\n                                大多数 RENFEI.NET 站点使用 Cookie - Cookie 是放置在设备上的小文本文件，域中放置 Cookie 的 Web\n                                服务器稍后可以检索这些小文本文件。 我们使用 Cookie 来存储你的首选项和设置、帮助登录、提供定向广告，以及分析站点运行。\n                            </p>\n                        </div>\n                    </div>\n                    <div class=\"col-md-7 px-3\">\n                        <h2 style=\"margin-bottom: 20px;\" id=\"1\">我们收集的个人数据</h2>\n                        <div class=\"gray-bar\" style=\"margin-bottom: 20px;\"></div>\n                        <p>\n                            我们会收集你的数据作为分析来源，其中包括但不限于：你的设备信息、IP地址、访问行为信息。我们收集的数据取决于你与 RENFEI.NET 互动的环境、你所做的选择。\n                        </p>\n                        <p>\n                            在你使用 RENFEI.NET 提供的服务时，将默认为您已经阅读并同意了我们的隐私条款。\n                        </p>\n                        <h2 style=\"margin-bottom: 20px;\" id=\"2\">我们如何使用个人数据</h2>\n                        <div class=\"gray-bar\" style=\"margin-bottom: 20px;\"></div>\n                        <p>\n                            RENFEI.NET 使用我们收集的数据为你提供丰富的交互式体验。给您提供更有价值的信息。\n                        </p>\n                        <p>\n                            我们还有可能根据您的信息进行广告推荐、性能分析调查。\n                        </p>\n                        <p></p>\n                        <h2 style=\"margin-bottom: 20px;\" id=\"3\">Cookie 和类似技术</h2>\n                        <div class=\"gray-bar\" style=\"margin-bottom: 20px;\"></div>\n                        <p>\n                            Cookie 是放置在设备上的小型文本文件，用于存储数据，域中放置 Cookie 的 Web 服务器可以调用这些数据。 我们使用 Cookie\n                            和类似技术来存储和遵守你的偏好和设置、使你能够登录、提供基于兴趣的广告、打击欺诈行为、分析我们服务的性能以及实现其他合法目的。\n                        </p>\n                        <p>我们也使用“Web 信号”帮助提供 Cookie 和收集用法和性能数据。 我们的网站可能包含来自第三方服务提供商的 Web 信号、Cookie 或类似技术。</p>\n                        <p>\n                            你有各种用于控制 Cookie、Web 信号和类似技术所收集的数据的工具。 例如，你可以使用 Internet 浏览器中的控件来限制你所访问的网站可如何使用\n                            Cookie，并通过清除或阻止 Cookie 来撤消同意。\n                        </p>\n                        <h2 style=\"margin-bottom: 20px;\" id=\"4\">搜索和人工智能</h2>\n                        <div class=\"gray-bar\" style=\"margin-bottom: 20px;\"></div>\n                        <p>搜索和人工智能产品会将你与信息联系起来，并智能地感知、处理和处置信息—随着时间的推移进行学习和适应。</p>\n                    </div>\n                </div>\n            </article>', 0, '2019-06-15 22:32:51', '2019-06-15 22:32:56', '我们十分重视你的隐私。 本隐私声明阐述了 RENFEI.NET 处理的个人数据以及 RENFEI.NET 处理个人数据的方式和目的。', NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
  `descritpion` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `url` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `method` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
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
  `uri` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '照片地址',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
INSERT INTO `t_posts` VALUES (1, 1, 'https://cdn.neilren.com/neilren4j/upload/b8bfb737a838450ca2e373ad4dd264f7.jpeg', '你好！世界程序', '<h2><span class=\"ez-toc-section\" id=\"tocjs\">toc.js</span></h2>\n<p>这个就是那段神奇的代码</p>\n<pre class=\"lang:js decode:true\" title=\"toc.js\"><code class=\"js\">!function(a){a.fn.toc=function(b){var c,d=this,e=a.extend({},jQuery.fn.toc.defaults,b),f=a(e.container),g=a(e.selectors,f),h=[],i=e.prefix+\"-active\",j=function(b){for(var c=0,d=arguments.length;d&gt;c;c++){var e=arguments[c],f=a(e);if(f.scrollTop()&gt;0)return f;f.scrollTop(1);var g=f.scrollTop()&gt;0;if(f.scrollTop(0),g)return f}return[]},k=j(e.container,\"body\",\"html\"),l=function(b){if(e.smoothScrolling){b.preventDefault();var c=a(b.target).attr(\"href\"),f=a(c);k.animate({scrollTop:f.offset().top},400,\"swing\",function(){location.hash=c})}a(\"li\",d).removeClass(i),a(b.target).parent().addClass(i)},m=function(b){c&amp;&amp;clearTimeout(c),c=setTimeout(function(){for(var b,c=a(window).scrollTop(),f=0,g=h.length;g&gt;f;f++)if(h[f]&gt;=c){a(\"li\",d).removeClass(i),b=a(\"li:eq(\"+(f-1)+\")\",d).addClass(i),e.onHighlight(b);break}},50)};return e.highlightOnScroll&amp;&amp;(a(window).bind(\"scroll\",m),m()),this.each(function(){var b=a(this),c=a(\"&lt;ul/&gt;\");g.each(function(d,f){var g=a(f);h.push(g.offset().top-e.highlightOffset);var i=(a(\"&lt;span/&gt;\").attr(\"id\",e.anchorName(d,f,e.prefix)).insertBefore(g),a(\"&lt;a/&gt;\").text(e.headerText(d,f,g)).attr(\"href\",\"#\"+e.anchorName(d,f,e.prefix)).bind(\"click\",function(c){l(c),b.trigger(\"selected\",a(this).attr(\"href\"))})),j=a(\"&lt;li/&gt;\").addClass(e.itemClass(d,f,g,e.prefix)).append(i);c.append(j)}),b.html(c)})},jQuery.fn.toc.defaults={container:\"body\",selectors:\"h1,h2,h3\",smoothScrolling:!0,prefix:\"toc\",onHighlight:function(){},highlightOnScroll:!0,highlightOffset:100,anchorName:function(a,b,c){return c+a},headerText:function(a,b,c){return c.text()},itemClass:function(a,b,c,d){return d+\"-\"+c[0].tagName.toLowerCase()}}}(jQuery);</code></pre>\n<h2><span class=\"ez-toc-section\" id=\"i\">生成目录程序生成目录程序生成目录程序生成目录程序生成目录程序</span></h2>\n<p>下面这两段程序，第一段没明白是做什么的，第二段是用来生成目录的程序。</p>\n<pre class=\"php\"><code class=php>//这个程序没看到有什么作用，可能是考虑了固定导航栏的高度\njQuery(document).on(\"click\", \"#toc a\", function (a) {\n    // #wpadminbar 世界替换了原来的 header\n    $(\"#wpadminbar\").animate({marginBottom: 130}, 200).animate({marginBottom: 30}, 200)\n});\n//这一段是用来生成目录的\njQuery(document).ready(function () {\n    return 0 === $(\".article_content h2\").length ? ($(\"#toc\").remove(), 0) : (jQuery(\"#toc\").toc({\n         selectors: \"h2,h3,h4\",\n          container: \".article_content\"\n     }), jQuery(\"#toc\").before(\"&lt;h2&gt;目录&lt;/h2&gt;\"), \"参考链接\" === $.trim($(\".article_content h2:nth-last-of-type(1)\").text()) &amp;&amp; $(\".article_content h2:nth-last-of-type(1)\").addClass(\"reference\").next(\"ul\").addClass(\"reference-list\"), void $(\"#toc~h2\").wrap(\'&lt;div class=\"chapter\" /&gt;\'))\n});</code></pre>\n<h2><span class=\"ez-toc-section\" id=\"i-2\">美化</span></h2>\n<p>下面的css 就是本页面目录所使用的样式</p>\n<h3><span class=\"ez-toc-section\" id=\"i-2\">H3</span></h3>\n<pre class=\"php\" title=\"css\"><code class=\"php\">    &lt;style&gt;\n        #toc {\n            /*background-color: #111;\n            box-shadow: -5px 0 5px 0 #000 inset;\n            color: #fff;\n            font-family: Consolas,\"Courier New\",Courier,FreeMono,monospace;\n            font-weight: 700;*/\n            margin-bottom: 2em;\n            padding-top: 20px;\n        }\n        #toc ul {\n            /*list-style: outside none none;*/\n            list-style:none;\n            margin: 0;\n            padding: 0;\n        }\n        #toc li {\n            padding: 5px 10px;\n            list-style:none;\n        }\n        #toc a {\n            color: #a6e22e;\n            display: block;\n            text-decoration: none;\n        }\n        #toc li:hover {\n            background: #369 none repeat scroll 0 0;\n            /*box-shadow: -5px 0 10px -5px #000 inset;*/\n        }\n        #toc li a:hover{\n            border:none;\n        }\n        #toc .toc-h2 {\n            padding-left: 2em;\n        }\n        #toc .toc-h3 {\n            padding-left: 4em;\n        }\n        #toc .toc-h4 {\n            padding-left: 6em;\n        }\n    &lt;/style&gt;</code></pre>\n<p>写在最后吧：<br />\n后来，我找到了官方项目地址，就算一次快乐的旅行吧：<br />\nhttp://projects.jga.me/toc/<br />\nhttps://github.com/jgallen23/toc</p>', 1, NULL, NULL, 0, '2019-06-04 13:30:21', '2019-06-04 13:30:27', '这个是简介', NULL, 0, 1);
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
-- Table structure for t_setting
-- ----------------------------
DROP TABLE IF EXISTS `t_setting`;
CREATE TABLE `t_setting` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `keys` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '键',
  `values` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '值',
  `orders` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_setting
-- ----------------------------
BEGIN;
INSERT INTO `t_setting` VALUES (1, 'sitename', 'The RenFei Blog', NULL);
INSERT INTO `t_setting` VALUES (2, 'domain', 'http://localhost:8091', NULL);
INSERT INTO `t_setting` VALUES (3, 'highlight', '/font/highlight/highlight.pack.js', NULL);
INSERT INTO `t_setting` VALUES (4, 'staticdomain', 'localhost:8091', NULL);
INSERT INTO `t_setting` VALUES (5, 'css', '/font/font-awesome/4.7.0/css/font-awesome.min.css', NULL);
INSERT INTO `t_setting` VALUES (6, 'css', '/css/style.css', NULL);
INSERT INTO `t_setting` VALUES (7, 'jss', '/font/jquery/jquery-3.4.1.min.js', NULL);
INSERT INTO `t_setting` VALUES (8, 'description', ' ', NULL);
INSERT INTO `t_setting` VALUES (9, 'script', ' ', NULL);
INSERT INTO `t_setting` VALUES (10, 'analyticscode', '<!-- Global site tag (gtag.js) - Google Analytics -->\n<script async src=\"https://www.googletagmanager.com/gtag/js?id=UA-141370164-1\"></script>\n<script>\n    window.dataLayer = window.dataLayer || [];\n    function gtag(){dataLayer.push(arguments);}\n    gtag(\'js\', new Date());\n\n    gtag(\'config\', \'UA-141370164-1\');\n</script>', NULL);
INSERT INTO `t_setting` VALUES (11, 'footermenu', '测试|#', NULL);
INSERT INTO `t_setting` VALUES (12, 'jss', '/js/scrolltopcontrol.js', NULL);
INSERT INTO `t_setting` VALUES (13, 'homebanner', '/img/banner/home_banner.jpg', NULL);
INSERT INTO `t_setting` VALUES (14, 'jss', '//qzonestyle.gtimg.cn/qzone/v6/portal/gy/404/data.js', NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
  PRIMARY KEY (`id`),
  FULLTEXT KEY `ft_index` (`title`,`describes`) /*!50100 WITH PARSER `ngram` */ 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- View structure for v_allinfo
-- ----------------------------
DROP VIEW IF EXISTS `v_allinfo`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `v_allinfo` AS select `t_category`.`type_id` AS `type_id`,`t_category`.`zh_name` AS `cat_name`,`t_category`.`en_name` AS `cat_ename`,`t_posts`.`category_id` AS `category_id`,`t_posts`.`id` AS `id`,`t_posts`.`title` AS `title`,`t_posts`.`featured_image` AS `featured_image`,`t_posts`.`describes` AS `describes`,`t_posts`.`release_time` AS `release_time` from (`t_category` join `t_posts` on((`t_category`.`id` = `t_posts`.`category_id`))) where ((`t_posts`.`is_delete` = 0) and (`t_posts`.`release_time` <= now())) union all select `t_category`.`type_id` AS `type_id`,`t_category`.`zh_name` AS `cat_name`,`t_category`.`en_name` AS `cat_ename`,`t_video`.`category_id` AS `category_id`,`t_video`.`id` AS `id`,`t_video`.`title` AS `title`,`t_video`.`featured_image` AS `featured_image`,`t_video`.`describes` AS `describes`,`t_video`.`release_time` AS `release_time` from (`t_category` join `t_video` on((`t_category`.`id` = `t_video`.`category_id`))) where ((`t_video`.`is_delete` = 0) and (`t_video`.`release_time` <= now())) union all select `t_category`.`type_id` AS `type_id`,`t_category`.`zh_name` AS `cat_name`,`t_category`.`en_name` AS `cat_ename`,`t_page`.`category_id` AS `category_id`,`t_page`.`id` AS `id`,`t_page`.`title` AS `title`,`t_page`.`featured_image` AS `featured_image`,`t_page`.`describes` AS `describes`,`t_page`.`release_time` AS `release_time` from (`t_category` join `t_page` on((`t_category`.`id` = `t_page`.`category_id`))) where ((`t_page`.`is_delete` = 0) and (`t_page`.`release_time` <= now())) union all select `t_category`.`type_id` AS `type_id`,`t_category`.`zh_name` AS `cat_name`,`t_category`.`en_name` AS `cat_ename`,`t_photo`.`category_id` AS `category_id`,`t_photo`.`id` AS `id`,`t_photo`.`title` AS `title`,`t_photo`.`featured_image` AS `featured_image`,`t_photo`.`describes` AS `describes`,`t_photo`.`release_time` AS `release_time` from (`t_category` join `t_photo` on((`t_category`.`id` = `t_photo`.`category_id`))) where ((`t_photo`.`is_delete` = 0) and (`t_photo`.`release_time` <= now()));

SET FOREIGN_KEY_CHECKS = 1;

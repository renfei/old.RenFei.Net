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

 Date: 10/06/2019 23:32:47
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
  CONSTRAINT `fk_page_category` FOREIGN KEY (`category_id`) REFERENCES `t_category` (`id`)
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
  FULLTEXT KEY `ft_index` (`content`,`title`) COMMENT '全文检索',
  CONSTRAINT `fk_posts_category` FOREIGN KEY (`category_id`) REFERENCES `t_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of t_posts
-- ----------------------------
BEGIN;
INSERT INTO `t_posts` VALUES (1, 1, 'https://cdn.neilren.com/neilren4j/upload/b8bfb737a838450ca2e373ad4dd264f7.jpeg', '你好！世界程序', '<h2><span class=\"ez-toc-section\" id=\"tocjs\">toc.js</span></h2>\n<p>这个就是那段神奇的代码</p>\n<pre class=\"lang:js decode:true\" title=\"toc.js\"><code class=\"js\">!function(a){a.fn.toc=function(b){var c,d=this,e=a.extend({},jQuery.fn.toc.defaults,b),f=a(e.container),g=a(e.selectors,f),h=[],i=e.prefix+\"-active\",j=function(b){for(var c=0,d=arguments.length;d&gt;c;c++){var e=arguments[c],f=a(e);if(f.scrollTop()&gt;0)return f;f.scrollTop(1);var g=f.scrollTop()&gt;0;if(f.scrollTop(0),g)return f}return[]},k=j(e.container,\"body\",\"html\"),l=function(b){if(e.smoothScrolling){b.preventDefault();var c=a(b.target).attr(\"href\"),f=a(c);k.animate({scrollTop:f.offset().top},400,\"swing\",function(){location.hash=c})}a(\"li\",d).removeClass(i),a(b.target).parent().addClass(i)},m=function(b){c&amp;&amp;clearTimeout(c),c=setTimeout(function(){for(var b,c=a(window).scrollTop(),f=0,g=h.length;g&gt;f;f++)if(h[f]&gt;=c){a(\"li\",d).removeClass(i),b=a(\"li:eq(\"+(f-1)+\")\",d).addClass(i),e.onHighlight(b);break}},50)};return e.highlightOnScroll&amp;&amp;(a(window).bind(\"scroll\",m),m()),this.each(function(){var b=a(this),c=a(\"&lt;ul/&gt;\");g.each(function(d,f){var g=a(f);h.push(g.offset().top-e.highlightOffset);var i=(a(\"&lt;span/&gt;\").attr(\"id\",e.anchorName(d,f,e.prefix)).insertBefore(g),a(\"&lt;a/&gt;\").text(e.headerText(d,f,g)).attr(\"href\",\"#\"+e.anchorName(d,f,e.prefix)).bind(\"click\",function(c){l(c),b.trigger(\"selected\",a(this).attr(\"href\"))})),j=a(\"&lt;li/&gt;\").addClass(e.itemClass(d,f,g,e.prefix)).append(i);c.append(j)}),b.html(c)})},jQuery.fn.toc.defaults={container:\"body\",selectors:\"h1,h2,h3\",smoothScrolling:!0,prefix:\"toc\",onHighlight:function(){},highlightOnScroll:!0,highlightOffset:100,anchorName:function(a,b,c){return c+a},headerText:function(a,b,c){return c.text()},itemClass:function(a,b,c,d){return d+\"-\"+c[0].tagName.toLowerCase()}}}(jQuery);</code></pre>\n<h2><span class=\"ez-toc-section\" id=\"i\">生成目录程序生成目录程序生成目录程序生成目录程序生成目录程序</span></h2>\n<p>下面这两段程序，第一段没明白是做什么的，第二段是用来生成目录的程序。</p>\n<pre class=\"php\"><code class=php>//这个程序没看到有什么作用，可能是考虑了固定导航栏的高度\njQuery(document).on(\"click\", \"#toc a\", function (a) {\n    // #wpadminbar 世界替换了原来的 header\n    $(\"#wpadminbar\").animate({marginBottom: 130}, 200).animate({marginBottom: 30}, 200)\n});\n//这一段是用来生成目录的\njQuery(document).ready(function () {\n    return 0 === $(\".article_content h2\").length ? ($(\"#toc\").remove(), 0) : (jQuery(\"#toc\").toc({\n         selectors: \"h2,h3,h4\",\n          container: \".article_content\"\n     }), jQuery(\"#toc\").before(\"&lt;h2&gt;目录&lt;/h2&gt;\"), \"参考链接\" === $.trim($(\".article_content h2:nth-last-of-type(1)\").text()) &amp;&amp; $(\".article_content h2:nth-last-of-type(1)\").addClass(\"reference\").next(\"ul\").addClass(\"reference-list\"), void $(\"#toc~h2\").wrap(\'&lt;div class=\"chapter\" /&gt;\'))\n});</code></pre>\n<h2><span class=\"ez-toc-section\" id=\"i-2\">美化</span></h2>\n<p>下面的css 就是本页面目录所使用的样式</p>\n<h3><span class=\"ez-toc-section\" id=\"i-2\">H3</span></h3>\n<pre class=\"php\" title=\"css\"><code class=\"php\">    &lt;style&gt;\n        #toc {\n            /*background-color: #111;\n            box-shadow: -5px 0 5px 0 #000 inset;\n            color: #fff;\n            font-family: Consolas,\"Courier New\",Courier,FreeMono,monospace;\n            font-weight: 700;*/\n            margin-bottom: 2em;\n            padding-top: 20px;\n        }\n        #toc ul {\n            /*list-style: outside none none;*/\n            list-style:none;\n            margin: 0;\n            padding: 0;\n        }\n        #toc li {\n            padding: 5px 10px;\n            list-style:none;\n        }\n        #toc a {\n            color: #a6e22e;\n            display: block;\n            text-decoration: none;\n        }\n        #toc li:hover {\n            background: #369 none repeat scroll 0 0;\n            /*box-shadow: -5px 0 10px -5px #000 inset;*/\n        }\n        #toc li a:hover{\n            border:none;\n        }\n        #toc .toc-h2 {\n            padding-left: 2em;\n        }\n        #toc .toc-h3 {\n            padding-left: 4em;\n        }\n        #toc .toc-h4 {\n            padding-left: 6em;\n        }\n    &lt;/style&gt;</code></pre>\n<p>写在最后吧：<br />\n后来，我找到了官方项目地址，就算一次快乐的旅行吧：<br />\nhttp://projects.jga.me/toc/<br />\nhttps://github.com/jgallen23/toc</p>', 1, NULL, NULL, 0, '2019-06-04 13:30:21', '2019-06-04 13:30:27', '这个是简介', NULL, 0, 1);
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- View structure for v_allinfo
-- ----------------------------
DROP VIEW IF EXISTS `v_allinfo`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `v_allinfo` AS select `t_category`.`type_id` AS `type_id`,`t_category`.`zh_name` AS `cat_name`,`t_category`.`en_name` AS `cat_ename`,`t_posts`.`category_id` AS `category_id`,`t_posts`.`id` AS `id`,`t_posts`.`title` AS `title`,`t_posts`.`featured_image` AS `featured_image`,`t_posts`.`describes` AS `describes`,`t_posts`.`release_time` AS `release_time` from (`t_category` join `t_posts` on((`t_category`.`id` = `t_posts`.`category_id`))) where ((`t_posts`.`is_delete` = 0) and (`t_posts`.`release_time` <= now())) union all select `t_category`.`type_id` AS `type_id`,`t_category`.`zh_name` AS `cat_name`,`t_category`.`en_name` AS `cat_ename`,`t_video`.`category_id` AS `category_id`,`t_video`.`id` AS `id`,`t_video`.`title` AS `title`,`t_video`.`featured_image` AS `featured_image`,`t_video`.`describes` AS `describes`,`t_video`.`release_time` AS `release_time` from (`t_category` join `t_video` on((`t_category`.`id` = `t_video`.`category_id`))) where ((`t_video`.`is_delete` = 0) and (`t_video`.`release_time` <= now())) union all select `t_category`.`type_id` AS `type_id`,`t_category`.`zh_name` AS `cat_name`,`t_category`.`en_name` AS `cat_ename`,`t_page`.`category_id` AS `category_id`,`t_page`.`id` AS `id`,`t_page`.`title` AS `title`,`t_page`.`featured_image` AS `featured_image`,`t_page`.`describes` AS `describes`,`t_page`.`release_time` AS `release_time` from (`t_category` join `t_page` on((`t_category`.`id` = `t_page`.`category_id`))) where ((`t_page`.`is_delete` = 0) and (`t_page`.`release_time` <= now())) union all select `t_category`.`type_id` AS `type_id`,`t_category`.`zh_name` AS `cat_name`,`t_category`.`en_name` AS `cat_ename`,`t_photo`.`category_id` AS `category_id`,`t_photo`.`id` AS `id`,`t_photo`.`title` AS `title`,`t_photo`.`featured_image` AS `featured_image`,`t_photo`.`describes` AS `describes`,`t_photo`.`release_time` AS `release_time` from (`t_category` join `t_photo` on((`t_category`.`id` = `t_photo`.`category_id`))) where ((`t_photo`.`is_delete` = 0) and (`t_photo`.`release_time` <= now()));

SET FOREIGN_KEY_CHECKS = 1;

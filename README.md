# RenFei.Net
RenFei.Net Open Source Code

[![Build Status](https://travis-ci.org/NeilRen/RenFei.Net.svg?branch=master)](https://travis-ci.org/NeilRen/RenFei.Net)
[![Coverage Status](https://coveralls.io/repos/github/NeilRen/RenFei.Net/badge.svg?branch=master)](https://coveralls.io/github/NeilRen/RenFei.Net?branch=master)
[![codebeat badge](https://codebeat.co/badges/aa156256-094d-4d10-8df3-34682eac16b0)](https://codebeat.co/projects/github-com-neilren-renfei-net-master)
<p align="center">
<img src="https://github.com/NeilRen/RenFei.Net/raw/master/doc/img/social_preview.jpg">
</p>
 
---
 
## Introduction
Tips:All the configurations in this project are for the Linux environment, and the Windows environment may need some modifications.  
提示：本项目中所有配置均针对Linux环境，Windows环境可能需要一些修改。
 
#### Directory Structure
The following is the catalog structure of the project, which contains a front-end and back-end separation project of VUE in:. / web/src/main/console  
下面是项目的目录结构，需要注意的是：其中包含了一个VUE的前后端分离项目在：./web/src/main/console
```
.
├── Dockerfile（用于描述在Docker中依赖Jdk8和Maven环境）
├── README.md
├── common（全局公共的模块）
├── core（主要逻辑模块）
│   ├── pom.xml
│   └── src
│       └── main
│           └── java
│               └── net
│                   └── renfei
│                       └── core
│                           ├── baseclass（基类）
│                           ├── config（配置类）
│                           ├── entity（实体类）
│                           └── service（服务层）
├── dao（数据访问层）
│   ├── pom.xml
│   └── src
│       └── main
│           ├── java
│           │   └── net
│           │       └── renfei
│           │           └── dao
│           │               ├── entity（数据库实体DO）
│           │               └── persistences（Mybatis的Interface）
│           └── resources
│               ├── generator.properties（Mybatis生成器配置文件）
│               ├── generatorConfig.xml（Mybatis生成器的配置文件）
│               └── mapper（Mybatis的xml）
├── db（数据库）
│   └── renfei.sql
├── doc（文档）
├── docker（Docker资源编排）
│   ├── docker-compose.yml
│   └── mysql-dockerfile
├── pom.xml
├── util（工具模块）
└── web（WEB模块）
    ├── pom.xml
    └── src
        └── main
            ├── console（VUE前后端分离的网站后台项目）
            ├── java
            │   └── net
            │       └── renfei
            │           └── web
            └── resources（Spring配置文件）
                ├── application-dev.yml（开发环境配置文件）
                ├── application-prod.yml（生产环境配置文件）
                ├── application.yml（Spring配置文件）
                ├── static（静态文件）
                └── templates（Thymeleaf模板）
```

---

## Getting Started

#### Development Environment
Before you start, you need to prepare some development environments. Below I list my development environment for your reference:  
开始前，你需要准备一些开发环境。下面我列出我的开发环境请进行参考：  

| Tool | Version | Required|
|------:|:------|:------:|
| JDK | 1.8.0_131 | Y |
| Apache Maven | 3.3.9 | Y |
| NodeJS | v10.16.0 | Using Vue ? Y : N |
| npm | 6.9.0 | Using Vue ? Y : N |
| MySQL | 5.7.17 | Y |
| Tomcat | 8.5.9 | N |
| Docker | 2.0.0.3 | N |

#### Do not build VUE projects
Tip: When Maven is packaged, the Vue project is built automatically to test the validity of the Vue project automatically using Travis CI. The Web Site Management Background (Vue) is deployed on the CDN separately after the construction.  
If you don't want to build a Vue project when Maven is packaged, remove the following from. / web/pom.xml:  
提示：在Maven打包时自动构建Vue项目是为了能够使用Travis CI自动测试Vue项目的正确性，网站管理后台（Vue）在构建以后单独部署在CDN上。  
如果你不希望在Maven打包时构建Vue项目，请在 ./web/pom.xml 中去掉以下内容：  
```xml
<!--
npm打包：注意这里配置的是Linux的npm命令，如果是Windows需要修改命令
-->
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    <executions>

        <execution>
            <id>exec-npm-install</id>
            <phase>prepare-package</phase>
            <goals>
                <goal>exec</goal>
            </goals>
            <configuration>
                <executable>npm</executable>
                <arguments>
                    <argument>install</argument>
                </arguments>
                <workingDirectory>${basedir}/src/main/console</workingDirectory>
            </configuration>
        </execution>

        <execution>
            <id>exec-npm-run-build</id>
            <phase>prepare-package</phase>
            <goals>
                <goal>exec</goal>
            </goals>
            <configuration>
                <executable>npm</executable>
                <arguments>
                    <argument>run</argument>
                    <argument>build</argument>
                </arguments>
                <workingDirectory>${basedir}/src/main/console</workingDirectory>
            </configuration>
        </execution>

    </executions>
</plugin>
```

---

## Using Docker
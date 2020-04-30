FROM centos:7
MAINTAINER RenFei i@renfei.net
RUN yum install -y java-1.8.0-openjdk* bind-utils
ADD docker/volumes/app/web2.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
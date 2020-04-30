#!/usr/bin/env bash

mvn clean package -Dmaven.test.skip=true -U

docker build -t harbor.renfei.net/renfei.net/renfei:1 .

docker push harbor.renfei.net/renfei.net/renfei:1
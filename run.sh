#!/usr/bin/env bash

export JAVA_OPTS="-Xms512m -Xmx2048m"
mvn clean install -DskipTests -Pdev
pushd todo-web
mvn -Djetty.port=8005 jetty:run

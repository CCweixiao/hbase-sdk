#!/bin/bash
mvn clean package -Dmaven.test.skip=true -Dhbase.profile=$1
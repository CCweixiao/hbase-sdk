#!/bin/bash
version=$1

if [[ -n "$version" ]];then
  echo "Start building hbase-sdk $version ......"
else
  echo "Please enter the HBase profile version you want to build."
  exit 1
fi

if [ "$version" == "1.2" ]; then
  mvn  clean package  -Dmaven.test.skip=true  -pl hbase-sdk-console -am
elif [ "$version" == "1.4" ]; then
  mvn  clean package  -Dmaven.test.skip=true  -Dhbase.profile=1.4  -pl hbase-sdk-console -am
elif [ "$version" == "2.2" ]; then
  mvn  clean package  -Dmaven.test.skip=true  -Dhbase.profile=2.2  -pl hbase-sdk-console -am
else
  echo "The temporarily supported HBase profile versions are [1.2, 1.4, 2.2]"
fi


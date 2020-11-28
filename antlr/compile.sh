#!/bin/sh
javac -cp .;./lib/antlr-4.5.1-complete.jar;%CLASSPATH% %* *.java

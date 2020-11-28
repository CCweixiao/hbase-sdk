#!/bin/sh
java -cp .;./lib/antlr-4.5.1-complete.jar;%CLASSPATH% org.antlr.v4.runtime.misc.TestRig Statements prog -gui %1

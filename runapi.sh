#!/bin/sh
MYSELF='which "$0" 2>/dev/null'
[ $? -gt 0 -a -f "$0" ] && MYSELF="./$0"
java=java
mvn exec:java
exit 1 

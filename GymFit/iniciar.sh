#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
if [ ! -f target/gymfit-1.0.0.jar ]; then mvn clean package; fi
exec java -jar target/gymfit-1.0.0.jar

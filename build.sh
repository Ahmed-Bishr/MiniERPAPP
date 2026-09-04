#!/usr/bin/env bash
# Compile the MiniERP application with javac (used when Maven is not installed).
set -e
cd "$(dirname "$0")"
mkdir -p target/classes
javac -cp "lib/*" -d target/classes $(find src/main/java -name '*.java')
echo "Build OK. Classes are in target/classes/"
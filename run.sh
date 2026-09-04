#!/usr/bin/env bash
# Compile (if needed) and run the MiniERP application.
set -e
cd "$(dirname "$0")"
mkdir -p target/classes
javac -cp "lib/*" -d target/classes $(find src/main/java -name '*.java')
java -cp "target/classes:src/main/resources:lib/*" org.example.Main
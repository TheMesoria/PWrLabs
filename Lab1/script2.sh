#!/usr/bin/env bash

cd "${1}"

who

for FILE in $(find . -type f -writable -not -empty); do
    cp "${FILE}" "${FILE}.bak"
done;

read INPUT

rm -rf *.bak
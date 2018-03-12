#!/usr/bin/env bash +x

 cd "${1}"

for FILE in $(find -name "*.cpp"); do
    cp "${FILE}" "${FILE}.all"
done;

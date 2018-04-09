#!/usr/bin/env bash +x

echo -e "Started with args: "
echo -e ${@}

if [ $# -eq 0 ];then
    echo -e "No args"
    exit
fi

if [ ! -d ${1} ]; then
    echo -e "Directory doesn't exists."
    exit
fi

if [ ! -d ${2} ]; then
    echo -e "Directory doesn't exists."
    exit
fi

for file in `ls ${1}`
do
    if [ -d "${1}/$file" ];then
        ln -s "$(pwd)/${1}/$file" "${2}/${file}_ln"
    fi
done

for file in `ls ${2}`
do
    if [ -f "${2}/$file" ];then
        ln -s "$(pwd)/${2}/$file" "${1}/${file}_ln"
    fi
done
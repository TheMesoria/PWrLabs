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

echo -e "found"
for file in ${1}/*
do
    stat --format=%n\ -\ %F\ -\ %A $file
done

echo -e "sym linking"

cd ${1}
dir=$(pwd)
cd -
for file in `ls ${1}`
do
    echo -e ${file}
    ln -s "$dir/$file" "$2/${file}_ln"
done
ls -l ${2}
# rm ${2}/*
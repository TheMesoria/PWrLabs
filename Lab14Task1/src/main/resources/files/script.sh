function generate_and_convert()
{
	for (( i=1; i<=30; i++ ))
	do
		echo -e "Generating file_${i}.bin..."
		head -c 10M </dev/urandom >file_${i}.bin
		echo -e "Reformating file_${i}.bin..."
		od -An -vtx1 file_${i}.bin > file_${i}.txt
		echo -e "Removing file_${i}.bin..."
		rm *.bin
	done
}

if [[ "${1}" == "gen" ]]; then
	generate_and_convert
fi

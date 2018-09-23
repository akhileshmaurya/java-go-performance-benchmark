#!/bin/bash
end=$1
if [ "$#" -ne 1 ]; then
    end=5
fi

sampleDir=/tmp/javaGoPer/
var=$(ls $sampleDir| sort -V | tail -n 1)

echo "File :"$var

for ((i=1; i<=$end; i++)); do
   #echo $i
   cp $sampleDir$var $sampleDir$var"_"$i
done

java -cp build/libs/java-go-performance-benchmark.jar  com.akhilesh.file.LoadAndParseFile
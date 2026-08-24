#!/bin/bash

COUNT=1000

> delovi.txt

for ((i=1; i<=COUNT; i++)); do
    echo $((RANDOM % 1000 + 1)) >> delovi.txt
done

echo "Generated $COUNT numbers in delovi.txt"


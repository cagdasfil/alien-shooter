#!/bin/bash 

rm -rf ./executables
mkdir executables

cd ./client/
chmod +x ./mvnw
./mvnw clean package 
cp ./target/client_program10.jar ../executables

cd ../server/
chmod +x ./mvnw
./mvnw clean package
cp ./target/server_program10.war ../executables

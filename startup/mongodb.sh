#!/bin/sh
docker-compose up -d

##To shutdown database without delete all containers.
#docker-compose stop
#
##To shutdown database and delete all containers.
#docker-compose down

sleep 1 # Waits 1 second.
#mongoku start
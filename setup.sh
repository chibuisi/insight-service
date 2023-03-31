#!/usr/bin/env bash

#start container if not running
if [ "$( docker container inspect -f '{{.State.Status}}' minor-insights-pubsub )" != "running" ]
then
  echo "Waiting for containers..." &
  wt -w 0 nt docker-compose up &
else echo "Container was initially running"
fi
#set permision to execute pubsub python file
#chmod +x bin/pubsub.py

#verify container is up
while :
    do
        if [ "$( docker container inspect -f '{{.State.Status}}' minor-insights-pubsub )" == "running" ]
        then
        #set the emulator host
        export PUBSUB_EMULATOR_HOST=[::1]:8681
        #create the topics
        python bin/pubsub.py is-minor-insights-dev create-topic email &
        python bin/pubsub.py is-minor-insights-dev create-topic schedule &
        python bin/pubsub.py is-minor-insights-dev create-subscription email emailsubscription &
        python bin/pubsub.py is-minor-insights-dev create-subscription schedule schedulesubscription
        break
        fi
    sleep 0.1
done

while :
    do
        if [ "$( docker container inspect -f '{{.State.Status}}' minor-insights-mysql )" == "running" ]
          then
            #Uncomment next line if python has been recently installed.
            #python -m pip install -r bin/requirements.

            #uncomment next line to create database user
            if [ "$(bin/mysql.sh -e "SELECT User FROM mysql.user where User='john'")" == "" ]
              then
                echo "Creating database user..."
                bin/mysql.sh < bin/add_john_user.sql &
            fi

            #uncomment next line to intialize database
            echo "Initializing database if not exist..."
            bin/mysql.sh < bin/init_db.sql &
        break
        fi
    sleep 0.1
done

read -p "Done. Press any key to continue" x
#$SHELL
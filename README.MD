Starting the containers

1 . Start MySQL (make sure docker is setup and running)
   docker-compose up 
    ##note the db server is exposed on port 3310

2. Add default user (change to the bin directory - cd bin)
   bash mysql.sh < add_john_user.sql

3. Optional create database if starting from scratch (change to the bin directory - cd bin)
   bash mysql.sh < init_db.sql
   
Updated
1. Run setup.sh (double click or enter key)
2. run start-server.sh

setup.sh will
1. start mysql and pubsub emulator
2. create topics and subscription
start-server.sh will start the spring-boot app
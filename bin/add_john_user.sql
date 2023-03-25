CREATE USER if not exists 'john'@'%' IDENTIFIED BY 'john1950';
GRANT ALL PRIVILEGES ON *.* TO 'john'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;
# grant all on *.* to 'john'@'%' identified by 'john1950';
# grant super on *.* to 'john'@'%';
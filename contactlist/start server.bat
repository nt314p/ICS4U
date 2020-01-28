@echo off
cd "C:\Program Files\MongoDB\Server\4.2\bin\" 
start "MongoDB" mongod.exe --dbpath "C:\Users\ntong\Dev\GitHub\ICS4U\contactlist\data\db"
timeout /t 3
cd C:\Users\ntong\Dev\GitHub\ICS4U\contactlist
start npm run serve
timeout /t 6
cd api
start "NodemonServer" nodemon server
timeout /t 3
start http://localhost:8080
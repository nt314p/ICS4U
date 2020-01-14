@echo off
echo Stopping Vue Client...
taskkill /FI "WINDOWTITLE eq npm" /F /T
echo:
echo Stopping Nodemon Server...
taskkill /FI "WINDOWTITLE eq NodemonServer - nodemon   server" /F /T
echo:
echo Stopping Mongo Database...
taskkill /FI "WINDOWTITLE eq MongoDB" /F /T
echo:
echo Success!
pause

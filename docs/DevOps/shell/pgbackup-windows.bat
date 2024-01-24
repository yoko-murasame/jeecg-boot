echo Do backing...please waiting....
@echo off

set KEEP_NUMBER=7
set BACKUP_DIR=.
set DB_NAME=postgres
set HOSTNAME=localhost
set PORT=54321
set USERNAME=postgres
set PASSWORD=123456

set PG_DUMP="C:\Program Files\PostgreSQL\13\bin\pg_dump.exe"

set /A KEEP_INDEX=%KEEP_NUMBER%-1
for /f "skip=%KEEP_INDEX% delims=" %%F in ('dir /b *.backup /o-d') do del "%BACKUP_DIR%\%%F"

set CUR_DATE=%date:~0,4%-%date:~5,2%-%date:~8,2%
set CUR_TIME=%time:~0,2%%time:~3,2%%time:~6,2%
set BACKUP_FILE=%BACKUP_DIR%\%DB_NAME%_%CUR_DATE%_%CUR_TIME%.backup

SET PGPASSWORD=%PASSWORD%

%PG_DUMP% -h %HOSTNAME% -p %PORT% -U %USERNAME% -Z 9 -Fc -f %BACKUP_FILE% %DB_NAME%

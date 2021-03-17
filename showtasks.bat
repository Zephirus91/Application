call runcrud.bat
if "%ERRORLEVEL%" == "0" goto start
echo.
echo RUNCRUD.BAT has errors - breaking work
goto fail

:fail
echo.
echo There were errors
goto end

:start
start "" http://localhost:8080/crud/v1/task/getTasks
echo.
echo Opening browser

:end
@echo off
title checkJAR
SET FIND=C:\gnuwin32\bin\find.exe
ECHO Generating list of files in JAR
IF NOT ERRORLEVEL 1 jar tf jmagic.jar | sort > filesInJAR.txt
ECHO Generating list of source files
IF NOT ERRORLEVEL 1 %FIND% bin/* -type d -printf "%%p/\n" -o -print | sed -e "s_^bin/__" | sort > files.txt
ECHO ^< are missing from workspace, ^> are missing from JAR
IF NOT ERRORLEVEL 1 diff filesInJAR.txt files.txt
DEL filesInJAR.txt files.txt
PAUSE

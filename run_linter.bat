@echo off

cd /d "%~dp0"

REM *** 1. Configurações de Caminho ***
SET PMD_EXECUTABLE=PMD\pmd-dist-7.17.0-bin\pmd-bin-7.17.0\bin\pmd.bat
SET SOURCE_DIR=src

REM *** 2. Configurações de Regra e Saída ***
SET RULESET=rulesets/java/quickstart.xml
SET REPORT_FILE=pmd-report.html

REM *** 3. Executa o comando PMD ***
echo Rodando PMD Linter...
%PMD_EXECUTABLE% check -d %SOURCE_DIR% -R %RULESET% -f html -r %REPORT_FILE%

echo.
echo Linter concluido! Relatorio em: %REPORT_FILE%
echo.

echo Tentando abrir o relatorio no seu navegador...
start %REPORT_FILE%

echo.
pause
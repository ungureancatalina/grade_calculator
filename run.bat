@echo off
setlocal
set PATH_TO_FX="C:\Users\ungur\OneDrive\Documente\Downloads\info\javafx-sdk-21.0.5\lib"
set MAIN_JAR="out\artifacts\calculate_grades_main_jar\calculate_grades.main.jar"
set JDBC_DRIVER="C:\Users\ungur\OneDrive\Documente\Downloads\info\postgresql-42.7.4.jar"

echo =========================
echo Starting JavaFX Application...
echo =========================
echo.
echo Using JavaFX Path: %PATH_TO_FX%
echo Using JAR: %MAIN_JAR%
echo Using JDBC Driver: %JDBC_DRIVER%
echo.

java --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml -cp %MAIN_JAR%;%JDBC_DRIVER% com.example.grades.Main

echo.
echo Application closed. Press any key to exit.
pause

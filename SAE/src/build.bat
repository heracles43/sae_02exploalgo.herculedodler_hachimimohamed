@echo off
echo Compilation...
cd src
javac Arc.java Arcs.java Graphe.java GrapheListe.java Valeurs.java BellmanFord.java Dijkstra.java Principale.java TestsSAE.java
if %errorlevel% neq 0 (
    echo ERREUR de compilation
    pause
    exit /b 1
)
echo Compilation OK

echo Creation du JAR...
jar cfe ../Projet.jar Principale *.class
echo JAR cree : Projet.jar

echo.
echo Lancer les tests avec :
echo   java -cp src TestsSAE
echo.
echo Lancer le programme avec :
echo   java -jar Projet.jar stan_nodes.txt stan_edges.txt HLRTT0 NYCOM0
pause

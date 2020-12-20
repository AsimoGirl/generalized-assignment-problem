# Problema-de-la-asignacion
Segundo proyecto de heurísticas y combinación

Se necesita tener gradle en versión 6 o mayor
### Reporte

El reporte se encuentra [aquí](/reporte/reporte.pdf)

### Generar documentación
Para generar documentación usar  

```bash
$ ./gradlew dokkaHtml
```
y dentro de build/dokka aparecerá la documentación

### Ejecución
Se le tendrá que pasar al programa el inicio de un rango de semillas y el final de ellas. 
El programa también funciona con una sola semilla.
En caso que se quiera probar un rango de semillas, aparecerá un txt llamado resultados.txt dentro de una carpeta resultados, el cual tendrá la evaluacion de todas las semillas y dará la mejor solución entre ellas.

El programa tiene ya incrustada la base de datos a usar, por lo que si se desea usar una base de datos diferente, agregarla a la carpeta resources y cambiar el nombre de la DB_URL dentro del archivo [DAO](src/main/kotlin/mx/unam/ciencias/heuristicas/DAO.kt)

El programa se ejecuta de la siguiente manera dentro del directorio actual:

```bash
$ ./gradlew run -PseedS=Int -PseedF=Int
```

Ejemplo de ejecución si se quieren probar un rango de semillas:
```bash
$ ./gradlew run -PseedS=20 -PseedF=25
```
Ejemplo de ejecución si solo se quiere una semilla:
```bash
$ ./gradlew run -PseedS=20 -PseedF=20
```




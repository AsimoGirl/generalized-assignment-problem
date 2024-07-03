# Generalized Assignment Problem
The comments are in Spanish as they were used for educational reasons.

You need to have gradle version 6 or higher

### Generate documentation
To generate documentation use

```bash
$ ./gradlew dokkaHtml
```
and within build/dokka the documentation will appear

### Execution
The beginning of a range of seeds and the end of them will have to be passed to the program.
The program also works with a single seed.
If you want to test a range of seeds, a txt called results.txt will appear inside a results folder, which will have the evaluation of all the seeds and will give the best solution among them.

The program already has the database to use embedded, so if you want to use a different database, add it to the resources folder and change the name of the DB_URL within the [DAO] file (src/main/kotlin/ mx/unam/ciencias/heuristicas/DAO.kt)

The program runs as follows within the current directory:

```bash
$ ./gradlew run -PseedS=Int -PseedF=Int
```

Example of execution if you want to test a range of seeds:
```bash
$ ./gradlew run -PseedS=20 -PseedF=25
```
Example of execution if you only want one seed:
```bash
$ ./gradlew run -PseedS=20 -PseedF=20
```

@file:JvmName("Main")
package mx.unam.ciencias.heuristicas

import mx.unam.ciencias.heuristicas.gap.Grafica
import mx.unam.ciencias.heuristicas.gap.Solucion
import mx.unam.ciencias.heuristicas.tabu.Heuristica
import java.io.File
import kotlin.random.Random


/**
 * Función main del proyecto
 * @param args Argumentos obtenidos por la terminal
 */
fun main(args: Array<String>) {
    val seedS = (args[0]).toInt()
    val seedF = (args[1]).toInt()
    val tareas = DAO().getTareas()
    val trabajadores = DAO().getTrabajadores()
    val graf1 = Grafica(trabajadores, tareas)
    var string = "Soluciones de las distintas semillas en el rango\n"
    var mejorCosto = Double.MAX_VALUE
    var mejorSemilla = 0
    var mejorAsignacion = ""
    for (i in seedS until seedF + 1)  {
        println("Semilla: $i")
        val asignacionActual = graf1.generaAsignacionInicial()
        val solucionInicial = Solucion(graf1, asignacionActual, Random(i))
        val gap = Heuristica(graf1, solucionInicial)
        //Heuristica
        gap.tabu()
        if (gap.evaluacion() <= mejorCosto){
            mejorCosto = gap.evaluacion()
            mejorSemilla = i
            mejorAsignacion = gap.asignacionString()
        }
        println("Asignacion:\n" + gap.asignacionString())
        println("Costo: ${gap.evaluacion()}")
        println("¿Es Factible?: ${gap.esFactible()}")
        println("---------------------------------------------\n")
        string += "Semilla: $i, Costo: ${gap.evaluacion()}\n"
    }
    string += "Mejor Semilla: $mejorSemilla, Mejor Costo: $mejorCosto , Mejor Asignación: $mejorAsignacion"
    if(seedS != seedF) {
        File("resultado/resultado-actual.txt").writeText(string)
        println("Mejor Asignación: $mejorAsignacion")
        println("Mejor Costo: $mejorCosto")
    }
}
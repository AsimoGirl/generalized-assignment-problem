package mx.unam.ciencias.heuristicas.tabu

import mx.unam.ciencias.heuristicas.gap.Grafica
import mx.unam.ciencias.heuristicas.gap.Solucion

/**
 * Declaramos nuestra clase Heuristica que realizará el recocido simulado con aceptación por umbrales
 *
 * @property g La grafica en la que estaremos trabajando
 * @property solucionInicial La primera posible solucion
 */
class Heuristica(g: Grafica, solucionInicial: Solucion) {
    /** Gráfica que estaremos utilizando*/
    private val grafica = g
    /** Gráfica que estaremos utilizando*/
    private val numtareas = g.numTareas
    /** Variable que representará la solución que se tenga en el momento*/
    private var solucionActual = solucionInicial
    /** Variable que irá guardando la mejor solución del sistema */
    private var mejorSolucionActual: Solucion = solucionInicial
    /** Variable que representará la solución que se tenga en el momento*/
    private var maximoIteraciones = 20000
    /** Variable que representará la solución que se tenga en el momento*/
    private var maximoListaTabu = 100
    /** Variable que representará la solución que se tenga en el momento*/
    private var maxVecinos = numtareas * 10

    /**
     * Función que regresa el costo de la mejor solución del sistema
     * @return El costo de la mejor solución del sistema
     */
    private fun buscaVecino(solucionesVecinas: ArrayList<Solucion>, solucionesTabu: ArrayList<Solucion>): Solucion{
        //Eliminamos todos los resultados tabú de la lista de vecinos
        solucionesVecinas.removeAll {
            it in solucionesTabu
        }
        //Ordenamos los vecinos de acuerdo a su costo
        solucionesVecinas.sortedWith(compareBy({ it.costo }, { it.costo }))
        //Devolvemos al vecino con el menor valor
        return solucionesVecinas[0]
    }

    /**
     * Función que regresa el costo de la mejor solución del sistema
     * @return El costo de la mejor solución del sistema
     */
    private fun generaVecinos(solucion: Solucion): ArrayList<Solucion>{
        val vecindad =  ArrayList<Solucion>()
        for(i in 0 until maxVecinos) {
            val vecino1 = solucion.generaVecinoSwap()
            val vecino2 = solucion.generaVecinoShift()
            vecindad.add(vecino1)
            vecindad.add(vecino2)
        }
        return vecindad
    }

    /**
     * Función que regresa el costo de la mejor solución del sistema
     * @return El costo de la mejor solución del sistema
     */
    fun tabu(){
        var iteracion = 0
        val listaTabu = ArrayList<Solucion>()
        listaTabu.add(solucionActual)
        while(iteracion != maximoIteraciones){
            val vecindad = generaVecinos(solucionActual)
            val mejorVecino = buscaVecino(vecindad, listaTabu)
            solucionActual = mejorVecino
            if(mejorVecino.costo < solucionActual.costo){
                mejorSolucionActual = mejorVecino
            }
            listaTabu.add(mejorVecino)
            while(listaTabu.size > maximoListaTabu){
                listaTabu.removeAt(0)
            }
            iteracion ++
        }
    }


    /**
     * Función que regresa el string de la ruta de la mejor solución del sistema
     * @return La lista de ids de la mejor solución del sistema
     */
    fun asignacionString(): String = mejorSolucionActual.toString()

    /**
     * Función que regresa el costo de la mejor solución del sistema
     * @return El costo de la mejor solución del sistema
     */
    fun evaluacion(): Double = mejorSolucionActual.costo

    /**
     * Función que regresa si la mejor solución del sistema es factible o no
     * @return Un booleano que nos dice si es factible o no la solución
     */
    fun esFactible(): Boolean = mejorSolucionActual.factible

}
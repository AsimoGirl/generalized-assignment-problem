package mx.unam.ciencias.heuristicas.tsp

import mx.unam.ciencias.heuristicas.DAO
import mx.unam.ciencias.heuristicas.modelo.Ciudad
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.*

/**
 * Declaramos nuestra clase Grafica que tendrá todos los métodos de la gráfica y la creará
 *
 * @property ciudades Lista de todos los objetos ciudades
 * @property idsCiudades String que representa todos los ids de las ciudades
 * @constructor Devuelve una gráfica
 */
class Grafica(val ciudades: ArrayList<Ciudad>, idsCiudades: String){
    /** Número de vértices de la gráfica*/
    private val numVertices = ciudades.size
    /** Lista de todos los objetos Conexion que son las distancias entre las ciudades*/
    private val todasDistancias = DAO(idsCiudades).getDistancia()
    /** Lista de todas las distancias como doubles*/
    private val distancias = getDistancias()
    /** Distancia máxima*/
    val distanciaMax = maxD()
    /** Valor del normalizador de la gráfica*/
    val normalizador = n()
    /** La matriz de adyacencias que representa la gráfica*/
    val matrizAdj = ws()

    /**
     * Función que obtiene la distancia de dos ciudades en la lista de objetos conexion
     * @param idU El id de la primera ciudad
     * @param idV El id de la segunda ciudad
     * @return La distancia entre estas dos ciudades si es que existe
     */
    fun getValorLista(idU: Int, idV:Int): Double{
        for (i in todasDistancias){
            if ((i.idCity1 == idU && i.idCity2 == idV) || (i.idCity2 == idU && i.idCity1 == idV)){
                return i.distance
            }
        }
        return -1.0
    }

    /**
     * Función que obtiene el peso de dos ciudades dentro de la gráfica
     * @param idI El id de la primera ciudad
     * @param idJ El id de la segunda ciudad
     * @return El valor de la matriz de adyacencia en los indices que representan esas ciudades
     */
    fun getPeso(idI: Int, idJ: Int): Double{
        val i = ciudades.indexOfFirst{ it.id == idI}
        val j = ciudades.indexOfFirst{ it.id == idJ}
        return matrizAdj[i][j]
    }

    /**
     * Función que obtiene ina lista con los valores de todas las posibles distancis entre ciudades
     * @return Una lista de doubles con los valores de todas las distancias
     */
    fun getDistancias(): ArrayList<Double>{
        val todasDistancias = arrayListOf<Double>()
        var u :Int
        var v: Int
        var i = 0
        var j = 1
        var distActual: Double
        while (i < numVertices - 1) {
            u = ciudades[i].id
            j = i + 1
            while(j < numVertices){
                v = ciudades[j].id
                distActual = getValorLista(u, v)
                if (distActual > -1){
                    todasDistancias.add(distActual)
                }
                j++
            }
            i++
        }
        return todasDistancias
    }

    /**
     * Función que obtiene la distancia máxima
     * @return El valor de la distancia más grande entre ciudades
     */
    fun maxD(): Double{
        return Collections.max(distancias)
    }

    /**
     * Función que obtiene el normalizador, al sumar las n-1 distancias más grandes
     * @return El valor del normalizador
     */
    fun n(): Double{
        var normalizador = 0.0
        var a = 0
        Collections.sort(distancias, Collections.reverseOrder())
        for (i in distancias) {
            if (a >= numVertices-1)
                break
            normalizador += i
            a++
        }
        return normalizador
    }

    /**
     * Función que convierte grados a radianes
     * @param g El valor de los grados
     * @return El valor en radianes
     */
    fun rad(g: Double): Double = (g * Math.PI) / 180

    /**
     * Función que obtiene la distancia natural entre dos ciudades
     * @param u EL objeto de la primera ciudad
     * @param v EL objeto de la segunda ciudad
     * @return El valor de la distancia natural entre ellas
     */
    fun d(u: Ciudad, v: Ciudad): Double {
        val latU = rad(u.latitude)
        val lonU = rad(u.longitude)
        val latV = rad(v.latitude)
        val lonV = rad(v.longitude)
        val a = sin((latV - latU) / 2).pow(2) +
                cos(latU) * cos(latV) * sin((lonV - lonU) / 2).pow(2)
        val r = 6373000
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return r * c
    }

    /**
     * Función que llena la matriz de adyacencia con la función de peso aumentada
     * @return La matriz de adyacencia ya llena
     */
    fun ws():Array<DoubleArray>{
        val matrizAdj = Array(numVertices) { DoubleArray(numVertices) }
        var natural: Double
        var distancia: Double
        for (i in 0 until numVertices) {
            val u: Ciudad = ciudades[i]
            for (j in 1 until numVertices) {
                val v: Ciudad = ciudades[j]
                distancia = getValorLista(u.id, v.id)
                //Función de peso aumentada
                if (distancia > -1) {
                    matrizAdj[i][j] = distancia
                    matrizAdj[j][i] = distancia
                } else {
                    natural = d(u, v)
                    matrizAdj[i][j] = distanciaMax * natural
                    matrizAdj[j][i] = distanciaMax * natural
                }
            }
        }
        return matrizAdj
    }

    /**
     * Función que obtiene la función de costo de la instancia de la gráfica actual
     * @return La función de costo
     */
    fun f() :Double{
        var suma = 0.0
        var indexU: Int
        var indexV: Int
        for (i in 0 until numVertices - 1) {
            indexU = ciudades.indexOf(ciudades[i])
            indexV = ciudades.indexOf(ciudades[i+1])
            suma += matrizAdj[indexU][indexV]
        }
        return suma / normalizador
    }

    /**
     * Función que optimiza la obtención del costo de la instancia una vez que se hace intercambio de índices
     * Se toman las 4 aristas posibles de la gráfica que se ven afectadas con este intercambio y
     * Así obtenemos el nuevo costo restando de los pesos de estas aristas eliminadas y la suma de agregar los nuevas
     * @param uIndex Primer indice que se intercambió
     * @param vIndex Segundo indice que se intercambió
     * @param costo El costo de la ruta antes del intercambio
     * @param ruta La ruta nueva después del intercambio de vértices
     * @return El costo de esta nueva ruta
     */
    fun getCostoOptimizado(uIndex:Int, vIndex:Int, costo: Double, ruta: ArrayList<Int>) :Double{
        val numCiudades = ruta.size
        // Arista afectada e0 = (i-1, i)
        var e0Antiguo = 0.0
        var e0Nuevo = 0.0
        // Arista afectada e1 = (i, i+1)
        var e1Antiguo = 0.0
        var e1Nuevo = 0.0
        // Arista afectada e2 = (j-1, j)
        var e2Antiguo = 0.0
        var e2Nuevo = 0.0
        // Arista afectada e3 = (j, j+1)
        var e3Antiguo = 0.0
        var e3Nuevo = 0.0
        if (uIndex > 0 && vIndex != uIndex-1) {
            e0Antiguo = getPeso(ruta[uIndex-1], ruta[uIndex])
            e0Nuevo   = getPeso(ruta[uIndex-1], ruta[vIndex])
        }
        if (uIndex < numCiudades - 1 && vIndex != uIndex + 1) {
            e1Antiguo = getPeso(ruta[uIndex], ruta[uIndex+1])
            e1Nuevo   = getPeso(ruta[vIndex], ruta[uIndex+1])
        }
        if (vIndex > 0 && uIndex != vIndex-1) {
            e2Antiguo = getPeso(ruta[vIndex-1], ruta[vIndex])
            e2Nuevo   = getPeso(ruta[vIndex-1], ruta[uIndex])
        }
        if (vIndex < numCiudades - 1 && uIndex != vIndex + 1) {
            e3Antiguo = getPeso(ruta[vIndex], ruta[vIndex+1])
            e3Nuevo   = getPeso(ruta[uIndex], ruta[vIndex+1])
        }
        val sumaNuevos = e0Nuevo + e1Nuevo + e2Nuevo + e3Nuevo
        val sumaPrevios = e0Antiguo + e1Antiguo + e2Antiguo + e3Antiguo
        return costo + ((sumaNuevos - sumaPrevios)/normalizador)
    }
}
package mx.unam.ciencias.heuristicas.tsp
import kotlin.random.Random
/**
 * This class has no useful logic; it's just a documentation example.
 *
 * @property ruta the name of this group.
 * @property random
 * @property costo
 * @constructor Crea una solución
 */
class Solucion(var ruta: ArrayList<Int>, private val random: Random, var costo: Double) {
    /** Número de ciudades que se usan en la solución*/
    private val numCiudades = ruta.size
    /**
     * Función que obtiene el vecino de una solución
     * @return Regresa el par de índices del vecino y los ids de la ruta nueva
     */
    fun generaVecino(): Pair<Pair<Int, Int>,ArrayList<Int>> {
        var uIndex = 0
        var vIndex = 0
        while (uIndex == vIndex) {
            uIndex = (0 until numCiudades).random(random)
            vIndex = (0 until numCiudades).random(random)
        }
        val nuevaRuta = ArrayList(ruta)
        val auxIndex = nuevaRuta[uIndex]
        nuevaRuta[uIndex] = nuevaRuta[vIndex]
        nuevaRuta[vIndex] = auxIndex
        val indices = Pair(uIndex, vIndex)
        return Pair(indices, nuevaRuta)
    }

    /**
     * Función que cambia la forma en que se muestra la ruta de la solución
     * @return Una string que representa la solución
     */
    override fun toString(): String {
        var solucion = ""
        for(i in 0 until numCiudades){
            solucion += if (i == numCiudades -1){
                (ruta[i].toString())
            } else{
                (ruta[i].toString()) + ","
            }
        }
        return solucion
    }
}
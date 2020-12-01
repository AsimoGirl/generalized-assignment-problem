package mx.unam.ciencias.heuristicas

import mx.unam.ciencias.heuristicas.modelo.Ciudad
import mx.unam.ciencias.heuristicas.modelo.Conexion
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement


/**
 * Declaramos nuestra clase DAO que obtiene la información de la base de datos
 *
 * @property ids Lista de ids de las ciudades a usar
 * @constructor Crea un DAO
 */
class DAO(private val ids: String) {
    /** URL donde se encuentra la base de datos a usar*/
    private val DB_URL = "jdbc:sqlite:resources/ciudades.db"

    /**
     * Función que obtiene la información de todas las ciudades dadas los ids
     * @return Una lista de todos los objetos ciudades
     */
    fun getCiudades(): ArrayList<Ciudad> {
        val connection: Connection?
        val statement: Statement?
        val ciudades = ArrayList<Ciudad>()
        val query: String
        var id: Int
        var name: String
        var country: String
        var population: Int
        var latitude: Double
        var longitude: Double
        try {
            connection = DriverManager.getConnection(DB_URL)
            connection.autoCommit = false
            statement = connection.createStatement()
            query = "SELECT * FROM cities WHERE ID IN ($ids)"
            val resultSet = statement.executeQuery(query)
            while (resultSet.next()) {
                id = resultSet.getInt("id")
                name = resultSet.getString("name")
                country = resultSet.getString("country")
                population = resultSet.getInt("population")
                latitude = resultSet.getDouble("latitude")
                longitude = resultSet.getDouble("longitude")
                ciudades.add(Ciudad(id, name, country, population, latitude, longitude))
            }
            connection.close()
        } catch (e: SQLException) {
            println(e.message)
        }
        return ciudades
    }

    /**
     * Función que obtiene la información de todas las conexiones entre las ciudades
     * @return Una lista de todos los objetos conexion de las ciudades
     */
    fun getDistancia(): ArrayList<Conexion> {
        val conexionesCiudad = arrayListOf<Conexion>()
        val connection: Connection?
        val statement: Statement?
        val query: String
        try {
            connection = DriverManager.getConnection(DB_URL)
            connection.autoCommit = false
            statement = connection.createStatement()
            query =
                "SELECT * FROM connections WHERE id_city_1 OR id_city_2  IN ($ids)"
            val resultSet = statement.executeQuery(query)
            while (resultSet.next()) {
                val idCity1 = resultSet.getInt("id_city_1")
                val idCity2 = resultSet.getInt("id_city_2")
                val distance = resultSet.getDouble("distance")
                conexionesCiudad.add(Conexion(idCity1, idCity2, distance))
            }
            connection.close()
        } catch (e: SQLException) {
            println(e.message)
        }
        return conexionesCiudad
    }
}


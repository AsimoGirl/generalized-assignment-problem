package mx.unam.ciencias.heuristicas

import mx.unam.ciencias.heuristicas.modelo.Trabajador
import mx.unam.ciencias.heuristicas.modelo.Tarea
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement


/**
 * Declaramos nuestra clase DAO que obtiene la información de la base de datos
 *
 * @constructor Crea un DAO
 */
class DAO() {
    /** URL donde se encuentra la base de datos a usar*/
    private val DB_URL = "jdbc:sqlite:resources/gap4.db"

    /**
     * Función que obtiene la información de todas los trabajadores
     * @return Una lista de todos los objetos trabajador con la información de la base de datos
     */
    fun getTrabajadores(): ArrayList<Trabajador> {
        val connection: Connection?
        val statement: Statement?
        val trabajadores = ArrayList<Trabajador>()
        val query: String
        var id: Int
        var nombre: String
        var capacidad: Double
        try {
            connection = DriverManager.getConnection(DB_URL)
            connection.autoCommit = false
            statement = connection.createStatement()
            query = "SELECT * FROM trabajadores"
            val resultSet = statement.executeQuery(query)
            while (resultSet.next()) {
                id = resultSet.getInt("id")
                nombre = resultSet.getString("nombre")
                capacidad = resultSet.getDouble("capacidad")
                trabajadores.add(Trabajador(id, nombre, capacidad))
            }
            connection.close()
        } catch (e: SQLException) {
            println(e.message)
        }
        return trabajadores
    }

    /**
     * Función que obtiene la información de todas las tareas
     * @return Una lista de todos los objetos tarea con la información de la base de datos
     */
    fun getTareas(): ArrayList<Tarea> {
        val tareas = arrayListOf<Tarea>()
        val connection: Connection?
        val statement: Statement?
        val query: String
        try {
            connection = DriverManager.getConnection(DB_URL)
            connection.autoCommit = false
            statement = connection.createStatement()
            query =
                "SELECT * FROM tareas"
            val resultSet = statement.executeQuery(query)
            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val nombre = resultSet.getString("nombre")
                tareas.add(Tarea(id, nombre))
            }
            connection.close()
        } catch (e: SQLException) {
            println(e.message)
        }
        return tareas
    }

    /**
     * Función que obtiene la información de todos los costos entre trabajadores y tareas
     * @return Una lista de la tripleta que tiene los indices del trbajador y tarea, además de su costo
     */
    fun getCostos(): ArrayList<Pair <Pair<Int, Int> , Double>>{
        val costos = arrayListOf<Pair <Pair<Int, Int> , Double>>()
        val connection: Connection?
        val statement: Statement?
        val query: String
        try {
            connection = DriverManager.getConnection(DB_URL)
            connection.autoCommit = false
            statement = connection.createStatement()
            query =
                "SELECT * FROM costos"
            val resultSet = statement.executeQuery(query)
            while (resultSet.next()) {
                val idTarea = resultSet.getInt("id_tarea")
                val idTrabajador = resultSet.getInt("id_trabajador")
                val ids = Pair(idTarea, idTrabajador)
                val costo = resultSet.getDouble("costo")
                val valores = Pair(ids, costo)
                costos.add(valores)
            }
            connection.close()
        } catch (e: SQLException) {
            println(e.message)
        }
        return costos
    }

    /**
     * Función que obtiene la información de todas las capacidades necesarias entre trabajadores y tareas
     * @return Una lista de la tripleta que tiene los indices del trbajador y tarea, además de su capacidad necesaria
     */
    fun getCapacidades(): ArrayList<Pair <Pair<Int, Int> , Double>>{
        val capacidades = arrayListOf<Pair <Pair<Int, Int> , Double>>()
        val connection: Connection?
        val statement: Statement?
        val query: String
        try {
            connection = DriverManager.getConnection(DB_URL)
            connection.autoCommit = false
            statement = connection.createStatement()
            query =
                "SELECT * FROM capacidades"
            val resultSet = statement.executeQuery(query)
            while (resultSet.next()) {
                val idTarea = resultSet.getInt("id_tarea")
                val idTrabajador = resultSet.getInt("id_trabajador")
                val ids = Pair(idTarea, idTrabajador)
                val capacidad = resultSet.getDouble("capacidad")
                val valores = Pair(ids, capacidad)
                capacidades.add(valores)
            }
            connection.close()
        } catch (e: SQLException) {
            println(e.message)
        }
        return capacidades
    }
}


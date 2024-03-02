package com.example.marsphotos.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
 class Item {
    @Entity(tableName = "Datosdelalumno")
    data class Item0(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        @ColumnInfo(name = "Matricula")
        val itemMatricula: String,
        @ColumnInfo(name = "Nombre")
        val itemNombre: String,
        @ColumnInfo(name = "Carrera")
        val itemCarrera: String,
        @ColumnInfo(name = "Promedio")
        val itemPromedio: Double,
        @ColumnInfo(name = "Semestre")
        val itemSemestre: Int

    )
    @Entity(tableName = "Calificaciones")
    data class Item1(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        @ColumnInfo(name = "Matricula")
        val itemMatricula: String,
        @ColumnInfo(name = "Nombre")
        val itemNombre: String,
        @ColumnInfo(name = "Materia")
        val itemMateria: String,
        @ColumnInfo(name = "Calificacion")
        val itemCalificacion: Int

    )
    @Entity(tableName = "Calificaciones Finales")
    data class Item2(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        @ColumnInfo(name = "Matricula")
        val itemMatricula: String,
        @ColumnInfo(name = "Nombre")
        val itemNombre: String,
        @ColumnInfo(name = "Materia")
        val itemMateria: String,
        @ColumnInfo(name = "Calificacion")
        val itemCalificacion: Int,
        @ColumnInfo(name = "Promedio")
        val itemPromedio: Double

    )
    @Entity(tableName = "Kardex")
    data class Item3(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        @ColumnInfo(name = "Matricula")
        val itemMatricula: String,
        @ColumnInfo(name = "Nombre")
        val itemNombre: String,
        @ColumnInfo(name = "Materia")
        val itemMateria: String,
        @ColumnInfo(name = "Calificacion")
        val itemCalificacion: Int,
        @ColumnInfo(name = "Semestre")
        val itemSemestre: Int,
        @ColumnInfo(name = "Estatus")
        val itemEstatus: String

    )


}
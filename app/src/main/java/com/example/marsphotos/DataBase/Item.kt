package com.example.marsphotos.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "Datosdelalumno")
data class DatosAlumno(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    val id:Int=0,
    @ColumnInfo(name = "Matricula")
    val itemMatricula: String,
    @ColumnInfo(name = "Nombre")
    val itemNombre: String,
    @ColumnInfo(name = "Carrera")
    val itemCarrera: String,
    @ColumnInfo(name = "Semestre")
    val itemSemestre: Int,
    @ColumnInfo(name = "FechaReins")
    val fechaReins: String,
    @ColumnInfo(name = "ModEducativo")
    val modEducativo: Int,
    @ColumnInfo(name = "Adeudo")
    val adeudo: Boolean,
    @ColumnInfo(name = "UrlFoto")
    val urlFoto: String,
    @ColumnInfo(name = "AdeudoDescripcion")
    val adeudoDescripcion: String,
    @ColumnInfo(name = "Inscrito")
    val inscrito: Boolean,
    @ColumnInfo(name = "Estatus")
    val estatus: String,
    @ColumnInfo(name = "SemActual")
    val semActual: Int,
    @ColumnInfo(name = "CdtosAcumulados")
    val cdtosAcumulados: Int,
    @ColumnInfo(name = "CdtosActuales")
    val cdtosActuales: Int,
    @ColumnInfo(name = "Especialidad")
    val especialidad: String,
    @ColumnInfo(name = "Lineamiento")
    val lineamiento: Int
)

@Entity(tableName = "Acceso")
data class Acceso(
    @PrimaryKey()
    @ColumnInfo(name="id")
    val id:Int,
    @ColumnInfo(name = "Matricula")
    val itemMatricula: String,
    @ColumnInfo(name = "acceso")
    val itemacceso: String

)
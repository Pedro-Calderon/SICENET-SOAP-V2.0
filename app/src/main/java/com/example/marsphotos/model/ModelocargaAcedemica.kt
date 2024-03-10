package com.example.marsphotos.model

import kotlinx.serialization.Serializable
import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "Envelope")
@Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/")
data class SoapEnvelopeCarga @JvmOverloads constructor(
    @field:Element(name = "Body")
    @field:Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/")
    var body: SoapBodyCarga = SoapBodyCarga()
)

@Root(name = "Body")
@Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/")
data class SoapBodyCarga @JvmOverloads constructor(
    @field:Element(name = "getCargaAcademicaByAlumnoResponse")
    @field:Namespace(reference = "http://tempuri.org/")
    var getCargaAcademicaByAlumnoResponse: GetCargaAcademicaByAlumnoResponse = GetCargaAcademicaByAlumnoResponse()
)

@Root(name = "getCargaAcademicaByAlumnoResponse")
@Namespace(reference = "http://tempuri.org/")
data class GetCargaAcademicaByAlumnoResponse @JvmOverloads constructor(
    @field:Element(name = "getCargaAcademicaByAlumnoResult")
    var getCargaAcademicaByAlumnoResult: String = ""
)


@Serializable
data class ModelocargaAcedemicarga(
    val Semipresencial: String,
    val Observaciones: String,
    val Docente: String,
    val clvOficial: String,
    val Sabado: String,
    val Viernes: String,
    val Jueves: String,
    val Miercoles: String,
    val Martes: String,
    val Lunes: String,
    val EstadoMateria: String,
    val CreditosMateria: Int,
    val Materia: String,
    val Grupo: String
)

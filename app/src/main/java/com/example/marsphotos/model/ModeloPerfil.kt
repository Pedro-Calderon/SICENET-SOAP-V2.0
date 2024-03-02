package com.example.marsphotos.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root
import kotlinx.serialization.Serializable

@Root(name = "Envelope")
@Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/")
data class SoapEnveloperfil @JvmOverloads constructor(
    @field:Element(name = "Body")
    @field:Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/")
    val body: SoapBody
)

@Root(name = "Body")
@Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/")
data class SoapBodyperfil @JvmOverloads constructor(
    @field:Element(name = "getAlumnoAcademicoWithLineamientoResponse")
    @field:Namespace(reference = "http://tempuri.org/")
    val getAlumnoAcademicoWithLineamientoResponse: GetAlumnoAcademicoWithLineamientoResponse
)

@Root(name = "getAlumnoAcademicoWithLineamientoResponse")
@Namespace(reference = "http://tempuri.org/")
data class GetAlumnoAcademicoWithLineamientoResponse @JvmOverloads constructor(
    @field:Element(name = "getAlumnoAcademicoWithLineamientoResult")
    val getAlumnoAcademicoWithLineamientoResult: String
)



@Serializable
data class AlumnoAcademicoResponse(
    val fechaReins: String,
    val modEducativo: Int,
    val adeudo: Boolean,
    val urlFoto: String,
    val adeudoDescripcion: String,
    val inscrito: Boolean,
    val estatus: String,
    val semActual: Int,
    val cdtosAcumulados: Int,
    val cdtosActuales: Int,
    val especialidad: String,
    val carrera: String,
    val lineamiento: Int,
    val nombre: String,
    val matricula: String
)

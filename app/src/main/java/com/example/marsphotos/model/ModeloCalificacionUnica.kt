package com.example.marsphotos.model

import kotlinx.serialization.Serializable
import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root



@Root(name = "Envelope")
@Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/")
data class SoapEnveloCalificacionUni @JvmOverloads constructor(
    @field:Element(name = "Body")
    @field:Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/")
    var body: SoapBodyCalificacion = SoapBodyCalificacion()
)


@Root(name = "Body")
@Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/")
data class SoapBodyCalificacion @JvmOverloads constructor(
    @field:Element(name = "getCalifUnidadesByAlumnoResponse")
    @field:Namespace(reference = "http://tempuri.org/")
    var getCalifUnidadesByAlumnoResponse: GetCalifUnidadesByAlumnoResponse = GetCalifUnidadesByAlumnoResponse()
)


@Root(name = "getCalifUnidadesByAlumnoResponse")
@Namespace(reference = "http://tempuri.org/")
data class GetCalifUnidadesByAlumnoResponse @JvmOverloads constructor(
    @field:Element(name = "getCalifUnidadesByAlumnoResult")
    var getCalifUnidadesByAlumnoResult: String = ""
)


@Serializable
data class Calificaciones(
    val Observaciones: String?,
    val C13: String?,
    val C12: String?,
    val C11: String?,
    val C10: String?,
    val C9: String?,
    val C8: String?,
    val C7: String?,
    val C6: String?,
    val C5: String?,
    val C4: String?,
    val C3: String?,
    val C2: String?,
    val C1: String?,
    val UnidadesActivas:String?,
    val Materia: String?,
    val Grupo: String?
)
fun Calificaciones.getCalificacion(key: String): String? {
    return when (key) {
        "C1" -> C1
        "C2" -> C2
        "C3" -> C3
        "C4" -> C4
        "C5" -> C5
        "C6" -> C6
        "C7" -> C7
        "C8" -> C8
        "C9" -> C9
        "C10" -> C10
        "C11" -> C11
        "C12" -> C12
        "C13" -> C13
        else -> null
    }
}

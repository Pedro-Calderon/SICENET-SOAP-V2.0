package com.example.marsphotos.model


import kotlinx.serialization.Serializable
import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "Envelope")
@Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/")
data class SoapEnvelopeRequest (
    @field:Element(name = "Body")
    @field:Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/")
    var body: SoapBodyResponse = SoapBodyResponse()
)


@Root(name = "Body")
@Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/")
data class SoapBodyResponse (
    @field:Element(name = "getAllCalifFinalByAlumnosResponse")
    @field:Namespace(reference = "http://tempuri.org/")
    var getAllCalifFinalByAlumnosResponse: GetAllCalifFinalByAlumnosResponse = GetAllCalifFinalByAlumnosResponse()
)

@Root(name = "getAllCalifFinalByAlumnosResponse")
@Namespace(reference = "http://tempuri.org/")
data class GetAllCalifFinalByAlumnosResponse (
    @field:Element(name = "getAllCalifFinalByAlumnosResult")
    var getAllCalifFinalByAlumnosResult: String = ""
)
@Serializable
data class CalificacionFResponse(
    val lstFinal: List<CalificacionesResponse>
)
// Model class for the response data
@Serializable
data class CalificacionesResponse(
    val calif: Int,
    val acred:String,
    val grupo:String,
    val materia: String,
    val Observaciones: String
) {


}


package com.example.marsphotos.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "Envelope")
@Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/")
data class SoapEnvelopeKardex(
    @field:Element(name = "Body")
    @field:Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/")
    var body: SoapBodyKardex = SoapBodyKardex()
)

@Root(name = "Body")
@Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/")
data class SoapBodyKardex(
    @field:Element(name = "getAllKardexConPromedioByAlumnoResponse")
    @field:Namespace(reference = "http://tempuri.org/")
    var getAllKardexConPromedioByAlumnoResponse: GetAllKardexConPromedioByAlumnoResponse = GetAllKardexConPromedioByAlumnoResponse()
)

@Root(name = "getAllKardexConPromedioByAlumnoResponse")
@Namespace(reference = "http://tempuri.org/")
data class GetAllKardexConPromedioByAlumnoResponse(
    @field:Element(name = "getAllKardexConPromedioByAlumnoResult")
    var getAllKardexConPromedioByAlumnoResult: String = ""
)


@Serializable
data class KardexResponse(
    val lstKardex: List<KardexItem>
)

@Serializable
data class KardexItem(
    val S3: String?,
    val P3: String?,
    val A3: String?,
    val ClvMat: String,
    val ClvOfiMat: String,
    val Materia: String,
    val Cdts: Int,
    val Calif: Int,
    val Acred: String,
    val S1: String,
    val P1: String,
    val A1: String,
    val S2: String?,
    val P2: String?,
    val A2: String?
)


package com.example.marsphotos.model


import kotlinx.serialization.Serializable
import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root


@Root(name = "Envelope")
@Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/")
data class SoapEnvelope @JvmOverloads constructor(
    @field:Element(name = "Body")
    @field:Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/")
    val body: SoapBody
)

@Root(name = "Body")
@Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/")
data class SoapBody @JvmOverloads constructor(
    @field:Element(name = "accesoLoginResponse")
    @field:Namespace(reference = "http://tempuri.org/")
    val accesoLoginResponse: AccesoLoginResponse
)

@Root(name = "accesoLoginResponse")
@Namespace(reference = "http://tempuri.org/")
data class AccesoLoginResponse @JvmOverloads constructor(
    @field:Element(name = "accesoLoginResult")
    val accesoLoginResult: String
)

@Serializable
data class AccesoLoginResult(
    val acceso : String,
    val estatus: String,
    val matricula: String
)
package com.openroad

import org.http4k.client.OkHttp
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CarInfoServiceExternalTest {

    val apiKey = "c72be63d-356d-4c65-8f99-2a997d3adc7c"

    @Test
    fun `will lookup car info based on reg plate and respond with 200 OK`(){
        val client = OkHttp()
        val request = Request(Method.GET, "https://uk1.ukvehicledata.co.uk/api/datapackage/VehicleData")
            .query("v", "2")
            .query("api_nullitems", "1")
            .query("auth_apikey", apiKey)
            .query("key_VRM", "RE67AZC")
        Assertions.assertEquals(OK, client(request).status)
    }

}
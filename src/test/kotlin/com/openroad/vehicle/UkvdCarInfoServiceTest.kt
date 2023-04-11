package com.openroad.vehicle

import com.openroad.getExpectedVehicleData
import com.openroad.utils.ServiceException
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.http4k.client.DualSyncAsyncHttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

class UkvdCarInfoServiceTest {

    val mockedClient = mockk<DualSyncAsyncHttpHandler>()
    val token = "someToken"
    val baseUrl = "someUrl"
    val carInfoService = UkvdCarInfoService(baseUrl, token, mockedClient)

    @Test
    fun `will get vehicle details for valid license plate`() {
        val expectedVehicleDetails = getExpectedVehicleData()
        val registration = "someRegistration"
        val readText = UkvdCarInfoServiceTest::class.java.getResource("/exampleVehicleLookup.json").readText()
        val requestSlot = slot<Request>()
        every { mockedClient.invoke(capture(requestSlot)) } returns Response(Status.OK).body(readText)
        carInfoService.getDetailsFor(registration).fold({
            fail(it)
        }, { runPlate ->
            assertEquals(expectedVehicleDetails, runPlate)
            assertEquals(
                "v=2&api_nullitems=1&auth_apikey=$token&key_VRM=$registration",
                requestSlot.captured.uri.query
            )
        })
    }

    @Test
    fun `will get vehicle details for invalid license plate`() {
        val registration = "someInvalidRegistration"
        val requestSlot = slot<Request>()
        every { mockedClient.invoke(capture(requestSlot)) } returns Response(Status.NOT_FOUND)
        carInfoService.getDetailsFor(registration).fold(
            {
                if(it is ServiceException){
                    assertEquals(Status.NOT_FOUND, it.statusCode)
                    assertEquals(
                        "v=2&api_nullitems=1&auth_apikey=$token&key_VRM=$registration",
                        requestSlot.captured.uri.query
                    )
                } else {
                    fail("Was expecting a ServiceException")
                }
            }, {fail("Shouldn't get here")})
    }

}
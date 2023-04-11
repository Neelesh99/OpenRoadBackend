package com.openroad.vehicle

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.openroad.utils.ServiceException
import com.openroad.vehicle.types.VehicleData
import io.klogging.java.LoggerFactory
import io.klogging.logger
import org.http4k.client.DualSyncAsyncHttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.http4k.format.Jackson
import org.http4k.routing.static

class UkvdCarInfoService(val baseUrl: String, val token: String, val client: DualSyncAsyncHttpHandler) : CarInfoService {

        val logger = LoggerFactory.getLogger(UkvdCarInfoService::class.java)
        override fun getDetailsFor(regPlate: String): Either<Exception, VehicleData> {
        try {
            val result = client(
                Request(Method.POST, baseUrl)
                    .query("v", "2")
                    .query("api_nullitems", "1")
                    .query("auth_apikey", token)
                    .query("key_VRM", regPlate)
            )
            if(result.status != Status.OK){
                logger.error("Unexpected result from UKVD with status: ${result.status}")
                logger.error(result)
                return ServiceException("Error contacting car info service", Exception(), result.status).left()
            }
            return VehicleData.fromExternalJson(Jackson.parse(result.bodyString())).right()
        } catch(e: Exception){
            return e.left()
        }
    }

}

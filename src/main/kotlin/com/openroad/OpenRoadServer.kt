package com.openroad

import com.openroad.formats.JacksonMessage
import com.openroad.formats.jacksonMessageLens
import com.openroad.routes.ExampleContractRoute
import com.openroad.routes.SimpleRoute
import org.http4k.contract.bind
import org.http4k.contract.contract
import org.http4k.contract.openapi.ApiInfo
import org.http4k.contract.openapi.v3.OpenApi3
import org.http4k.contract.security.ApiKeySecurity
import org.http4k.core.*
import org.http4k.filter.DebuggingFilters
import org.http4k.filter.OpenTelemetryMetrics
import org.http4k.filter.OpenTelemetryTracing
import org.http4k.filter.ServerFilters
import org.http4k.lens.Query
import org.http4k.lens.int
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Http4kServer
import org.http4k.server.Undertow
import org.http4k.server.asServer

fun OpenRoadApp() : HttpHandler {
    return routes(
        "/ping" bind Method.GET to {
            Response(Status.OK).body("pong")
        },

        "/contract/api/v1" bind contract {
            renderer = OpenApi3(ApiInfo("OpenRoadServer API", "v1.0"))

            // Return Swagger API definition under /contract/api/v1/swagger.json
            descriptionPath = "/swagger.json"

            // You can use security filter tio protect routes
            security = ApiKeySecurity(Query.int().required("api"), { it == 42 }) // Allow only requests with &api=42

            routes += SimpleRoute()
        }
    )
}

fun OpenRoadServer(): Http4kServer {
    val printingApp: HttpHandler = DebuggingFilters.PrintRequest()
        .then(ServerFilters.OpenTelemetryTracing())
        .then(ServerFilters.OpenTelemetryMetrics.RequestCounter())
        .then(ServerFilters.OpenTelemetryMetrics.RequestTimer()).then(OpenRoadApp())

    val server = printingApp.asServer(Undertow(0)).start()
    return server
}
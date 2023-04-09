package com.openroad.routes

import org.http4k.contract.ContractRoute
import org.http4k.contract.meta
import org.http4k.core.*
import org.http4k.core.Method.POST
import org.http4k.core.Status.Companion.OK
import org.http4k.format.Jackson.auto


object SimpleRoute {
    // this specifies the route contract, including examples of the input and output body objects - they will
    // get exploded into JSON schema in the OpenAPI docs
    private val spec = "/hello" meta {
        summary = "echoes the name and message sent to it"
    } bindContract Method.GET

    // note that because we don't have any dynamic parameters, we can use a HttpHandler instance instead of a function
    private val echo: HttpHandler = { request: Request ->
        Response(OK).body("HelloThere")
    }

    operator fun invoke(): ContractRoute = spec to echo
}

package com.openroad

import io.klogging.config.DEFAULT_CONSOLE
import io.klogging.config.loggingConfiguration

fun main() {
    loggingConfiguration { DEFAULT_CONSOLE() }
    val server = OpenRoadServer()

    println("Server started on " + server.port())
}


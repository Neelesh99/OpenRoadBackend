package com.openroad.utils

import org.http4k.core.Status

class ServiceException(message: String, cause: Throwable = Exception(), val statusCode: Status) : Exception(message, cause) {

}
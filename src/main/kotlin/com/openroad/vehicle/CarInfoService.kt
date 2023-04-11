package com.openroad.vehicle

import arrow.core.Either
import com.openroad.vehicle.types.VehicleData

interface CarInfoService {

    fun getDetailsFor(regPlate: String) : Either<Exception, VehicleData>

}
package com.openroad.vehicle.types

import com.fasterxml.jackson.databind.JsonNode
import org.http4k.format.Jackson

data class VehicleData(val vehicleInformation: VehicleInformation, val vehicleTechnicals: VehicleTechnicals) {
    fun toJson(): JsonNode {
        return Jackson.obj(
            "vehicleInformation" to vehicleInformation.toJson(),
            "vehicleTechnicals" to vehicleTechnicals.toJson()
        )
    }

    companion object {
        fun fromExternalJson(jsonNode: JsonNode): VehicleData {
            val innerNode = jsonNode.get("Response").get("DataItems")
            val vehicleInformation = VehicleInformation.fromExternalJson(innerNode)
                val vehicleTechnicals = VehicleTechnicals.fromExternalJson(innerNode)
                return VehicleData(vehicleInformation, vehicleTechnicals)
        }

        fun fromJson(jsonNode: JsonNode): VehicleData {
            return VehicleData(VehicleInformation.fromJson(jsonNode.get("vehicleInformation")), VehicleTechnicals.fromJson(jsonNode.get("vehicleTechnicals")))
        }
    }

}

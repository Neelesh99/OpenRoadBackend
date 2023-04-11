package com.openroad.vehicle

import com.fasterxml.jackson.databind.JsonNode

data class VehicleData(val vehicleInformation: VehicleInformation, val vehicleTechnicals: VehicleTechnicals) {
    companion object {
        fun fromJson(jsonNode: JsonNode): VehicleData {
            val innerNode = jsonNode.get("Response").get("DataItems")
            val vehicleInformation = VehicleInformation.fromJson(innerNode)
                val vehicleTechnicals = VehicleTechnicals.fromJson(innerNode)
                return VehicleData(vehicleInformation, vehicleTechnicals)
        }
    }

}

package com.openroad.vehicle

import com.fasterxml.jackson.databind.JsonNode

data class VehicleTechnicals(val dimensions: VehicleDimensions, val powertrain: PowerTrain, val consumption: Consumption) {
    companion object {
        fun fromJson(jsonNode: JsonNode): VehicleTechnicals {
            val vehicleDimensions = VehicleDimensions.fromJson(jsonNode.get("TechnicalDetails").get("Dimensions")!!)
            val powertrain = InternalCombustion.fromJson(jsonNode)
            val consumption = InternalCombustionConsumption.fromJson(jsonNode)
            return VehicleTechnicals(vehicleDimensions, powertrain, consumption)
        }
    }

}

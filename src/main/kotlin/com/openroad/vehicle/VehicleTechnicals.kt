package com.openroad.vehicle

import com.fasterxml.jackson.databind.JsonNode
import org.http4k.format.Jackson

data class VehicleTechnicals(val dimensions: VehicleDimensions, val powertrain: PowerTrain, val consumption: Consumption) {
    fun toJson(): JsonNode {
        return Jackson.obj(
            "dimensions" to dimensions.toJson(),
            "powertrain" to powertrain.toJson(),
            "consumption" to consumption.toJson()
        )
    }

    companion object {
        fun fromExternalJson(jsonNode: JsonNode): VehicleTechnicals {
            val vehicleDimensions = VehicleDimensions.fromExternalJson(jsonNode.get("TechnicalDetails").get("Dimensions")!!)
            val powertrain = InternalCombustion.fromExternalJson(jsonNode)
            val consumption = InternalCombustionConsumption.fromExternalJson(jsonNode)
            return VehicleTechnicals(vehicleDimensions, powertrain, consumption)
        }

        fun fromJson(jsonNode: JsonNode): VehicleTechnicals {
            return VehicleTechnicals(
                VehicleDimensions.fromJson(jsonNode.get("dimensions")),
                InternalCombustion.fromJson(jsonNode.get("powertrain")),
                InternalCombustionConsumption.fromJson(jsonNode.get("consumption"))
            )
        }
    }

}

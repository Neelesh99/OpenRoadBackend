package com.openroad.vehicle

import com.fasterxml.jackson.databind.JsonNode

interface Consumption {
    fun fuelType(): FuelType
}

data class InternalCombustionConsumption(val combinedMpg: Double, private val fuelType: FuelType) : Consumption {
    override fun fuelType(): FuelType {
        return fuelType
    }

    companion object {
        fun fromJson(jsonNode: JsonNode): InternalCombustionConsumption {
            val combinedMpg = jsonNode.get("TechnicalDetails").get("Consumption").get("Combined").get("Mpg").doubleValue()
            val fuelType = FuelType.defaultedValueOf(jsonNode.get("VehicleRegistration").get("FuelType").textValue())
            return InternalCombustionConsumption(combinedMpg, fuelType)
        }
    }

}

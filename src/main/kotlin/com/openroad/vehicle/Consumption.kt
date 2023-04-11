package com.openroad.vehicle

import com.fasterxml.jackson.databind.JsonNode
import org.http4k.format.Jackson
import org.http4k.format.Jackson.number
import org.http4k.format.Jackson.string

interface Consumption {
    fun fuelType(): FuelType
    fun toJson(): JsonNode
}

data class InternalCombustionConsumption(val combinedMpg: Double, private val fuelType: FuelType) : Consumption {
    override fun fuelType(): FuelType {
        return fuelType
    }

    override fun toJson(): JsonNode {
        return Jackson.obj(
            "combinedMpg" to number(combinedMpg),
            "fuelType" to string(fuelType.name)
        )
    }

    companion object {
        fun fromExternalJson(jsonNode: JsonNode): InternalCombustionConsumption {
            val combinedMpg = jsonNode.get("TechnicalDetails").get("Consumption").get("Combined").get("Mpg").doubleValue()
            val fuelType = FuelType.defaultedValueOf(jsonNode.get("VehicleRegistration").get("FuelType").textValue())
            return InternalCombustionConsumption(combinedMpg, fuelType)
        }

        fun fromJson(jsonNode: JsonNode): InternalCombustionConsumption {
            return InternalCombustionConsumption(
                jsonNode.get("combinedMpg").doubleValue(),
                FuelType.defaultedValueOf(jsonNode.get("fuelType").textValue())
            )
        }
    }

}

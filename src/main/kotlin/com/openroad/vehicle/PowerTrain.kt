package com.openroad.vehicle

import com.fasterxml.jackson.databind.JsonNode
import org.http4k.format.Jackson
import org.http4k.format.Jackson.number
import org.http4k.format.Jackson.string

interface PowerTrain {
    fun fuelType() : FuelType

    fun power() : Double

    fun co2() : Double
    fun toJson(): JsonNode
}
data class InternalCombustion(
    val fuelType: FuelType,
    val capacity: Int,
    val aspiration: String,
    val cylinders: Int,
    val power: Double,
    val torque: Double,
    val co2: Double
) : PowerTrain {
    override fun fuelType(): FuelType {
        return fuelType
    }

    override fun power(): Double {
        return power
    }

    override fun co2(): Double {
        return co2
    }

    override fun toJson(): JsonNode {
        return Jackson.obj(
            "fuelType" to string(fuelType.name),
            "capacity" to number(capacity),
            "aspiration" to string(aspiration),
            "cylinders" to number(cylinders),
            "power" to number(power),
            "torque" to number(torque),
            "co2" to number(co2)
        )
    }

    companion object {
        fun fromExternalJson(jsonNode: JsonNode): InternalCombustion {
            val registrationNode = jsonNode.get("VehicleRegistration")
            val capacity = registrationNode.get("EngineCapacity").textValue().toInt()
            val technicalDetailsNode = jsonNode.get("TechnicalDetails")
            val engineNode = technicalDetailsNode.get("General").get("Engine")
            val aspiration = engineNode.get("Aspiration").textValue()
            val cylinders = engineNode.get("NumberOfCylinders").intValue()
            val performanceNode = technicalDetailsNode.get("Performance")
            val power = performanceNode.get("Power").get("Bhp").doubleValue()
            val torque = performanceNode.get("Torque").get("Nm").doubleValue()
            val co2 = performanceNode.get("Co2").doubleValue()
            val fuelType = FuelType.defaultedValueOf(registrationNode.get("FuelType").textValue())
            return InternalCombustion(fuelType, capacity, aspiration, cylinders, power, torque, co2)
        }

        fun fromJson(jsonNode: JsonNode): InternalCombustion {
            return InternalCombustion(
                FuelType.defaultedValueOf(jsonNode.get("fuelType").textValue()),
                jsonNode.get("capacity").intValue(),
                jsonNode.get("aspiration").textValue(),
                jsonNode.get("cylinders").intValue(),
                jsonNode.get("power").doubleValue(),
                jsonNode.get("torque").doubleValue(),
                jsonNode.get("co2").doubleValue()
            )
        }
    }

}

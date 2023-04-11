package com.openroad.vehicle

import com.fasterxml.jackson.databind.JsonNode
import org.http4k.format.Jackson
import org.http4k.format.Jackson.number

data class VehicleDimensions(
    val mass: Double,
    val doors: Int,
    val seats: Int,
    val fuelTankCapacity: Double,
    val length: Double,
    val width: Double
) {
    fun toJson(): JsonNode {
        return Jackson.obj(
            "mass" to number(mass),
            "doors" to number(doors),
            "seats" to number(seats),
            "fuelTankCapacity" to number(fuelTankCapacity),
            "length" to number(length),
            "width" to number(width)
        )
    }

    companion object {
        fun fromExternalJson(jsonNode: JsonNode): VehicleDimensions {
            val mass = jsonNode.get("GrossVehicleWeight").doubleValue()
            val doors = jsonNode.get("NumberOfDoors").intValue()
            val seats = jsonNode.get("NumberOfSeats").intValue()
            val fuelTankCapacity = jsonNode.get("FuelTankCapacity").doubleValue()
            val length = jsonNode.get("CarLength").doubleValue()
            val width = jsonNode.get("Width").doubleValue()
            return VehicleDimensions(mass, doors, seats, fuelTankCapacity, length, width)
        }

        fun fromJson(jsonNode: JsonNode): VehicleDimensions {
            return VehicleDimensions(
                jsonNode.get("mass").doubleValue(),
                jsonNode.get("doors").intValue(),
                jsonNode.get("seats").intValue(),
                jsonNode.get("fuelTankCapacity").doubleValue(),
                jsonNode.get("length").doubleValue(),
                jsonNode.get("width").doubleValue()
            )
        }
    }

}

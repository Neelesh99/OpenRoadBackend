package com.openroad.vehicle

import com.fasterxml.jackson.databind.JsonNode

data class VehicleDimensions(
    val mass: Double,
    val doors: Int,
    val seats: Int,
    val fuelTankCapacity: Double,
    val length: Double,
    val width: Double
) {
    companion object {
        fun fromJson(jsonNode: JsonNode): VehicleDimensions {
            val mass = jsonNode.get("GrossVehicleWeight").doubleValue()
            val doors = jsonNode.get("NumberOfDoors").intValue()
            val seats = jsonNode.get("NumberOfSeats").intValue()
            val fuelTankCapacity = jsonNode.get("FuelTankCapacity").doubleValue()
            val length = jsonNode.get("CarLength").doubleValue()
            val width = jsonNode.get("Width").doubleValue()
            return VehicleDimensions(mass, doors, seats, fuelTankCapacity, length, width)
        }
    }

}

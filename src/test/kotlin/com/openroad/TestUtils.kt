package com.openroad

import com.openroad.vehicle.types.*

fun getExpectedVehicleData(): VehicleData = VehicleData(
    VehicleInformation(
        type = "Car",
        make = "JAGUAR",
        model = "XF",
        trim = "R-SPORT",
        isElectric = false,
        colour = "SILVER",
        destroyed = false,
    ),
    VehicleTechnicals(
        dimensions = VehicleDimensions(
            mass = 2260.0,
            doors = 4,
            seats = 5,
            fuelTankCapacity = 74.00,
            length = 4954.0,
            width = 2091.0
        ),
        powertrain = InternalCombustion(
            fuelType = FuelType.PETROL,
            capacity = 1997,
            aspiration = "Turbo Charged",
            cylinders = 4,
            power = 246.70,
            torque = 365.0,
            co2 = 154.0
        ),
        consumption = InternalCombustionConsumption(
            combinedMpg = 41.50,
            fuelType = FuelType.PETROL
        )
    )
)
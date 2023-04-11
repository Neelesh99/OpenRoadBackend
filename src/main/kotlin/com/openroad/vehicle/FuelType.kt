package com.openroad.vehicle

enum class FuelType {
    PETROL,
    DIESEL,
    LPG,
    ELECTRIC,
    OTHER;

    companion object {
        fun defaultedValueOf(value: String) : FuelType {
            return try {
                FuelType.valueOf(value)
            } catch (e: Exception){
                OTHER
            }
        }
    }
}
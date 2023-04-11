package com.openroad.vehicle

import com.fasterxml.jackson.databind.JsonNode

data class VehicleInformation(
    val type: String,
    val make: String,
    val model: String,
    val trim: String,
    val isElectric: Boolean,
    val colour: String,
    val destroyed: Boolean
) {
    companion object {
        fun fromJson(jsonNode: JsonNode): VehicleInformation {
            val registrationNode = jsonNode.get("VehicleRegistration")
            val type = registrationNode.get("VehicleClass").textValue()
            val colour = registrationNode.get("Colour").textValue()
            val destroyed = registrationNode.get("CertificateOfDestructionIssued").booleanValue()
            val classificationDetails = jsonNode.get("ClassificationDetails")
            val smmtShort = classificationDetails.get("Smmt")
            val make = smmtShort.get("Make").textValue()
            val model = smmtShort.get("Range").textValue()
            val trim = smmtShort.get("Trim").textValue()
            val isElectric = classificationDetails.get("Ukvd").get("IsElectricVehicle").booleanValue()
            return VehicleInformation(type, make, model, trim, isElectric, colour, destroyed)
        }
    }

}

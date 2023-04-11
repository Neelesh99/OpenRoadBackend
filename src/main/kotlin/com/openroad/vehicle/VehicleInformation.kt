package com.openroad.vehicle

import com.fasterxml.jackson.databind.JsonNode
import org.http4k.format.Jackson
import org.http4k.format.Jackson.bool
import org.http4k.format.Jackson.boolean
import org.http4k.format.Jackson.string

data class VehicleInformation(
    val type: String,
    val make: String,
    val model: String,
    val trim: String,
    val isElectric: Boolean,
    val colour: String,
    val destroyed: Boolean
) {
    fun toJson(): JsonNode {
        return Jackson.obj(
            "type" to string(type),
            "make" to string(make),
            "model" to string(model),
            "trim" to string(trim),
            "isElectric" to boolean(isElectric),
            "colour" to string(colour),
            "destroyed" to boolean(destroyed)
        )
    }

    companion object {
        fun fromExternalJson(jsonNode: JsonNode): VehicleInformation {
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

        fun fromJson(jsonNode: JsonNode): VehicleInformation {
            return VehicleInformation(
                jsonNode.get("type").textValue(),
                jsonNode.get("make").textValue(),
                jsonNode.get("model").textValue(),
                jsonNode.get("trim").textValue(),
                jsonNode.get("isElectric").booleanValue(),
                jsonNode.get("colour").textValue(),
                jsonNode.get("destroyed").booleanValue()
            )
        }
    }

}

import com.openroad.getExpectedVehicleData
import com.openroad.vehicle.types.*
import org.http4k.format.Jackson
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DataTypesTest {

    @Test
    fun `will convert CarInfoService json into VehicleData datatype`(){
        val fileData = DataTypesTest::class.java.getResource("/exampleVehicleLookup.json").readText()
        val jsonNode = Jackson.parse(fileData)
        val expectedVehicleData = getExpectedVehicleData()
        assertEquals(expectedVehicleData, VehicleData.fromExternalJson(jsonNode))
    }

    @Test
    fun `will roundtrip VehicleData datatype`(){
        val vehicleData = getExpectedVehicleData()
        assertEquals(vehicleData, VehicleData.fromJson(vehicleData.toJson()))
    }

}


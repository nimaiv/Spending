package hr.nimai.spending

import hr.nimai.spending.domain.use_case.GetProizvodInfoFromBarcode
import org.junit.Test

class BarcodeRequestTest {

    @Test
    fun callBarcodeRequest() {
        val barcode = "3850102314126"

        val useCase = GetProizvodInfoFromBarcode()

    }
}
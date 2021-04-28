package au.com.nig.fraud

import au.com.nig.fraud.io.IO
import java.math.BigDecimal

object TransactionsAnalyzer {
    fun result(filePath: String, threshold: BigDecimal): String {
        val fileContent = IO.readFile(filePath)

        TODO()
    }
}

package au.com.nig.fraud

import org.apache.commons.csv.CSVRecord
import java.math.BigDecimal
import java.time.LocalDateTime

data class Transaction(val card: String, val date: LocalDateTime, val amount: BigDecimal) {
    companion object {
        fun mapToTransaction(record: CSVRecord): Transaction = TODO()
    }
}

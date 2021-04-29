package au.com.nig.fraud.model

import org.apache.commons.csv.CSVRecord
import java.math.BigDecimal
import java.time.LocalDateTime

data class Transaction(val card: String, val date: LocalDateTime, val amount: BigDecimal) {
    companion object {
        fun mapToTransaction(record: CSVRecord): Transaction {
            val date = LocalDateTime.parse(record[1])
            val amt = BigDecimal.valueOf(record[2].toDouble())
            return Transaction(record[0], date, amt)
        }
    }
}

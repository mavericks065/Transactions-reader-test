package au.com.nig.fraud.model

import au.com.nig.fraud.io.IO
import au.com.nig.fraud.model.Transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.math.BigDecimal
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDateTime

object TransactionTest : Spek({
    describe("A csv record") {
        it("should be mapped to a transaction") {
            // Given
            val expected = Transaction(
                card = "10d7ce2f43e35fa57d1bbf8b1e2",
                date = LocalDateTime.of(2014, 4, 29, 13, 15, 54),
                amount = BigDecimal.valueOf(10.0)
            )

            val currentRelativePath: Path = Paths.get("")
            val s = currentRelativePath.toAbsolutePath().toString()
            val filePath = "$s/src/test/resources/short_sample.csv"
            val record = IO.readFile(filePath)[0]

            // When
            val result = Transaction.mapToTransaction(record)

            // Then
            assertEquals("10d7ce2f43e35fa57d1bbf8b1e2", result.card)
            assertEquals(expected, result)
        }
    }
})

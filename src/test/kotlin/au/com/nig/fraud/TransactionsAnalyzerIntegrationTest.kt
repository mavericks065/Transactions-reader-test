package au.com.nig.fraud

import org.junit.jupiter.api.Assertions.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.math.BigDecimal
import java.nio.file.Path
import java.nio.file.Paths

object TransactionsAnalyzerIntegrationTest: Spek({
    group("when reading a file and performing an analysis"){
        describe("without any fraudulent activity") {
            it("should tell us the activity is clean") {
                // Given
                val currentRelativePath: Path = Paths.get("")
                val s = currentRelativePath.toAbsolutePath().toString()
                val filePath = "$s/src/test/resources/short_sample.csv"
                val threshold = BigDecimal.valueOf(1000.0)

                // When
                val result = TransactionsAnalyzer.analyzeTransactions(filePath, threshold)

                // Then
                assertEquals("No Fraudulent activity", result)
            }
        }
        describe("with fraudulent activity") {
            it("should send us back the hash of the credit card number") {
                // Given
                val currentRelativePath: Path = Paths.get("")
                val s = currentRelativePath.toAbsolutePath().toString()
                val filePath = "$s/src/test/resources/short_sample.csv"
                val threshold = BigDecimal.valueOf(40.0)

                // When
                val result = TransactionsAnalyzer.analyzeTransactions(filePath, threshold)

                // Then
                assertEquals("10d7ce2f43e35fa57d1bbf8b1e2", result)
            }
            it("should send us back the hash of the credit card number even if activity is fraudulent only over a sliding period") {
                // Given
                val currentRelativePath: Path = Paths.get("")
                val s = currentRelativePath.toAbsolutePath().toString()
                val filePath = "$s/src/test/resources/sample.csv"
                val threshold = BigDecimal.valueOf(150.0)

                // When
                val result = TransactionsAnalyzer.analyzeTransactions(filePath, threshold)

                // Then
                assertEquals("10d7ce2f43e35fa57d1bbf8b1e2", result)
            }
        }

    }
})

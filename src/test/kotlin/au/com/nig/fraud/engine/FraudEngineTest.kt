package au.com.nig.fraud.engine

import au.com.nig.fraud.engine.FraudEngine
import au.com.nig.fraud.model.FraudResult
import au.com.nig.fraud.model.Transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.math.BigDecimal
import java.time.LocalDateTime

object FraudEngineTest : Spek({
    describe("when receiving transactions and all transactions are below the threshold") {
        it("should not return the card number") {
            // Given
            val expected = FraudResult(emptyList())
            val transactions = listOf(
                Transaction(
                    card = "10d7ce2f43e35fa57d1bbf8b1e2",
                    date = LocalDateTime.of(2014, 4, 29, 13, 15, 54),
                    amount = BigDecimal.valueOf(10.0)
                ),
                Transaction(
                    card = "10d7ce2f43e35fa57d1bbf8b1e2",
                    date = LocalDateTime.of(2014, 4, 29, 15, 15, 54),
                    amount = BigDecimal.valueOf(20.0)
                ),
                Transaction(
                    card = "10d7ce2f43e35fa57d1bbf8b1e2",
                    date = LocalDateTime.of(2014, 4, 30, 1, 15, 54),
                    amount = BigDecimal.valueOf(15.0)
                )
            )

            // When
            val result = FraudEngine.checkTransactions(transactions, BigDecimal.valueOf(100.0))

            // Then
            assertEquals(expected, result)
        }
    }
    describe("when receiving transactions and the amount is above the threshold") {
        it("should return the card number") {
            // Given
            val expected = FraudResult(listOf("10d7ce2f43e35fa57d1bbf8b1e2"))
            val transactions = listOf(
                Transaction(
                    card = "10d7ce2f43e35fa57d1bbf8b1e2",
                    date = LocalDateTime.of(2014, 4, 29, 13, 15, 54),
                    amount = BigDecimal.valueOf(10.0)
                ),
                Transaction(
                    card = "10d7ce2f43e35fa57d1bbf8b1e2",
                    date = LocalDateTime.of(2014, 4, 29, 15, 15, 54),
                    amount = BigDecimal.valueOf(20.0)
                ),
                Transaction(
                    card = "10d7ce2f43e35fa57d1bbf8b1e2",
                    date = LocalDateTime.of(2014, 4, 30, 1, 15, 54),
                    amount = BigDecimal.valueOf(15.0)
                )
            )

            // When
            val result = FraudEngine.checkTransactions(transactions, BigDecimal.valueOf(40.0))

            // Then
            assertEquals(expected, result)
        }
        describe("not on the first 24 hours but over a sliding period") {
            it("should return the card number") {
                // Given
                val expected = FraudResult(listOf("10d7ce2f43e35fa57d1bbf8b1e2"))
                val transactions = listOf(
                    Transaction(
                        card = "10d7ce2f43e35fa57d1bbf8b1e2",
                        date = LocalDateTime.of(2014, 4, 29, 13, 15, 54),
                        amount = BigDecimal.valueOf(10.0)
                    ),
                    Transaction(
                        card = "10d7ce2f43e35fa57d1bbf8b1e2",
                        date = LocalDateTime.of(2014, 4, 29, 15, 15, 54),
                        amount = BigDecimal.valueOf(20.0)
                    ),
                    Transaction(
                        card = "10d7ce2f43e35fa57d1bbf8b1e2",
                        date = LocalDateTime.of(2014, 4, 29, 23, 15, 54),
                        amount = BigDecimal.valueOf(50.0)
                    ),
                    Transaction(
                        card = "10d7ce2f43e35fa57d1bbf8b1e2",
                        date = LocalDateTime.of(2014, 4, 30, 14, 15, 54),
                        amount = BigDecimal.valueOf(50.0)
                    )
                )

                // When
                val result = FraudEngine.checkTransactions(transactions, BigDecimal.valueOf(100.0))

                // Then
                assertEquals(expected, result)
            }
        }
    }

    describe("when receiving transactions from several cards and the amount of one cards is above the threshold") {
        it("should return this card number") {
            // Given
            val expected = FraudResult(listOf("10d7ce2f43e35fa57d1bbf8b1FF"))
            val transactions = listOf(
                Transaction(
                    card = "10d7ce2f43e35fa57d1bbf8b1FF",
                    date = LocalDateTime.of(2014, 4, 29, 13, 15, 54),
                    amount = BigDecimal.valueOf(10.0)
                ),
                Transaction(
                    card = "10d7ce2f43e35fa57d1bbf8b1e2",
                    date = LocalDateTime.of(2014, 4, 29, 15, 15, 54),
                    amount = BigDecimal.valueOf(20.0)
                ),
                Transaction(
                    card = "10d7ce2f43e35fa57d1bbf8b1FF",
                    date = LocalDateTime.of(2014, 4, 29, 23, 15, 54),
                    amount = BigDecimal.valueOf(40.0)
                ),
                Transaction(
                    card = "10d7ce2f43e35fa57d1bbf8b1FF",
                    date = LocalDateTime.of(2014, 4, 30, 1, 15, 54),
                    amount = BigDecimal.valueOf(50.0)
                )
            )

            // When
            val result = FraudEngine.checkTransactions(transactions, BigDecimal.valueOf(50.0))

            // Then
            assertEquals(expected, result)
        }
    }
})

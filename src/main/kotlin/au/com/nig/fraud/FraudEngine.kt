package au.com.nig.fraud

import java.math.BigDecimal

object FraudEngine {
    fun checkTransactions(transactions: List<Transaction>, threshold: BigDecimal): FraudResult {
        val fraudCards = mutableListOf<String>()
        transactions
            .groupBy (keySelector = { it.card })
            .forEach { entry ->
                val card = entry.key
                val cardTransactions = entry.value
                val isFraud = isCardFraudedOnLast24Hours(cardTransactions, threshold)
                if (isFraud)
                    fraudCards.add(card)
            }
        return FraudResult(fraudCards.toList())
    }

    fun isCardFraudedOnLast24Hours(transactions: List<Transaction>, threshold: BigDecimal): Boolean {

        val totalAmount = transactions.map { it.amount }
            .fold(BigDecimal.ZERO) { acc, x -> acc.add(x) }

        return totalAmount >= threshold
    }
}

data class FraudResult(val fraudulentCards: List<String>)

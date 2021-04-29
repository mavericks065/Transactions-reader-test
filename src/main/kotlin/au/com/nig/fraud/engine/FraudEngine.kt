package au.com.nig.fraud.engine

import au.com.nig.fraud.model.FraudResult
import au.com.nig.fraud.model.Transaction
import java.math.BigDecimal

object FraudEngine {
    fun checkTransactions(transactions: List<Transaction>, threshold: BigDecimal): FraudResult {
        val fraudCards = mutableListOf<String>()
        transactions
            .groupBy(keySelector = { it.card })
            .forEach { entry ->
                val card = entry.key
                val cardTransactions = entry.value
                val isFraud = isCardFraudOn24HoursPeriod(cardTransactions, threshold)
                if (isFraud)
                    fraudCards.add(card)
            }
        return FraudResult(fraudCards.toList())
    }

    private fun isCardFraudOn24HoursPeriod(transactions: List<Transaction>, threshold: BigDecimal): Boolean {
        var isCardFraud = false
        for (transaction in transactions) {
            val dateLimit = transaction.date.plusDays(1L)
            val totalAmountOver24Hrs = transactions.filter { it.date < dateLimit }
                .map { it.amount }
                .fold(BigDecimal.ZERO) { acc, x -> acc.add(x) }
            if (totalAmountOver24Hrs >= threshold) {
                isCardFraud = true
                break;
            }
        }

        return isCardFraud
    }
}

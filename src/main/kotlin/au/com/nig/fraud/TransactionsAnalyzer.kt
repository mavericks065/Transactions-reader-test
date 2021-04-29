package au.com.nig.fraud

import au.com.nig.fraud.engine.FraudEngine
import au.com.nig.fraud.io.IO
import au.com.nig.fraud.model.Transaction
import java.math.BigDecimal

object TransactionsAnalyzer {
    fun analyzeTransactions(filePath: String, threshold: BigDecimal): String {
        val transactions = IO.readFile(filePath)
            .map { Transaction.mapToTransaction(it) }

        val fraudulentCards = FraudEngine.checkTransactions(transactions, threshold).fraudulentCards

        return if (fraudulentCards.isEmpty())
            "No Fraudulent activity"
        else fraudulentCards
            .reduce { acc, str -> "$acc,$str" }
    }
}

package au.com.nig

import au.com.nig.fraudulentprogram.TransactionsAnalyzer
import java.math.BigDecimal
import java.util.logging.Level
import java.util.logging.Logger

val logger: Logger = Logger.getLogger("main")

fun main(args: Array<String>) {
    val filePath = args[0]
    val threshold = BigDecimal.valueOf(args[1].toDouble())

    val analysisResult = TransactionsAnalyzer.result(filePath, threshold)

    logger.log(Level.INFO, "##### Fraudulent card number #####")
    logger.log(Level.INFO, analysisResult)
}

package au.com.nig.fraudulentprogram.io

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import java.io.FileNotFoundException
import java.nio.file.Paths
import java.util.logging.Level
import java.util.logging.Logger

object IO {
    private val logger = Logger.getLogger("IO")

    fun readFile(filePath: String): List<CSVRecord> {
        try {
            val bufReader = Paths.get(filePath).toFile().bufferedReader()
            val parser = CSVParser(
                bufReader,
                CSVFormat.DEFAULT.withTrim()
            )
            return parser.records
        } catch (e: FileNotFoundException) {
            logger.log(Level.SEVERE, e.message)
            throw IOException("Wrong file name")
        }
    }
}

package example.chiwoo.demo.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateUtils {

    companion object {
        val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val currentDtmToString: String = FORMATTER.format(LocalDateTime.now())
        val toLocalDateTime: (String) -> LocalDateTime = { LocalDateTime.parse(it, FORMATTER) }
        val localDtmToString: (LocalDateTime) -> String = { FORMATTER.format(it) }
    }

}
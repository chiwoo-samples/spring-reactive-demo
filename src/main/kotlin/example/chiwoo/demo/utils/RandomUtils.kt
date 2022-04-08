package example.chiwoo.demo.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.roundToLong

class RandomUtils {
    companion object {

        fun <P1, P2, R> ((P1, P2) -> R).curried(): (P1) -> (P2) -> R {
            return { p1: P1 -> { p2: P2 -> this(p1, p2) } }
        }


        private val RANDOM = Random()

        fun nextInt(range: Int): Int {
            return RANDOM.nextInt(range - 1) + 1
        }

        fun nextInt(from: Int, to: Int): Int {
            return RANDOM.nextInt(to - from) + from
        }

        fun nextLong(range: Long): Long {
            return ThreadLocalRandom.current().nextLong(1, range)
        }

        fun nextLong(from: Long, to: Long): Long {
            return ThreadLocalRandom.current().nextLong(from, to)
        }

        val nextDouble =
            { from: Int, to: Int -> ((from + RANDOM.nextDouble() * (to - from)) * 100.00).roundToLong() / 100.00 }

        // private val ALPHA_NUMERIC = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        private val ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray()
        private val ALPHA_NUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray()


        private val EMAILS = "gmail.com,naver.com,daum.net,hotmail.com".trim().split(",")

        val nextAlphaNumeric = { length: Int -> List(length) { ALPHA_NUMERIC.random() }.joinToString("") }
        val nextWord = { length: Int -> List(length) { ALPHA.random() }.joinToString("") }
        val nextEmail = { name: String -> name + "@" + EMAILS[nextInt(EMAILS.size)] }

        private val toEpochMilli =
            { localDtm: LocalDateTime -> localDtm.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() }

        fun nextLocalDtmFromYear(from: Int) :LocalDateTime {
            val now = LocalDateTime.now()
            val fromLocalDtm = LocalDateTime.of(from, 1, 1, 0, 0)
            val days = ChronoUnit.DAYS.between(fromLocalDtm, now).toLong()
            return now.minusDays(nextLong(days) )
        }

        fun <T> nextArray(values: Array<T>): T {
            return values[RANDOM.nextInt(values.size)]
        }

    }



}
package io.mustelidae.riverotter.domain.calendar

import com.usingsky.calendar.KoreanLunarCalendar
import java.time.LocalDate

class PlanetCalendar {
    private val koreanLunarCalendar = KoreanLunarCalendar()

    fun solarToLunar(date: LocalDate): LocalDate {
        koreanLunarCalendar.setSolarDate(date.year, date.monthValue, date.dayOfMonth)
        return koreanLunarCalendar.lunarLocalDate
    }

    fun lunarToSolar(date: LocalDate): LocalDate {
        val monthOfIntercalation = intercalations.find { it.first == date.year && it.second == date.monthValue }

        val interaction = (monthOfIntercalation != null)

        koreanLunarCalendar.setLunarDate(date.year, date.monthValue, date.dayOfMonth, interaction)
        return koreanLunarCalendar.solarLocalDate
    }

    companion object {
        val intercalations = listOf(
            Pair(1900, 8),
            Pair(1903, 5),
            Pair(1906, 4),
            Pair(1909, 2),
            Pair(1911, 6),
            Pair(1914, 5),
            Pair(1917, 2),
            Pair(1919, 7),
            Pair(1922, 5),
            Pair(1925, 4),
            Pair(1928, 2),
            Pair(1930, 6),
            Pair(1933, 5),
            Pair(1936, 3),
            Pair(1938, 7),
            Pair(1941, 6),
            Pair(1944, 4),
            Pair(1947, 2),
            Pair(1949, 7),
            Pair(1952, 5),
            Pair(1955, 3),
            Pair(1957, 8),
            Pair(1960, 6),
            Pair(1963, 4),
            Pair(1966, 3),
            Pair(1968, 7),
            Pair(1971, 5),
            Pair(1974, 4),
            Pair(1976, 8),
            Pair(1979, 6),
            Pair(1982, 4),
            Pair(1984, 10),
            Pair(1987, 6),
            Pair(1990, 5),
            Pair(1993, 3),
            Pair(1995, 8),
            Pair(1998, 5),
            Pair(2001, 4),
            Pair(2004, 2),
            Pair(2006, 7),
            Pair(2009, 5),
            Pair(2012, 3),
            Pair(2014, 9),
            Pair(2017, 5),
            Pair(2020, 4),
            Pair(2023, 2),
            Pair(2025, 6),
            Pair(2028, 5),
            Pair(2031, 3),
            Pair(2033, 11),
            Pair(2036, 6),
            Pair(2039, 5),
            Pair(2042, 2),
            Pair(2044, 7),
            Pair(2047, 5),
            Pair(2050, 3)
        )
    }
}

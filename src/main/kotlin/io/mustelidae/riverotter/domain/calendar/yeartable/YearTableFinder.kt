package io.mustelidae.riverotter.domain.calendar.yeartable

import io.mustelidae.riverotter.config.DataNotFoundException
import io.mustelidae.riverotter.domain.calendar.yeartable.repository.YearTableRepository
import org.springframework.stereotype.Service
import java.util.Locale

@Service
class YearTableFinder(
    private val yearTableRepository: YearTableRepository
) {

    fun findOrThrow(locale: Locale): YearTable {
        return yearTableRepository.findByLocale(locale) ?: throw DataNotFoundException("$locale year table is not found")
    }

    fun hasYearTable(locale: Locale): Boolean {
        val yearTable = yearTableRepository.findByLocale(locale)
        return (yearTable != null)
    }
}

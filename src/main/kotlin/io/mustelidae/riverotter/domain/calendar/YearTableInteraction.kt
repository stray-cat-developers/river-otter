package io.mustelidae.riverotter.domain.calendar

import io.mustelidae.riverotter.domain.calendar.yeartable.YearTable
import io.mustelidae.riverotter.domain.calendar.yeartable.YearTableFinder
import io.mustelidae.riverotter.domain.calendar.yeartable.repository.YearTableRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.util.Locale

@Service
class YearTableInteraction(
    private val yearTableRepository: YearTableRepository,
    private val yearTableFinder: YearTableFinder,
) {

    fun save(locale: Locale, year: Int, calendarId: ObjectId): ObjectId {
        val yearTable = if (yearTableFinder.hasYearTable(locale)) {
            yearTableFinder.findOrThrow(locale).apply {
                addBy(year, calendarId)
            }
        } else {
            YearTable(locale, mutableListOf(YearTable.YearOfLocale(year, calendarId)))
        }

        return yearTableRepository.save(yearTable).id
    }
}

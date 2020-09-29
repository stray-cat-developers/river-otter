package io.mustelidae.riverotter.domain.calender.yeartable

import io.mustelidae.riverotter.domain.calender.yeartable.repository.YearTableRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.util.Locale

@Service
class YearTableInteraction(
    private val yearTableRepository: YearTableRepository,
    private val yearTableFinder: YearTableFinder
) {

    fun save(locale: Locale, year: Int, calenderId: ObjectId): ObjectId {
        val yearTable = if (yearTableFinder.hasYearTable(locale)) {
            yearTableFinder.findOrThrow(locale).apply {
                addBy(year, calenderId)
            }
        } else {
            YearTable(locale, mutableListOf(YearTable.YearOfLocale(year, calenderId)))
        }

        return yearTableRepository.save(yearTable).id
    }
}

package io.mustelidae.riverotter.domain.calendar.holiday.repository

import io.mustelidae.riverotter.domain.calendar.holiday.HolidayCalendar
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.Locale

@Repository
interface HolidayCalendarRepository : MongoRepository<HolidayCalendar, ObjectId> {
    fun findByYearAndLocale(year: Int, locale: Locale): HolidayCalendar?
}

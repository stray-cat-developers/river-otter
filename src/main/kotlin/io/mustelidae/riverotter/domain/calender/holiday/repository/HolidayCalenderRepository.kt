package io.mustelidae.riverotter.domain.calender.holiday.repository

import io.mustelidae.riverotter.domain.calender.holiday.HolidayCalender
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.Locale

@Repository
interface HolidayCalenderRepository : MongoRepository<HolidayCalender, ObjectId> {
    fun findByYearAndLocale(year: Int, locale: Locale): HolidayCalender?
}

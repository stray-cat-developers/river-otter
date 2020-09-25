package io.mustelidae.riverotter.domain.calender.holiday.repository

import io.mustelidae.riverotter.domain.calender.holiday.HolidayCalender
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface HolidayCalenderRepository : MongoRepository<HolidayCalender, ObjectId> {
    fun findByYearAndCountry(year: Int, country: String): HolidayCalender?
}

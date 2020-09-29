package io.mustelidae.riverotter.domain.calender.yeartable.repository

import io.mustelidae.riverotter.domain.calender.yeartable.YearTable
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Locale

interface YearTableRepository : MongoRepository<YearTable, ObjectId> {
    fun findByLocale(locale: Locale): YearTable?
}

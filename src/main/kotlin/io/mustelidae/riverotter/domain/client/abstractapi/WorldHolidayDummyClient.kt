package io.mustelidae.riverotter.domain.client.abstractapi

import io.mustelidae.riverotter.config.NotSupportCountryException
import io.mustelidae.riverotter.utils.ClientSupport
import io.mustelidae.riverotter.utils.Jackson
import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.util.Locale

class WorldHolidayDummyClient :
    ClientSupport(
        Jackson.getMapper(),
        false,
        LoggerFactory.getLogger(WorldHolidayDummyClient::class.java)
    ),
    WorldHolidayClient {

    override fun findHoliday(country: Locale, year: Int, month: Int, day: Int): WorldHolidayResources.Reply.Holidays {

        if (country == Locale.US) {
            val json = usaHoliday(LocalDate.of(year, month, day))
            return if (json.isNullOrBlank())
                WorldHolidayResources.Reply.Holidays()
            else
                json.fromJson()
        }

        throw NotSupportCountryException(country)
    }

    private fun usaHoliday(date: LocalDate): String? {
        if (date == LocalDate.of(2020, 1, 1)) {
            return """
                [
                    {
                        "name": "New Year's Day",
                        "name_local": "",
                        "language": "",
                        "description": "",
                        "country": "US",
                        "location": "",
                        "state": "",
                        "is_observed": false,
                        "type": "public_holiday",
                        "date": "01/01/2020",
                        "date_year": "2020",
                        "date_month": "01",
                        "date_day": "01",
                        "week_day": "Wednesday"
                    }
                ]                     
            """.trimIndent()
        }

        if (date == LocalDate.of(2020, 1, 20)) {
            return """
                [
                    {
                        "name": "Martin Luther King Jr. Day",
                        "name_local": "",
                        "language": "",
                        "description": "",
                        "country": "US",
                        "location": "",
                        "state": "",
                        "is_observed": false,
                        "type": "public_holiday",
                        "date": "01/20/2020",
                        "date_year": "2020",
                        "date_month": "01",
                        "date_day": "20",
                        "week_day": "Monday"
                    }
                ]                
            """.trimIndent()
        }

        if (date == LocalDate.of(2020, 2, 17)) {
            return """
                [
                    {
                        "name": "Presidents' Day",
                        "name_local": "",
                        "language": "",
                        "description": "",
                        "country": "US",
                        "location": "",
                        "state": "",
                        "is_observed": false,
                        "type": "public_holiday",
                        "date": "02/17/2020",
                        "date_year": "2020",
                        "date_month": "02",
                        "date_day": "17",
                        "week_day": "Monday"
                    }
                ]                
            """.trimIndent()
        }

        if (date == LocalDate.of(2020, 5, 25)) {
            return """
                [
                    {
                        "name": "Memorial Day",
                        "name_local": "",
                        "language": "",
                        "description": "",
                        "country": "US",
                        "location": "",
                        "state": "",
                        "is_observed": false,
                        "type": "public_holiday",
                        "date": "05/25/2020",
                        "date_year": "2020",
                        "date_month": "05",
                        "date_day": "25",
                        "week_day": "Monday"
                    }
                ]                
            """.trimIndent()
        }

        if (date == LocalDate.of(2020, 7, 3)) {
            return """
            [
                {
                    "name": "Independence Day",
                    "name_local": "",
                    "language": "",
                    "description": "",
                    "country": "US",
                    "location": "",
                    "state": "",
                    "is_observed": false,
                    "type": "public_holiday",
                    "date": "07/03/2020",
                    "date_year": "2020",
                    "date_month": "07",
                    "date_day": "03",
                    "week_day": "Friday"
                }
            ]                
            """.trimIndent()
        }

        if (date == LocalDate.of(2020, 9, 7)) {
            return """
            [
                {
                    "name": "Labor Day",
                    "name_local": "",
                    "language": "",
                    "description": "",
                    "country": "US",
                    "location": "",
                    "state": "",
                    "is_observed": false,
                    "type": "public_holiday",
                    "date": "09/07/2020",
                    "date_year": "2020",
                    "date_month": "09",
                    "date_day": "07",
                    "week_day": "Monday"
                }
            ]                
            """.trimIndent()
        }

        if (date == LocalDate.of(2020, 10, 12)) {
            return """
                [
                    {
                        "name": "Columbus Day",
                        "name_local": "",
                        "language": "",
                        "description": "",
                        "country": "US",
                        "location": "",
                        "state": "",
                        "is_observed": false,
                        "type": "public_holiday",
                        "date": "10/12/2020",
                        "date_year": "2020",
                        "date_month": "10",
                        "date_day": "12",
                        "week_day": "Monday"
                    }
                ]                
            """.trimIndent()
        }

        if (date == LocalDate.of(2020, 10, 12)) {
            return """
                 [
                    {
                        "name": "Veterans Day",
                        "name_local": "",
                        "language": "",
                        "description": "",
                        "country": "US",
                        "location": "",
                        "state": "",
                        "is_observed": false,
                        "type": "public_holiday",
                        "date": "11/11/2020",
                        "date_year": "2020",
                        "date_month": "11",
                        "date_day": "11",
                        "week_day": "Wednesday"
                    }
                ]               
            """.trimIndent()
        }

        if (date == LocalDate.of(2020, 11, 26)) {
            return """
                [
                    {
                        "name": "Thanksgiving Day",
                        "name_local": "",
                        "language": "",
                        "description": "",
                        "country": "US",
                        "location": "",
                        "state": "",
                        "is_observed": false,
                        "type": "public_holiday",
                        "date": "11/26/2020",
                        "date_year": "2020",
                        "date_month": "11",
                        "date_day": "26",
                        "week_day": "Thursday"
                    }
                ]              
            """.trimIndent()
        }

        if (date == LocalDate.of(2020, 12, 25)) {
            return """
                [
                    {
                        "name": "Christmas Day",
                        "name_local": "",
                        "language": "",
                        "description": "",
                        "country": "US",
                        "location": "",
                        "state": "",
                        "is_observed": false,
                        "type": "public_holiday",
                        "date": "12/25/2020",
                        "date_year": "2020",
                        "date_month": "12",
                        "date_day": "25",
                        "week_day": "Friday"
                    }
                ]          
            """.trimIndent()
        }

        return null
    }
}

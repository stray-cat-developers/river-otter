package io.mustelidae.riverotter.domain.client.abstractapi

import java.util.Locale

/**
 * using https://app.abstractapi.com/#/holidays
 * free plan 1,000 API calls / month
 * only call day by day.
 */
interface WorldHolidayClient {

    fun findHoliday(country: Locale, year: Int, month: Int, day: Int): WorldHolidayResources.Reply.Holidays
}

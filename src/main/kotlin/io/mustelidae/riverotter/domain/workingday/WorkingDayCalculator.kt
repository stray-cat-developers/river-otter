package io.mustelidae.riverotter.domain.workingday

import io.mustelidae.riverotter.common.Error
import io.mustelidae.riverotter.common.ErrorCode
import io.mustelidae.riverotter.config.SystemException
import io.mustelidae.riverotter.domain.calendar.holiday.Holiday
import io.mustelidae.riverotter.utils.searchIndex
import io.mustelidae.riverotter.utils.sort
import java.time.LocalDate

class WorkingDayCalculator(
    private val holidays: MutableList<Holiday>,
) {

    fun calculate(date: LocalDate, businessDays: Int): LocalDate {
        holidays.sort()

        var workDay = businessDays
        var plusDay: Long = 0

        while (workDay > 0) {
            val day = date.plusDays(plusDay)
            val isHoliday = (holidays.searchIndex(day) >= 0)

            if (isHoliday) {
                plusDay++
                continue
            }

            if (workDay == 1) {
                return day
            }

            plusDay++
            workDay--
        }
        throw SystemException(Error(ErrorCode.PD01, "An error occurred in the business day calculator."))
    }
}

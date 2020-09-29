package io.mustelidae.riverotter.domain.calendar.api

import io.mustelidae.riverotter.domain.calendar.yeartable.YearTable
import io.swagger.annotations.ApiModel
import java.util.Locale

class YearTableResources {

    class Reply {

        @ApiModel("YearTable.Reply.Table")
        data class Table(
            val id: String,
            val locale: Locale,
            val years: List<Year>
        ) {

            @ApiModel("YearTable.Reply.Table.Year")
            data class Year(
                val year: Int,
                var calendarId: String
            ) {
                companion object {
                    fun from(yearOfLocale: YearTable.YearOfLocale): Year {
                        return Year(
                            yearOfLocale.year,
                            yearOfLocale.id.toString()
                        )
                    }
                }
            }

            companion object {
                fun from(yearTable: YearTable): Table {
                    val years = yearTable.yearsOfLocale
                        .map { Year.from(it) }
                    return Table(
                        yearTable.id.toString(),
                        yearTable.locale,
                        years
                    )
                }
            }
        }
    }
}

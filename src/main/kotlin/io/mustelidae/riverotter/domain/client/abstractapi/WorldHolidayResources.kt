package io.mustelidae.riverotter.domain.client.abstractapi

class WorldHolidayResources {

    class Reply {
        class Holidays : ArrayList<Holidays.HolidayItem>() {
            data class HolidayItem(
                val country: String,
                val date: String,
                val date_day: String,
                val date_month: String,
                val date_year: String,
                val description: String,
                val is_observed: Boolean,
                val language: String,
                val location: String,
                val name: String,
                val name_local: String,
                val state: String,
                val type: String,
                val week_day: String
            )
        }
    }
}

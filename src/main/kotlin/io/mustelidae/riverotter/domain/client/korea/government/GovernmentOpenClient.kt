package io.mustelidae.riverotter.domain.client.korea.government

interface GovernmentOpenClient {

    fun findAllHoliday(year: Int): GovernmentOpenResources.Reply.Holiday
}

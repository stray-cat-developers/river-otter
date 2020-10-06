package io.mustelidae.riverotter.utils

import io.mustelidae.riverotter.common.Replies
import io.mustelidae.riverotter.common.Reply
import io.mustelidae.riverotter.domain.calendar.holiday.Holiday
import java.time.LocalDate
import java.time.ZoneOffset

fun <T> List<T>.toReplies(): Replies<T> = Replies(this)
fun <T> T.toReply(): Reply<T> = Reply(this)

fun List<Holiday>.searchIndex(localDate: LocalDate): Int {
    val time = localDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC)
    return this.binarySearch { timeComparison(it, time) }
}

private fun timeComparison(holiday: Holiday, time: Long) = (holiday.time - time).toInt()

fun MutableList<Holiday>.sort() {
    this.sortBy { it.time }
}

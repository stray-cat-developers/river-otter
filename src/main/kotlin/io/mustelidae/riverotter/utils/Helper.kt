package io.mustelidae.riverotter.utils

import io.mustelidae.riverotter.common.Replies
import io.mustelidae.riverotter.common.Reply

fun <T> List<T>.toReplies(): Replies<T> = Replies(this)
fun <T> T.toReply(): Reply<T> = Reply(this)

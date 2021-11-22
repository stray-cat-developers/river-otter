package io.mustelidae.riverotter.domain.topic

import org.bson.types.ObjectId

internal class TopicTest

fun Topic.Companion.aFixture(): Topic {
    return Topic(ObjectId(), "Local test topic")
}

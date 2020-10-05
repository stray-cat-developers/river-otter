package io.mustelidae.riverotter.domain.topic.repository

import io.mustelidae.riverotter.domain.topic.Topic
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface TopicRepository : MongoRepository<Topic, ObjectId>

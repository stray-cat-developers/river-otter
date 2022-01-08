package io.mustelidae.riverotter.config

import com.mongodb.ConnectionString
import com.mongodb.client.MongoClients
import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodProcess
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfig
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Lazy
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component
import java.io.IOException
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import kotlin.random.Random

@Lazy(false)
@Import(value = [EmbeddedMongoAutoConfiguration::class])
@Component
@EnableConfigurationProperties(value = [MongoProperties::class])
class DefaultEmbeddedMongo(
    private val mongoProperties: MongoProperties
) {

    lateinit var mongoExecutable: MongodExecutable
    lateinit var mongoProcess: MongodProcess
    private val starter = MongodStarter.getDefaultInstance()
    var port: Int = Random.nextInt(27000, 27999)

    @PostConstruct
    fun startup() {
        val builder = MongodConfig.builder()
            .version(Version.Main.PRODUCTION)
            .net(Net(mongoProperties.host, port, Network.localhostIsIPv6()))
            .build()
        this.mongoExecutable = starter.prepare(builder)
        this.mongoProcess = this.mongoExecutable.start()
    }

    @Bean
    @Throws(IOException::class)
    fun mongoTemplate(): MongoTemplate {
        val mongoClient = MongoClients.create(ConnectionString("mongodb://${mongoProperties.host}:$port"))
        return MongoTemplate(mongoClient, mongoProperties.database)
    }

    @PreDestroy
    fun shutdown() {
        mongoProcess.stop()
        mongoExecutable.stop()
    }
}

package io.mustelidae.riverotter.common

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import org.springframework.util.Assert
import java.util.ArrayList
import java.util.Collections

@JsonRootName(value = "entities")
open class Replies<T>
constructor(content: Iterable<T>) : Iterable<T> {

    private val content: MutableCollection<T>?

    constructor() : this(ArrayList<T>())

    init {
        Assert.notNull(content, "Content must not be null!")

        this.content = ArrayList()

        for (element in content) {
            this.content.add(element)
        }
    }

    @JsonProperty("content")
    open fun getContent(): Collection<T> {
        return Collections.unmodifiableCollection(content!!)
    }

    override fun iterator(): Iterator<T> {
        return content!!.iterator()
    }

    override fun toString(): String {
        return String.format("Resources { content: %s, %s }", getContent(), super.toString())
    }

    override fun equals(other: Any?): Boolean {

        if (other === this) {
            return true
        }

        if (other == null || other.javaClass != javaClass) {
            return false
        }

        val that = other as Replies<*>?

        val contentEqual = if (this.content == null) that!!.content == null else this.content == that!!.content
        return if (contentEqual) super.equals(other) else contentEqual
    }

    override fun hashCode(): Int {

        var result = super.hashCode()
        result += if (content == null) 0 else 17 * content.hashCode()

        return result
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun <T : Reply<S>, S> wrap(content: Iterable<S>): Replies<T> {

            Assert.notNull(content, "Content must not be null!")
            val resources = ArrayList<T>()

            for (element in content) {
                resources.add(Reply(element) as T)
            }

            return Replies(resources)
        }
    }
}

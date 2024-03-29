package com.example.trenirovochka.domain.common

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 * Usually used with [SingleEventObserver]
 *
 * [Read more about this.](https://medium.com/google-developers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150)
 */
open class SingleEvent<out T>(private val content: T) {

    var consumed = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     * @return The unconsumed content or `null` if it was consumed already.
     */
    fun getNotConsumedContent(): T? {
        return if (consumed) {
            null
        } else {
            consumed = true
            content
        }
    }

    /**
     * @return The content whether it's been handled or not.
     */
    fun getContent(): T = content

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SingleEvent<*>

        if (content != other.content) return false
        if (consumed != other.consumed) return false

        return true
    }

    override fun hashCode(): Int {
        var result = content?.hashCode() ?: 0
        result = 31 * result + consumed.hashCode()
        return result
    }
}
package dev.essential.kevent

import dev.essential.kevent.internal.HandlerDefinition
import java.util.WeakHashMap
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.reflect.KClass

/**
 * Manages registrations and dispatching.
 */
class EventBus {

    private val handlers = CopyOnWriteArrayList<HandlerDefinition<*>>()
    
    /**
     * Exception handler for errors thrown by event handlers.
     * By default, prints stack trace and continues.
     */
    var exceptionHandler: (Exception, Event) -> Unit = { exception, _ ->
        exception.printStackTrace()
    }

    /**
     * Registers all handlers from the given listener.
     * If the listener is already registered, this method does nothing.
     *
     * @param listener The listener to register
     */
    fun register(listener: EventListener) {
        if (handlers.any { it.listener === listener }) {
            return
        }
        
        handlers += listenerHandlers[listener] ?: emptyList()
        handlers.sortBy { it.priority.ordinal }
    }

    /**
     * Unregisters all handlers from the given listener.
     *
     * @param listener The listener to unregister
     */
    fun unregister(listener: EventListener) {
        handlers.removeAll { it.listener === listener }
    }

    /**
     * Posts an event to all registered handlers.
     * Handlers are invoked in priority order.
     *
     * @param event The event to post
     * @return The same event
     */
    fun <E : Event> post(event: E): E {
        val eventType = event::class

        for (handler in handlers) {
            if (!isMatchingHandler(handler, eventType)) continue

            try {
                @Suppress("UNCHECKED_CAST")
                (handler.handler as (E) -> Unit).invoke(event)
            } catch (e: Exception) {
                exceptionHandler(e, event)
            }
        }

        return event
    }

    private fun isMatchingHandler(
        handler: HandlerDefinition<*>,
        eventType: KClass<out Event>
    ): Boolean {
        return handler.eventType == eventType ||
               handler.eventType.java.isAssignableFrom(eventType.java)
    }
    
    companion object {
        /**
         * Internal storage for event handlers associated with each listener.
         * Uses WeakHashMap to prevent memory leaks when listeners are garbage collected.
         */
        @PublishedApi
        internal val listenerHandlers = WeakHashMap<EventListener, MutableList<HandlerDefinition<*>>>()
    }
}
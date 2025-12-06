package dev.essential.kevent

import dev.essential.kevent.internal.HandlerDefinition

/**
 * Base class for objects that handle events.
 * Define handlers as properties using [on].
 *
 * Usage:
 * ```
 * class MyListener : EventListener() {
 *     private val handler = on<MyEvent> { event ->
 *         println(event.data)
 *     }
 * }
 * ```
 */
abstract class EventListener {

    @PublishedApi
    internal val handlers = mutableListOf<HandlerDefinition<*>>()

    /**
     * Registers an event handler for events of type [E].
     *
     * @param E The event type to listen for
     * @param priority Execution order relative to other handlers
     * @param handler Function invoked when the event is posted
     */
    protected inline fun <reified E : Event> on(
        priority: EventPriority = EventPriority.NORMAL,
        noinline handler: (E) -> Unit
    ) {
        handlers += HandlerDefinition(
            listener = this,
            eventType = E::class,
            priority = priority,
            handler = handler
        )
    }
}
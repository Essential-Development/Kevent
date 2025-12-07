package dev.essential.kevent

import dev.essential.kevent.internal.HandlerDefinition

/**
 * Interface for objects that handle events.
 * Define handlers as properties using [on].
 *
 * Usage:
 * ```
 * class MyListener : EventListener {
 *     private val handler = on<MyEvent> { event ->
 *         println(event.data)
 *     }
 * }
 * ```
 */
interface EventListener

/**
 * Registers an event handler for events of type [E].
 *
 * @param E The event type to listen for
 * @param priority Execution order relative to other handlers
 * @param handler Function invoked when the event is posted
 */
inline fun <reified E : Event> EventListener.on(
    priority: EventPriority = EventPriority.NORMAL,
    noinline handler: (E) -> Unit
) {
    val handlers = EventBus.listenerHandlers.getOrPut(this) { mutableListOf() }
    handlers += HandlerDefinition(
        listener = this,
        eventType = E::class,
        priority = priority,
        handler = handler
    )
}
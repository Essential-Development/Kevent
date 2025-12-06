package dev.essential.kevent.internal

import dev.essential.kevent.Event
import dev.essential.kevent.EventListener
import dev.essential.kevent.EventPriority
import kotlin.reflect.KClass

/**
 * Represents a registered event handler.
 * Created when [EventListener.on] is called.
 *
 * @param E The event type this handler accepts
 * @property listener The [EventListener] that owns this handler
 * @property eventType The class of events this handler responds to
 * @property priority Execution order relative to other handlers
 * @property handler The function invoked when a matching event is posted
 */
@PublishedApi
internal class HandlerDefinition<E : Event>(
    val listener: EventListener,
    val eventType: KClass<E>,
    val priority: EventPriority,
    val handler: (E) -> Unit
)
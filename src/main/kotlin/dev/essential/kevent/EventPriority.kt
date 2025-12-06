package dev.essential.kevent

/**
 * Execution priority for event handlers.
 * Handlers are invoked from [LOWEST] to [HIGHEST].
 */
enum class EventPriority {
    /** First to execute.*/
    LOWEST,

    LOW,

    /** Default priority. */
    NORMAL,

    HIGH,

    /** Last to execute.*/
    HIGHEST,
}
# Keven(t)

**A lightweight, synchronous event bus for Kotlin**

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2+-purple.svg)](https://kotlinlang.org/)

Kevent is a simple, synchronous event bus for Kotlin. Zero dependencies, priority-based handlers, and a clean DSL for defining listeners.

---

## ✨ Features

- 🚀 **Zero dependencies** — no bloat, just events
- 🎯 **Type-safe** — full Kotlin type system support
- ⚡ **Synchronous** — predictable execution order
- 🎚️ **Priority-based** — control handler execution order
- 🧩 **Event hierarchies** — handle parent and child events
- 🎨 **Clean DSL** — intuitive listener syntax

---

## 🚀 Quick Start

### Installation

Add JitPack repository and Kevent dependency to your `build.gradle.kts`:

```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.Essential-Development:kevent:1.0.0")
}
```

> 💡 **Note:** Replace `1.0.0` with the [latest release](https://github.com/Essential-Development/kevent/releases) version.

### Define Events

```kotlin
import dev.essential.kevent.Event

// Simple event
data class KeyPressedEvent(val key: String) : Event

// Cancellable event
data class CatOnKeyboardEvent(
val mash: String,
var sent: Boolean = false
) : Event
```

### Create a Listener

```kotlin
import dev.essential.kevent.EventListener
import dev.essential.kevent.EventPriority
import dev.essential.kevent.on

class CatMessenger : EventListener {
    
    private val onKey = on<KeyPressedEvent> { event ->
        println("Typing: ${event.key}")
    }
    
    private val onCat = on<CatOnKeyboardEvent>(EventPriority.HIGH) { event ->
        println("Cat wrote: ${event.mash}")
        event.sent = true // clearly important message
    }
}
```

### Register and Post Events

```kotlin
import dev.essential.kevent.EventBus

val eventBus = EventBus()
val listener = CatMessenger()

// Start listening
eventBus.register(listener)

// Post events
eventBus.post(KeyPressedEvent("H"))
eventBus.post(KeyPressedEvent("I"))

// Cat appears on keyboard
val event = eventBus.post(CatOnKeyboardEvent("jjjjjjjjjjjjjjjjjjjjjjjjjjj"))
if (event.sent) {
// Message was sent
}

// Stop listening
eventBus.unregister(listener)
```

---

## 📖 Documentation

### Priority

Handlers execute from `LOWEST` to `HIGHEST`:

| Priority  | Description      |
|-----------|------------------|
| `LOWEST`  | Runs first       |
| `LOW`     | Early processing |
| `NORMAL`  | Default priority |
| `HIGH`    | Important logic  |
| `HIGHEST` | Runs last        |

```kotlin
class PriorityExample : EventListener {

    private val first = on<MyEvent>(EventPriority.LOWEST) { 
        println("I run first")
    }

    private val last = on<MyEvent>(EventPriority.HIGHEST) { 
        println("I run last")
    }
}
```

### Event Hierarchies

Handlers respond to exact types and subtypes:

```kotlin
sealed class ChatEvent : Event {
    data class Message(val user: String, val text: String) : ChatEvent()
    data class Join(val user: String) : ChatEvent()
    data class Leave(val user: String) : ChatEvent()
}

class ChatListener : EventListener {

    // Log all chat activity
    private val onAnyChatEvent = on<ChatEvent> { event ->
        chatHistory.append(event)
    }

    // Welcome new users
    private val onJoinChatEvent = on<ChatEvent.Join> { event ->
        println("${event.user} joined the chat!")
    }
}
```

### Error Handling

Customize how exceptions are handled:

```kotlin
eventBus.exceptionHandler = { exception, event ->
    logger.error("Failed to handle $event", exception)
}
```

---

## 🤝 Contributing

Contributions are welcome! Whether you're:

- 🐛 **Reporting bugs** — Help us improve by reporting issues
- 💡 **Suggesting features** — Share your ideas
- 📝 **Improving documentation** — Help make Kevent easier to use
- 🔧 **Submitting code** — Contribute fixes or features

---

## 📄 License

Kevent is licensed under the [MIT License](LICENSE).
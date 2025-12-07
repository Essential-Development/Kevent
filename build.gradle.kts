plugins {
    kotlin("jvm") version "2.2.0"
    `maven-publish`
}

group = "com.github.Essential-Development"
version = "1.0.2"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            // Add metadata for better repository compatibility
            pom {
                name.set("Kevent")
                description.set("A lightweight, synchronous event bus for Kotlin")
                url.set("https://github.com/Essential-Development/kevent")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer {
                        id.set("Essential-Development")
                        name.set("Essential Development")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/Essential-Development/kevent.git")
                    developerConnection.set("scm:git:ssh://github.com:Essential-Development/kevent.git")
                    url.set("https://github.com/Essential-Development/kevent")
                }
            }
        }
    }
}

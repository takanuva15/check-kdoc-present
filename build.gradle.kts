import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.4.32"
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
}

group = "me.takanuva15"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("MainKt")
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    api("com.github.kotlinx.ast:grammar-kotlin-parser-antlr-kotlin-jvm:816803892c")
}

kotlin {
    sourceSets {
        val main by getting {
            dependencies {
                api("com.github.kotlinx.ast:grammar-kotlin-parser-antlr-kotlin:816803892c")
            }
        }
    }
}

ktlint {
    enableExperimentalRules.set(true)
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    withType<Jar> {
        manifest {
            attributes["Main-Class"] = application.mainClass
        }
        exclude("**/module-info.class")
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    }

    test {
        useJUnitPlatform()
    }
}

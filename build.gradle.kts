import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.github.johnrengelman.shadow").version("7.1.2")
    id("java")
    kotlin("jvm") version "1.8.21"
    kotlin("kapt") version "1.8.21"
    kotlin("plugin.serialization") version "1.4.20"
    id("xyz.jpenilla.run-paper").version("2.1.0")
    id("xyz.jpenilla.run-waterfall").version("2.1.0")
    id("xyz.jpenilla.run-velocity").version("2.1.0")
}

group = "io.github.bluesheep2804"
version = "0.1.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    // Spigot
    compileOnly("org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")
    implementation("net.kyori:adventure-platform-bukkit:4.3.0")

    //BungeeCord
    compileOnly("net.md-5:bungeecord-api:1.20-R0.1-SNAPSHOT")
    implementation("net.kyori:adventure-platform-bungeecord:4.3.0")

    // Velocity
    compileOnly("com.velocitypowered:velocity-api:3.2.0-SNAPSHOT")
    kapt("com.velocitypowered:velocity-api:3.2.0-SNAPSHOT")

    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("com.charleskorn.kaml:kaml:0.54.0")
    implementation("net.kyori:adventure-text-minimessage:4.14.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
    runServer {
        minecraftVersion("1.20.1")
        runDirectory(File("run_paper"))
    }
    runVelocity {
        velocityVersion("3.2.0-SNAPSHOT")
        runDirectory(File("run_velocity"))
    }
    runWaterfall {
        waterfallVersion("1.20")
        runDirectory(File("run_waterfall"))
    }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
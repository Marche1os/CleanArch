plugins {
    kotlin("jvm") version "2.0.0"
}

group = "ru.marche1os.turingmachine"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(18)
}
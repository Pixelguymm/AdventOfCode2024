plugins {
    kotlin("jvm") version "2.0.21"
}

group = "me.pixelguy"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(20)
}
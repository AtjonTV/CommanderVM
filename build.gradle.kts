import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.2.51"
    application
}

group = "at.atvg_studios.gitlab"
// The Version is defined as Year-Month-Day<-Revision> (Revision is optional)
version = "18.4.19"

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    applicationName = rootProject.name
    mainClassName = "${project.group}.CommanderKt"
}
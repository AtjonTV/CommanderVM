import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.2.51"
    application
}

group = "at.atvg_studios.gitlab"
// The Version is defined as Year-Month-Day<-Revision> (Revision is optional)
version = "18.7.26.1"
var build = getBuildCode()

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("com.google.code.gson:gson:2.8.5")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    applicationName = rootProject.name
    mainClassName = "${project.group}.CommanderKt"
}

val fatJar = task("fatJar", type = Jar::class) {
    baseName = "${project.name}VM"
    manifest {
        attributes["Main-Class"] = "${project.group}.CommanderKt"
        attributes["Implementation-Version"] = "$version"
        attributes["Implementation-Build"] = "$build"
        attributes["Implementation-Date"] = System.currentTimeMillis()
    }
    from(
            configurations.runtime.map {
                if (it.isDirectory) it else zipTree(it)
            }
    )
    with(tasks["jar"] as CopySpec)
}
tasks {
    "build" {
        dependsOn(fatJar)
    }
}

fun getBuildCode(): Long
{
    var z = File("build.v").readText().toLong()
    z++
    File("build.v").writeText(z.toString())
    return z--
}
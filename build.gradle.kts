import cl.franciscosolis.sonatypecentralupload.SonatypeCentralUploadTask
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.0.21"
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.+"
    id("cl.franciscosolis.sonatype-central-upload") version "1.0.2"
}

group = "com.tksimeji"
version = "0.0.2"

java {
    withSourcesJar()
    withJavadocJar()
}

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))

    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
}

publishing {
    publications {
        register<MavenPublication>("maven") {
            pom {
                name.set("tksimeji")
                description.set("A Mojang API client for Java and Kotlin")
                url.set("https://github.com/tksimeji/mojango")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/tksimeji/mojango/blob/master/LICENSE")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("tksimeji")
                        name.set("tksimeji")
                        email.set("tksimeji@outlook.com")
                    }
                }
                scm {
                    url.set("https://github.com/tksimeji/mojango")
                }
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set("")
}

tasks.named<SonatypeCentralUploadTask>("sonatypeCentralUpload") {
    dependsOn("shadowJar", "sourcesJar", "javadocJar", "generatePomFileForMavenPublication")

    username = System.getenv("SONATYPE_CENTRAL_USERNAME")
    password = System.getenv("SONATYPE_CENTRAL_PASSWORD")

    archives = files(
        tasks.named("shadowJar"),
        tasks.named("sourcesJar"),
        tasks.named("javadocJar"),
    )

    pom = file(
        tasks.named("generatePomFileForMavenPublication").get().outputs.files.single()
    )

    signingKey.set(File("secret.asc").readText())
    signingKeyPassphrase = System.getenv("PGP_SIGNING_KEY_PASSPHRASE")
}

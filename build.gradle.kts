import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.3.50"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    `java-library`
    `build-scan`
    `maven-publish`
}

val springBootVersion = "2.2.1.RELEASE"

group = "br.com.felipempantoja"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val developmentOnly by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-autoconfigure:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-configuration-processor:$springBootVersion")

    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:$springBootVersion") {
        because("we need to process HTML template files for sending the emails")
    }
    implementation("org.springframework.boot:spring-boot-starter-mail:$springBootVersion")

    developmentOnly("org.springframework.boot:spring-boot-devtools:$springBootVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=compatibility")
        jvmTarget = "1.8"
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

buildScan {
    // always accept the terms of service
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"

    // always publish a build scan ($ ./gradlew build --scan)
    publishAlways()
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(sourcesJar.get())
            pom {
                name.set("Mail Sender")
                description.set("A customizable way of sending emails")
                url.set("http://gitlab.mprj.mp.br/gsi-mailer")
                developers {
                    developer {
                        id.set("felipempantoja")
                        name.set("Felipe Pantoja")
                        email.set("felipempantoja@gmail.com")
                    }
                }
//                scm {
//                    connection.set("scm:git:git://example.com/my-library.git")
//                    developerConnection.set("scm:git:ssh://example.com/my-library.git")
//                    url.set("http://example.com/my-library/")
//                }
            }
        }
    }
    repositories {
        maven {
//            val releasesRepoUrl = uri("$buildDir/repos/releases")
//            val snapshotsRepoUrl = uri("$buildDir/repos/snapshots")
//            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            url = uri("file://${System.getProperty("user.home")}/.m2/repository")
        }
    }

}

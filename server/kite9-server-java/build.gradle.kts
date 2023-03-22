/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    id("org.kite9.java-conventions")
    kotlin("multiplatform") version "1.8.10"
}

kotlin {
    jvm()
}


dependencies {
    api(project(":kite9-visualization-java"))
    api(project(":kite9-pipeline-common"))
    api("org.kohsuke:github-api:1.306")
    api("org.springframework.boot:spring-boot-starter-actuator:2.7.0")
    api("org.springframework.boot:spring-boot-starter-hateoas:2.7.0")
    api("org.springframework.boot:spring-boot-starter-web:2.7.0")
    api("org.springframework.boot:spring-boot-starter-oauth2-client:2.7.0")
    api("org.springframework.boot:spring-boot-starter-webflux:2.7.0")
    api("org.springframework.boot:spring-boot-starter-websocket:2.7.0")
    api("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.3")
    api("net.sf.saxon:Saxon-HE:10.5")
    api("org.xmlunit:xmlunit-core:2.9.0")
    api("org.webjars:bootstrap:4.3.1")
    api("org.webjars.npm:codemirror:5.58.3")
    api("org.webjars:highlightjs:9.6.0")
    api("org.webjars.npm:hint.css:2.3.2")
    api("org.webjars.npm:kotlin:1.4.30")
    api(project(":kite9-visualization-js"))
    testImplementation("org.springframework.security:spring-security-test:5.7.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.0")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

description = "Kite9 Server (Spring-Boot)"
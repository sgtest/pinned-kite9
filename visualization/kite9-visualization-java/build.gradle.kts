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
    api(project(":kite9-visualization-common"))
    api("org.apache.xmlgraphics:batik-svggen:1.14")
    api("org.apache.xmlgraphics:batik-transcoder:1.14")
    api("org.apache.xmlgraphics:batik-bridge:1.14")
    api("org.apache.xmlgraphics:batik-ext:1.14")
    api("org.apache.xmlgraphics:xmlgraphics-commons:2.6")
    api("org.apache.xmlgraphics:batik-rasterizer:1.14")
    api("org.apache.xmlgraphics:batik-codec:1.14")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.xmlunit:xmlunit-core:2.1.0")
}

description = "Kite9 Visualization Java Batik (Server-Side)"
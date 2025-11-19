// build.gradle.kts (Versión estable y completa)

plugins {
    // Plugins de Kotlin y JPA
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.jpa") version "1.9.25"

    // Plugins de Spring Boot (Usamos la versión 3.3.1, que es estable)
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.duocuc.tienda"
version = "0.0.1-SNAPSHOT"
description = "Pokemon API Backend"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17) // O JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // --- DEPENDENCIAS CORE ---
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security") // PARA ROL ADMIN

    // Conector de MySQL (para AWS RDS)
    runtimeOnly("com.mysql:mysql-connector-j")

    // --- DEPENDENCIAS DE KOTLIN ---
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // --- DEPENDENCIAS DE UTILIDADES Y DOCUMENTACIÓN ---
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Spring Boot DevTools (para reinicios rápidos)
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // Springdoc OpenAPI (Swagger) - OBLIGATORIO para documentar
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    // --- DEPENDENCIAS DE PRUEBA ---
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

// Configuración de Kotlin para que JPA pueda hacer su magia
allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
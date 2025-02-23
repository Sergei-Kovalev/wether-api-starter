plugins {
	java
	id("org.springframework.boot") version "3.4.3"
	id("io.spring.dependency-management") version "1.1.7"
	id("io.freefair.lombok") version "8.12.1"
}

group = "jdev.kovalev"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

val mapstructVersion = "1.6.3"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
	implementation ("org.mapstruct:mapstruct:${mapstructVersion}")
	annotationProcessor ("org.mapstruct:mapstruct-processor:${mapstructVersion}")

}

tasks.withType<Test> {
	useJUnitPlatform()
}

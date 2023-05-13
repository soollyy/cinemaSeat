import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.6"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation ("org.springframework.boot:spring-boot-starter-jdbc")
	implementation ("com.h2database:h2")
	implementation("org.testng:testng:7.1.0")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation ("io.jsonwebtoken:jjwt:0.2")
	implementation ("javax.xml.bind:jaxb-api:2.3.1")
	implementation("javax.xml.bind:jaxb-api:2.3.1")
	implementation("com.sun.xml.bind:jaxb-impl:2.3.2")
	implementation("javax.activation:activation:1.1.1")
////	implementation(kotlin("stdlib-jdk8"))
//	implementation("io.jsonwebtoken:jjwt-api:0.11.2")
//	implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
//	implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")
//	implementation("io.ktor:ktor-server-auth")
//	implementation("io.ktor:ktor-server-auth-jwt")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

import org.gradle.accessors.dm.LibrariesForLibs

plugins {
	id("server_stats.base")
	id("server_stats.java")
	id("com.gradleup.shadow")
}

val libs = the<LibrariesForLibs>()

repositories {
	mavenCentral()
	maven {
		name = "Fabric"
		url = uri("https://maven.fabricmc.net/")
	}
	maven {
		name = "Ornithe"
		url = uri("https://maven.ornithemc.net/releases")
	}
	maven {
		name = "Quilt"
		url = uri("https://maven.quiltmc.org/repository/release")
	}
}

dependencies {
	compileOnly(libs.gson)
	compileOnly(libs.annotations)
	implementation(libs.quilt.loader)
}

tasks.shadowJar {
	configurations.add(project.configurations.shadow)
	relocate("com.google.gson", "net.lostluma.server_stats.external.gson")
}

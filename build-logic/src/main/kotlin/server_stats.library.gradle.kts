import org.gradle.accessors.dm.LibrariesForLibs

plugins {
	id("server_stats.base")
	id("server_stats.java")
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
	implementation(libs.gson)
	implementation(libs.slf4j)
	implementation(libs.annotations)
	implementation(libs.fabric.loader)
}

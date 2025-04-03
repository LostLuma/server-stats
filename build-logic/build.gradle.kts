plugins {
	`kotlin-dsl`
}

repositories {
	maven {
		name = "Fabric"
		url = uri("https://maven.fabricmc.net/")
	}
	maven {
		name = "Ornithe Releases"
		url = uri("https://maven.ornithemc.net/releases")
	}
	maven {
		name = "Quilt"
		url = uri("https://maven.quiltmc.org/repository/release")
	}
	gradlePluginPortal()
}

dependencies {
	implementation(libs.shadow)
	implementation(libs.ploceus)
	implementation(libs.quilt.loom)

	// Enable using version catalog in local plugins
	// https://github.com/gradle/gradle/issues/15383
	implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

kotlin {
	jvmToolchain(21)
}

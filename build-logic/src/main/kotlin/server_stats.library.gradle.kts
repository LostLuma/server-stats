import org.gradle.accessors.dm.LibrariesForLibs

plugins {
	id("server_stats.base")
	id("server_stats.java")
}

val libs = the<LibrariesForLibs>()

repositories {
	fun exclusiveRepository(title: String, uri: String, pattern: String) {
		exclusiveContent {
			forRepository {
				maven {
					name = title
					url = uri(uri)
				}
			}
			filter {
				includeGroupByRegex(pattern)
			}
		}
	}

	mavenCentral()

	exclusiveRepository("Fabric", "https://maven.fabricmc.net", "net\\.fabricmc.*")
	exclusiveRepository("Ornithe", "https://maven.ornithemc.net/releases", "net\\.ornithemc.*|io\\.github\\.gaming32.*")
	exclusiveRepository("Quilt", "https://maven.quiltmc.org/repository/release", "org.quiltmc.*")
}

dependencies {
	implementation(libs.gson)
	implementation(libs.slf4j)
	implementation(libs.annotations)
	implementation(libs.fabric.loader)
}

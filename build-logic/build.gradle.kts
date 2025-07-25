plugins {
	`kotlin-dsl`
}

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

	exclusiveRepository("Fabric", "https://maven.fabricmc.net", "net\\.fabricmc.*")
	exclusiveRepository("Ornithe", "https://maven.ornithemc.net/releases", "net\\.ornithemc.*|io\\.github\\.gaming32.*")
	exclusiveRepository("Quilt", "https://maven.quiltmc.org/repository/release", "org.quiltmc.*")

	gradlePluginPortal()
}

dependencies {
	implementation(libs.ploceus)
	implementation(libs.fabric.loom)

	// Enable using version catalog in local plugins
	// https://github.com/gradle/gradle/issues/15383
	implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

kotlin {
	jvmToolchain(21)
}

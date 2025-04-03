import org.gradle.accessors.dm.LibrariesForLibs

plugins {
	id("org.quiltmc.loom")
	id("ploceus")
	id("server_stats.library")
}

val libs = the<LibrariesForLibs>()
val isMerged = !project.hasProperty("environment")
val minecraftVersion = project.property("minecraft_version");

loom {
	mixin {
		useLegacyMixinAp.set(false)
	}
}

if (!isMerged) {
	val isClient = project.property("environment") == "client"

	ploceus {
		if (isClient) {
			clientOnlyMappings()
		} else {
			serverOnlyMappings()
		}
	}

	loom {
		if (isClient) {
			clientOnlyMinecraftJar()
		} else {
			serverOnlyMinecraftJar()
		}
	}
}

dependencies {
	modImplementation(libs.quilt.loader)
	minecraft("com.mojang:minecraft:${minecraftVersion}")

	if (project.hasProperty("nests_build")) {
		nests(ploceus.nests(project.property("nests_build").toString()))
	}
	mappings(ploceus.featherMappings(project.property("feather_build").toString()))
}

tasks.remapJar {
	dependsOn(tasks.shadowJar)
	inputFile.set(tasks.shadowJar.get().archiveFile)
}

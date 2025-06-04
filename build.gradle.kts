import me.modmuss50.mpp.ReleaseType
import net.fabricmc.loom.task.RemapJarTask

plugins {
	id("server_stats.module")
	alias(libs.plugins.mod.publish)
}

dependencies {
	include(project(":module:api"))
	include(project(":module:impl"))

	include(project(":module:bugfix:drop-amount:1.0.0-beta.1.5.0-to-1.2.0-alpha.12w08a"))
	include(project(":module:bugfix:drop-amount:1.2.0-to-1.7.0-alpha.13w37b"))
	include(project(":module:bugfix:drop-amount:1.7.0-alpha.13w38a-to-1.8.0-alpha.14w08a"))
	include(project(":module:bugfix:drop-amount:1.8.0-alpha.14w10a-to-1.8.0-alpha.14w28b"))
	include(project(":module:bugfix:drop-amount:1.8.0-alpha.14w29a-to-1.12.2"))
	include(project(":module:bugfix:jump"))
	include(project(":module:bugfix:movement"))
	include(project(":module:bugfix:result-amount"))

	include(project(":module:dfu"))

	include(project(":module:gui:large-stats:1.0.0-beta.1.5.0-to-1.2.3"))
	include(project(":module:gui:large-stats:1.2.4-to-1.5.2"))
	include(project(":module:gui:large-stats:1.6.0-alpha.13w16a-to-1.6.4"))

	include(project(":module:gui:mob-stats:1.0.0-beta.1.5.0-to-1.5.2"))
	include(project(":module:gui:mob-stats:1.6.0-alpha.13w16a-to-1.6.4"))

	include(project(":module:gui:util:1.0.0-beta.1.5.0-to-1.0.0-beta.1.8.1"))
	include(project(":module:gui:util:1.0.0-to-1.3.0-alpha.12w17a"))
	include(project(":module:gui:util:1.3.2-to-1.5.2"))
	include(project(":module:gui:util:1.6.0-alpha.13w16a-to-1.6.0-alpha.13w17a"))
	include(project(":module:gui:util:1.6.0-alpha.13w18a-to-1.6.0-alpha.13w23b"))
	include(project(":module:gui:util:1.6.0-alpha.13w24a-to-1.6.4"))

	include(project(":module:identity:client"))
	include(project(":module:identity:merged:1.3.2-to-1.5.2"))
	include(project(":module:identity:merged:1.6.0-alpha.13w16a-to-1.6.4"))
	include(project(":module:identity:merged:1.7.0-alpha.13w36a-to-1.7.0-alpha.13w38c"))
	include(project(":module:identity:merged:1.7.0-alpha.13w39a-to-1.7.5"))
	include(project(":module:identity:server"))

	include(project(":module:lifecycle:client:1.0.0-beta.1.5.0-to-1.0.0-beta.1.7.3"))
	include(project(":module:lifecycle:client:1.0.0-beta.1.8.0-to-1.3.0-alpha.12w17a"))

	include(project(":module:lifecycle:merged:1.3.2-to-1.4.0-alpha.12w38b"))
	include(project(":module:lifecycle:merged:1.4.0-alpha.12w39a-to-1.6.4"))

	include(project(":module:lifecycle:server:1.0.0-beta.1.5.0-to-1.1.0-alpha.11w50a"))
	include(project(":module:lifecycle:server:1.1.0-alpha.12w01a-to-1.3.0-alpha.12w17a"))

	include(project(":module:network:osl:client"))
	include(project(":module:network:osl:common"))
	include(project(":module:network:osl:server"))

	include(project(":module:network:vanilla:client"))
	include(project(":module:network:vanilla:common"))
	include(project(":module:network:vanilla:merged:1.3.2-to-1.5.2"))
	include(project(":module:network:vanilla:merged:1.6.0-alpha.13w16a-to-1.6.4"))
	include(project(":module:network:vanilla:server"))

	// Code for all versions without server-side statistics
	include(project(":module:shared:client"))
	include(project(":module:shared:common"))
	include(project(":module:shared:server"))

	include(project(":module:statistic:combat:1.0.0-beta.1.5.0-to-1.1.0-alpha.11w48a"))
	include(project(":module:statistic:combat:1.1.0-alpha.11w49a-to-1.1.0-alpha.11w50a"))
	include(project(":module:statistic:combat:1.1.0-alpha.12w01a-to-1.6.4"))

	include(project(":module:util"))

	// b1.5.0 -> first version with statistics!
	include(project(":module:version:1.0.0-beta.1.5.0-to-1.0.0-beta.1.7.3-client"))
	include(project(":module:version:1.0.0-beta.1.5.0-to-1.0.0-beta.1.5.2-server"))
	// b1.6.0 -> changed PlayerManager.respawn signature
	include(project(":module:version:1.0.0-beta.1.6.0-to-1.0.0-beta.1.7.3-server"))

	// b1.8.0 -> added DamageSource, Minecraft.startGame signature changed
	include(project(":module:version:1.0.0-beta.1.8.0-to-1.0.0-beta.1.8.1-client"))
	include(project(":module:version:1.0.0-beta.1.8.0-to-1.0.0-beta.1.8.1-server"))

	// Minecraft.changeDimension, PlayerManager.respawn signature changed
	include(project(":module:version:1.0.0-to-1.3.0-alpha.12w17a-client"))
	include(project(":module:version:1.0.1-to-1.3.0-alpha.12w17a-server"))
	// The rest of the 1.3 snapshots are very weird
	// Because Mojang was working on the client / server merge
	include(project(":module:version:1.3.2-to-1.6.4"))
}

tasks.withType<Jar> {
	from("LICENSE")
}

val modVersion = project.property("mod_version").toString()

fun getVersionType(): ReleaseType {
	return if (modVersion.startsWith("0.") || modVersion.contains("-alpha.")) {
		ReleaseType.ALPHA
	} else if (modVersion.contains("-")) {
		ReleaseType.BETA
	} else {
		ReleaseType.STABLE
	}
}

publishMods {
	version = modVersion
	displayName = "v${modVersion}"

	type = getVersionType()
	modLoaders.addAll("fabric", "quilt")

	file = tasks.withType<RemapJarTask>()["remapJar"].archiveFile
	changelog = file(rootDir.toPath().resolve("src/main/resources/changelog/${modVersion}.md")).readText()

	modrinth {
		accessToken = providers.environmentVariable("MODRINTH_SECRET")
		projectId = "shTz7pFB"

		minecraftVersionRange {
			start = "b1.5"
			end = "b1.8.1"
			includeSnapshots = true
		}

		minecraftVersionRange {
			start = "1.0"
			end = "1.2.5"
			// end = "12w17a" - missing on Modrinth ...?
			includeSnapshots = true
		}

		minecraftVersionRange {
			start = "1.3.2"
			end = "1.12.2"
			includeSnapshots = true
		}

		requires{ slug = "osl"; version = libs.versions.osl.bundle.get() }
	}
}

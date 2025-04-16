plugins {
	id("server_stats.module")
}

dependencies {
	shadow(libs.gson)

	include(project(":module:bugfix:jump"))
	include(project(":module:bugfix:movement"))
	include(project(":module:bugfix:result-amount"))

	include(project(path = ":module:common", configuration = "shadow"))

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

	include(project(":module:network:osl:client"))
	include(project(":module:network:osl:common"))
	include(project(":module:network:osl:server"))

	include(project(":module:network:vanilla:client"))
	include(project(":module:network:vanilla:common"))
	include(project(":module:network:vanilla:merged:1.3.2-to-1.5.2"))
	include(project(":module:network:vanilla:merged:1.6.0-alpha.13w16a-to-1.6.4"))
	include(project(":module:network:vanilla:server"))

	// Code for all versions without server-side statistics
	include(project(path = ":module:shared:client"))
	include(project(path = ":module:shared:common"))
	include(project(path = ":module:shared:server"))

	// b1.5.0 -> first version with statistics!
	include(project(":module:versions:1.0.0-beta.1.5.0-to-1.0.0-beta.1.7.3-client"))
	include(project(":module:versions:1.0.0-beta.1.5.0-to-1.0.0-beta.1.5.2-server"))
	// b1.6.0 -> changed PlayerManager.respawn signature
	include(project(":module:versions:1.0.0-beta.1.6.0-to-1.0.0-beta.1.7.3-server"))

	// b1.8.0 -> added DamageSource, Minecraft.startGame signature changed
	include(project(":module:versions:1.0.0-beta.1.8.0-to-1.0.0-beta.1.8.1-client"))
	include(project(":module:versions:1.0.0-beta.1.8.0-to-1.0.0-beta.1.8.1-server"))

	// 1.0.0 -> Initial release
	// NOTE: client works until 1.0.0-pre.3 in theory
	include(project(":module:versions:1.0.0-to-1.1.0-alpha.11w48a-client"))
	include(project(":module:versions:1.0.1-to-1.1.0-alpha.11w48a-server"))
	// 11w49a -> CustomPayloadPacket added
	include(project(":module:versions:1.1.0-alpha.11w49a-to-1.1.0-alpha.11w50a-client"))
	include(project(":module:versions:1.1.0-alpha.11w49a-to-1.1.0-alpha.11w50a-server"))
	// 12w01a -> Entities.register and MinecraftServer.loadWorld signature changed
	include(project(":module:versions:1.1.0-alpha.12w01a-to-1.3.0-alpha.12w16a-client"))
	include(project(":module:versions:1.1.0-alpha.12w01a-to-1.3.0-alpha.12w16a-server"))
	// 12w17a -> ...
	include(project(":module:versions:1.3.0-alpha.12w17a-to-1.3.0-alpha.12w17a-client"))
	include(project(":module:versions:1.3.0-alpha.12w17a-to-1.3.0-alpha.12w17a-server"))
	// NOTE: 12w18a to 12w21a are v weird ..
	// NOTE: All further snapshots including 1.3.0-pre.1 unsupported, for now
	// 1.3.2 -> now merged!
	include(project(":module:versions:1.3.2-to-1.4.0-alpha.12w38b"))
	// 12w39a -> ...
	include(project(":module:versions:1.4.0-alpha.12w39a-to-1.5.0-alpha.13w01b"))
	//13w02a -> ...
	include(project(":module:versions:1.5.0-alpha.13w02a-to-1.5.2"))
	// 13w16a -> ...
	include(project(":module:versions:1.6.0-alpha.13w16a-to-1.6.4"))
	// 13w36a -> server-side statistics
	include(project(":module:versions:1.7.0-alpha.13w36a-to-1.7.0-alpha.13w38c"))
	// 13w39a -> PlayerManager.createForLogin signature changed
	include(project(":module:versions:1.7.0-alpha.13w39a-to-1.7.5"))
	// 1.7.6 -> player data saved with uuid
	include(project(":module:versions:1.7.6-pre.1-to-1.8.0-alpha.14w05b"))
	// 14w06a -> new statistics format, no upgrade path :(
	include(project(":module:versions:1.8.0-alpha.14w06a-to-1.12.2"))
}

tasks.remapJar {
	from("LICENSE")
}

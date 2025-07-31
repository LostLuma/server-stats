pluginManagement {
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

		gradlePluginPortal()
	}
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "server-stats"
includeBuild("build-logic")

include(":module:api")
include(":module:impl")

include(":module:broadcast:client:1.0.0-beta.1.5.0-to-1.3.0-alpha.12w16a")
include(":module:broadcast:client:1.3.0-alpha.12w17a-to-1.3.0-alpha.12w17a")
include(":module:broadcast:merged")
include(":module:broadcast:server")

include(":module:bugfix:achievement:adventuring-time-unlock")
include(":module:bugfix:achievement:adventuring-time-persistence")
include(":module:bugfix:achievement:overkill:1.0.0-beta.1.9.0-pre.5-to-1.1.0-alpha.11w48a")
include(":module:bugfix:achievement:overkill:1.1.0-alpha.11w49a-to-1.6.0-alpha.13w25c")
include(":module:bugfix:achievement:overkill:1.6.0-alpha.13w26a-to-1.8.0-alpha.14w31a")
include(":module:bugfix:achievement:sniper-duel:1.0.0-beta.1.8.0-pre.1-to-1.0.0-beta.1.8.0-pre.1")
include(":module:bugfix:achievement:sniper-duel:1.0.0-beta.1.8.0-pre.2-to-1.1-alpha.12w01a")
include(":module:bugfix:achievement:the-end")
include(":module:bugfix:achievement:tool-material:1.0.0-beta.1.5.0-to-1.3.2")
include(":module:bugfix:achievement:tool-material:1.4.0-alpha.12w32a-to-1.6.4")
include(":module:bugfix:achievement:tool-material:1.7.0-alpha.13w36a-to-1.7.0-alpha.13w36b")

include(":module:bugfix:misc:game-directory")

include(":module:bugfix:statistic:block-merging:1.0.0-beta.1.5.0-to-1.6.4")
include(":module:bugfix:statistic:block-merging:1.7.0-alpha.13w36a-to-1.12.2")
include(":module:bugfix:statistic:combat:1.7.0-alpha.13w36a-to-1.8.0-alpha.14w08a")
include(":module:bugfix:statistic:combat:1.8.0-alpha.14w10a-to-1.8.2-pre.4")
include(":module:bugfix:statistic:combat:1.8.2-pre.5-to-1.9.0-alpha.15w32c")
include(":module:bugfix:statistic:combat:1.9.0-alpha.15w33a-to-1.10.2")
include(":module:bugfix:statistic:combat:1.11.0-alpha.16w32a-to-1.12.0-alpha.17w06a")
include(":module:bugfix:statistic:combat:1.12.0-alpha.17w13a-to-1.12.0-alpha.17w13b")
include(":module:bugfix:statistic:combat:1.12.0-alpha.17w14a-to-1.12.2")
include(":module:bugfix:statistic:damage-attribution:1.9.0-alpha.15w36a-to-1.11.0-alpha.16w42a")
include(":module:bugfix:statistic:damage-attribution:1.11.0-alpha.16w43a-to-1.11.0-alpha.16w43a")
include(":module:bugfix:statistic:damage-attribution:1.11.0-alpha.16w44a-to-1.12.2")
include(":module:bugfix:statistic:doubled-distance")
include(":module:bugfix:statistic:downwards-climbing")
include(":module:bugfix:statistic:drop-amount:1.0.0-beta.1.5.0-to-1.2.0-alpha.12w08a")
include(":module:bugfix:statistic:drop-amount:1.2.0-to-1.7.0-alpha.13w37b")
include(":module:bugfix:statistic:drop-amount:1.7.0-alpha.13w38a-to-1.8.0-alpha.14w08a")
include(":module:bugfix:statistic:drop-amount:1.8.0-alpha.14w10a-to-1.8.0-alpha.14w28b")
include(":module:bugfix:statistic:drop-amount:1.8.0-alpha.14w29a-to-1.12.2")
include(":module:bugfix:statistic:item-use:1.0.0-beta.1.5.0-to-1.0.0-beta.1.7.3")
include(":module:bugfix:statistic:item-use:1.0.0-beta.1.8.0-pre.1-to-1.0.0-beta.1.8.1")
include(":module:bugfix:statistic:item-use:1.0.0-beta.1.9.0-pre.1-to-1.0.0-beta.1.9.0-pre.1")
include(":module:bugfix:statistic:item-use:1.0.0-beta.1.9.0-pre.2-to-1.0.0-beta.1.9.0-pre.2")
include(":module:bugfix:statistic:item-use:1.0.0-beta.1.9.0-pre.3-to-1.0.0-beta.1.9.0-pre.3")
include(":module:bugfix:statistic:item-use:1.0.0-beta.1.9.0-pre.4-to-1.0.0-beta.1.9.0-pre.5")
include(":module:bugfix:statistic:item-use:1.0.0-beta.1.9.0-pre.6-to-1.1.0")
include(":module:bugfix:statistic:item-use:1.2.0-alpha.12w03a-to-1.2.0-alpha.12w03a")
include(":module:bugfix:statistic:item-use:1.2.0-alpha.12w04a-to-1.3.0-alpha.12w16a")
include(":module:bugfix:statistic:item-use:1.3.0-alpha.12w17a-to-1.4.0-alpha.12w32a")
include(":module:bugfix:statistic:item-use:1.4.0-alpha.12w34a-to-1.4.0-alpha.12w36a")
include(":module:bugfix:statistic:item-use:1.4.0-alpha.12w37a-to-1.4.7")
include(":module:bugfix:statistic:item-use:1.5.0-alpha.13w01a-to-1.6.4")
include(":module:bugfix:statistic:item-use:1.7.0-alpha.13w36a-to-1.8.0-alpha.14w08a")
include(":module:bugfix:statistic:item-use:1.9.0-alpha.15w33c-to-1.11.0")
include(":module:bugfix:statistic:item-use:1.11.1-alpha.16w50a-to-1.12.2")
include(":module:bugfix:statistic:jump")
include(":module:bugfix:statistic:movement")
include(":module:bugfix:statistic:pickup-amount:1.9.0-alpha.15w33a-to-1.12.0-alpha.17w06a")
include(":module:bugfix:statistic:pickup-amount:1.12.0-alpha.17w13a-to-1.12.2")
include(":module:bugfix:statistic:result-amount")
include(":module:bugfix:statistic:sheep-shearing:1.0.0-beta.1.7.0-to-1.5.2")
include(":module:bugfix:statistic:sheep-shearing:1.6.0-alpha.13w16a-to-1.6.4")
include(":module:bugfix:statistic:sheep-shearing:1.7.0-alpha.13w36a-to-1.8.9")
include(":module:bugfix:statistic:sheep-shearing:1.9.0-alpha.15w31a-to-1.10.2")
include(":module:bugfix:statistic:sheep-shearing:1.11.0-alpha.16w32a-to-1.12.2")

include(":module:compat:modmenu")

include(":module:dfu")

include(":module:entrypoint:client")
include(":module:entrypoint:common")
include(":module:entrypoint:merged:1.3.2-to-1.5.2")
include(":module:entrypoint:merged:1.6.0-alpha.13w16a-to-1.6.4")
include(":module:entrypoint:server")

include(":module:event")

include(":module:gui:large-stats:1.0.0-beta.1.5.0-to-1.2.3")
include(":module:gui:large-stats:1.2.4-to-1.5.2")
include(":module:gui:large-stats:1.6.0-alpha.13w16a-to-1.6.4")

include(":module:gui:mob-stats:1.0.0-beta.1.5.0-to-1.5.2")
include(":module:gui:mob-stats:1.6.0-alpha.13w16a-to-1.6.4")

include(":module:gui:util:1.0.0-beta.1.5.0-to-1.0.0-beta.1.9.0-pre.4")
include(":module:gui:util:1.0.0-beta.1.9.0-pre.5-to-1.3.0-alpha.12w17a")
include(":module:gui:util:1.3.2-to-1.5.2")
include(":module:gui:util:1.6.0-alpha.13w16a-to-1.6.0-alpha.13w17a")
include(":module:gui:util:1.6.0-alpha.13w18a-to-1.6.0-alpha.13w23b")
include(":module:gui:util:1.6.0-alpha.13w24a-to-1.6.4")

include(":module:identity:client:0.31.20100327-to-0.31.20100625-1917")
include(":module:identity:client:0.31.20100627-to-1.3-alpha.12w17a")
include(":module:identity:merged:1.3.2-to-1.5.2")
include(":module:identity:merged:1.6.0-alpha.13w16a-to-1.6.4")
include(":module:identity:merged:1.7.0-alpha.13w36a-to-1.7.0-alpha.13w38c")
include(":module:identity:merged:1.7.0-alpha.13w39a-to-1.7.5")

include(":module:identity:server")

include(":module:lifecycle:client:0.31.20100327-to-0.31.20100330-1611")
include(":module:lifecycle:client:0.31.20100413-to-0.31.20100625-1917")
include(":module:lifecycle:client:0.31.20100627-to-1.0.0-alpha.1.0.5")
include(":module:lifecycle:client:1.0.0-alpha.1.0.6-to-1.0.0-alpha.1.1.2")
include(":module:lifecycle:client:1.0.0-alpha.1.2.0-to-1.0.0-beta.1.2.0")
include(":module:lifecycle:client:1.0.0-beta.1.3.0-to-1.0.0-beta.1.7.3")
include(":module:lifecycle:client:1.0.0-beta.1.8.0-pre.1-to-1.3.0-alpha.12w17a")

include(":module:lifecycle:merged:1.3.2-to-1.4.0-alpha.12w38b")
include(":module:lifecycle:merged:1.4.0-alpha.12w39a-to-1.5.2")
include(":module:lifecycle:merged:1.6.0-alpha.13w16a-to-1.6.4")

include(":module:lifecycle:server:1.0.0-alpha.0.1.0-to-1.0.0-beta.1.2.0")
include(":module:lifecycle:server:1.0.0-beta.1.3.0-to-1.0.0-beta.1.3.0")
include(":module:lifecycle:server:1.0.0-beta.1.4.0-to-1.1.0-alpha.11w50a")
include(":module:lifecycle:server:1.1.0-alpha.12w01a-to-1.3.0-alpha.12w17a")

include(":module:network:osl:client")
include(":module:network:osl:common")
include(":module:network:osl:server")

include(":module:network:vanilla:client")
include(":module:network:vanilla:common")
include(":module:network:vanilla:merged:1.3.2-to-1.5.2")
include(":module:network:vanilla:merged:1.6.0-alpha.13w16a-to-1.6.4")
include(":module:network:vanilla:server")

include(":module:provider:client")
include(":module:provider:common")
include(":module:provider:merged")
include(":module:provider:server")

include(":module:statistic:combat:1.0.0-beta.1.5.0-to-1.1.0-beta.1.7.3")
include(":module:statistic:combat:1.0.0-beta.1.8.0-pre.1-to-1.1.0-alpha.11w48a")
include(":module:statistic:combat:1.1.0-alpha.11w49a-to-1.1.0-alpha.11w50a")
include(":module:statistic:combat:1.1.0-alpha.12w01a-to-1.6.4")

include(":module:statistic:item-transfer:1.0.0-beta.1.5.0-to-1.2.0-alpha.12w08a")
include(":module:statistic:item-transfer:1.2.0-to-1.6.4")
include(":module:statistic:item-transfer:1.7.0-alpha.13w36a-to-1.7.0-alpha.13w37b")
include(":module:statistic:item-transfer:1.7.0-alpha.13w38a-to-1.8.0-alpha.14w05b")
include(":module:statistic:item-transfer:1.8.0-alpha.14w06a-to-1.8.0-alpha.14w08a")
include(":module:statistic:item-transfer:1.8.0-alpha.14w10a-to-1.8.0-alpha.14w26c")
include(":module:statistic:item-transfer:1.8.0-alpha.14w27a-to-1.8.0-alpha.14w28b")
include(":module:statistic:item-transfer:1.8.0-alpha.14w29a-to-1.9.0-alpha.15w32c")
include(":module:statistic:item-transfer:1.9.0-alpha.15w33a-to-1.12.0-alpha.17w06a")
include(":module:statistic:item-transfer:1.12.0-alpha.17w13a-to-1.12.2")

include(":module:statistic:local:client")
include(":module:statistic:local:merged")
include(":module:statistic:local:server")

include(":module:statistic:movement")

include(":module:translation:client")
include(":module:translation:common")
include(":module:translation:merged")
include(":module:translation:server")

include(":module:util")

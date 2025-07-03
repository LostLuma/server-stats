pluginManagement {
	repositories {
		maven {
			name = "Fabric"
			url = uri("https://maven.fabricmc.net")
		}
		maven {
			name = "Ornithe"
			url = uri("https://maven.ornithemc.net/releases")
		}
		maven {
			name = "Quilt"
			url = uri("https://maven.quiltmc.org/repository/release")
		}
		gradlePluginPortal()
	}
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

rootProject.name = "server-stats"
includeBuild("build-logic")

include(":module:api")
include(":module:impl")

include(":module:bugfix:achievement:adventuring-time-unlock")
include(":module:bugfix:achievement:adventuring-time-persistence")
include(":module:bugfix:achievement:overkill:1.0.0-to-1.1.0-alpha.11w48a")
include(":module:bugfix:achievement:overkill:1.1.0-alpha.11w49a-to-1.6.0-alpha.13w25c")
include(":module:bugfix:achievement:overkill:1.6.0-alpha.13w26a-to-1.8.0-alpha.14w31a")
include(":module:bugfix:achievement:sniper-duel")
include(":module:bugfix:achievement:the-end")
include(":module:bugfix:achievement:tool-material:1.0.0-beta.1.5.0-to-1.3.2")
include(":module:bugfix:achievement:tool-material:1.4.0-alpha.12w32a-to-1.6.4")
include(":module:bugfix:achievement:tool-material:1.7.0-alpha.13w36a-to-1.7.0-alpha.13w36b")

include(":module:bugfix:statistic:doubled-distance")
include(":module:bugfix:statistic:drop-amount:1.0.0-beta.1.5.0-to-1.2.0-alpha.12w08a")
include(":module:bugfix:statistic:drop-amount:1.2.0-to-1.7.0-alpha.13w37b")
include(":module:bugfix:statistic:drop-amount:1.7.0-alpha.13w38a-to-1.8.0-alpha.14w08a")
include(":module:bugfix:statistic:drop-amount:1.8.0-alpha.14w10a-to-1.8.0-alpha.14w28b")
include(":module:bugfix:statistic:drop-amount:1.8.0-alpha.14w29a-to-1.12.2")
include(":module:bugfix:statistic:jump")
include(":module:bugfix:statistic:movement")
include(":module:bugfix:statistic:pickup-amount:1.9.0-alpha.15w33a-to-1.9.0-alpha.15w33c")
include(":module:bugfix:statistic:pickup-amount:1.9.0-alpha.15w34a-to-1.9.0-alpha.15w47c")
include(":module:bugfix:statistic:pickup-amount:1.9.0-alpha.15w49a-to-1.12.0-alpha.17w06a")
include(":module:bugfix:statistic:pickup-amount:1.12.0-alpha.17w13a-to-1.12.2")
include(":module:bugfix:statistic:result-amount")

include(":module:dfu")

include(":module:gui:large-stats:1.0.0-beta.1.5.0-to-1.2.3")
include(":module:gui:large-stats:1.2.4-to-1.5.2")
include(":module:gui:large-stats:1.6.0-alpha.13w16a-to-1.6.4")

include(":module:gui:mob-stats:1.0.0-beta.1.5.0-to-1.5.2")
include(":module:gui:mob-stats:1.6.0-alpha.13w16a-to-1.6.4")

include(":module:gui:util:1.0.0-beta.1.5.0-to-1.0.0-beta.1.8.1")
include(":module:gui:util:1.0.0-to-1.3.0-alpha.12w17a")
include(":module:gui:util:1.3.2-to-1.5.2")
include(":module:gui:util:1.6.0-alpha.13w16a-to-1.6.0-alpha.13w17a")
include(":module:gui:util:1.6.0-alpha.13w18a-to-1.6.0-alpha.13w23b")
include(":module:gui:util:1.6.0-alpha.13w24a-to-1.6.4")

include(":module:identity:client")
include(":module:identity:merged:1.3.2-to-1.5.2")
include(":module:identity:merged:1.6.0-alpha.13w16a-to-1.6.4")
include(":module:identity:merged:1.7.0-alpha.13w36a-to-1.7.0-alpha.13w38c")
include(":module:identity:merged:1.7.0-alpha.13w39a-to-1.7.5")

include(":module:identity:server")

include(":module:lifecycle:client:1.0.0-beta.1.5.0-to-1.0.0-beta.1.7.3")
include(":module:lifecycle:client:1.0.0-beta.1.8.0-to-1.3.0-alpha.12w17a")

include(":module:lifecycle:merged:1.3.2-to-1.4.0-alpha.12w38b")
include(":module:lifecycle:merged:1.4.0-alpha.12w39a-to-1.6.4")

include(":module:lifecycle:server:1.0.0-beta.1.5.0-to-1.1.0-alpha.11w50a")
include(":module:lifecycle:server:1.1.0-alpha.12w01a-to-1.3.0-alpha.12w17a")

include(":module:network:osl:client")
include(":module:network:osl:common")
include(":module:network:osl:server")

include(":module:network:vanilla:client")
include(":module:network:vanilla:common")
include(":module:network:vanilla:merged:1.3.2-to-1.5.2")
include(":module:network:vanilla:merged:1.6.0-alpha.13w16a-to-1.6.4")
include(":module:network:vanilla:server")

include(":module:shared:client")
include(":module:shared:common")
include(":module:shared:server")

include(":module:statistic:combat:1.0.0-beta.1.5.0-to-1.1.0-beta.1.7.3")
include(":module:statistic:combat:1.0.0-beta.1.8.0-to-1.1.0-alpha.11w48a")
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

include(":module:statistic:movement")

include(":module:util")

include(":module:version:1.0.0-beta.1.5.0-to-1.0.0-beta.1.7.3-client")
include(":module:version:1.0.0-beta.1.5.0-to-1.0.0-beta.1.5.2-server")
include(":module:version:1.0.0-beta.1.6.0-to-1.0.0-beta.1.7.3-server")

include(":module:version:1.0.0-beta.1.8.0-to-1.0.0-beta.1.8.1-client")
include(":module:version:1.0.0-beta.1.8.0-to-1.0.0-beta.1.8.1-server")

include(":module:version:1.0.0-to-1.3.0-alpha.12w17a-client")
include(":module:version:1.0.1-to-1.3.0-alpha.12w17a-server")

include(":module:version:1.3.2-to-1.6.4")

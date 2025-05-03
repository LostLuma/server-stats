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

include(":module:bugfix:drop-amount:1.0.0-beta.1.5.0-to-1.2.0-alpha.12w08a")
include(":module:bugfix:drop-amount:1.2.0-to-1.7.0-alpha.13w37b")
include(":module:bugfix:drop-amount:1.7.0-alpha.13w38a-to-1.8.0-alpha.14w08a")
include(":module:bugfix:drop-amount:1.8.0-alpha.14w10a-to-1.8.0-alpha.14w28b")
include(":module:bugfix:drop-amount:1.8.0-alpha.14w29a-to-1.12.2")
include(":module:bugfix:jump")
include(":module:bugfix:movement")
include(":module:bugfix:result-amount")

include(":module:common")

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

include(":module:version:1.0.0-beta.1.5.0-to-1.0.0-beta.1.7.3-client")
include(":module:version:1.0.0-beta.1.5.0-to-1.0.0-beta.1.5.2-server")
include(":module:version:1.0.0-beta.1.6.0-to-1.0.0-beta.1.7.3-server")

include(":module:version:1.0.0-beta.1.8.0-to-1.0.0-beta.1.8.1-client")
include(":module:version:1.0.0-beta.1.8.0-to-1.0.0-beta.1.8.1-server")

include(":module:version:1.0.0-to-1.1.0-alpha.11w48a-client")
include(":module:version:1.0.1-to-1.1.0-alpha.11w48a-server")

include(":module:version:1.1.0-alpha.11w49a-to-1.1.0-alpha.11w50a-client")
include(":module:version:1.1.0-alpha.11w49a-to-1.1.0-alpha.11w50a-server")

include(":module:version:1.1.0-alpha.12w01a-to-1.3.0-alpha.12w16a-client")
include(":module:version:1.1.0-alpha.12w01a-to-1.3.0-alpha.12w16a-server")

include(":module:version:1.3.0-alpha.12w17a-to-1.3.0-alpha.12w17a-client")
include(":module:version:1.3.0-alpha.12w17a-to-1.3.0-alpha.12w17a-server")

include(":module:version:1.3.2-to-1.4.0-alpha.12w38b")

include(":module:version:1.4.0-alpha.12w39a-to-1.5.0-alpha.13w01b")

include(":module:version:1.5.0-alpha.13w02a-to-1.5.2")

include(":module:version:1.6.0-alpha.13w16a-to-1.6.4")

include(":module:version:1.7.0-alpha.13w36a-to-1.7.0-alpha.13w38c")
include(":module:version:1.7.0-alpha.13w39a-to-1.7.5")

// include(":module:version:1.7.6-pre.1-to-1.8.0-alpha.14w05b")

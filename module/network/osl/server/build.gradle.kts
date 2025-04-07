import net.ornithemc.ploceus.api.GameSide

plugins {
	id("server_stats.module")
}

dependencies {
	implementation(project(":module:common"))
	implementation(project(":module:network:osl:common"))
	ploceus.dependOsl(libs.versions.osl.bundle.get(), GameSide.SERVER)
}

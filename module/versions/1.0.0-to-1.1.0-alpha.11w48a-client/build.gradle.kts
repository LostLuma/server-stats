import net.ornithemc.ploceus.api.GameSide

plugins {
	id("server_stats.module")
}

dependencies {
	implementation(project(":module:common"))
	ploceus.dependOsl(libs.versions.osl.get(), GameSide.CLIENT)
}

loom {
	accessWidenerPath = file("src/main/resources/server_stats.accesswidener")
}

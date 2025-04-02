import net.ornithemc.ploceus.api.GameSide

plugins {
	id("server_stats.module")
}

loom {
	accessWidenerPath = file("src/main/resources/server_stats.accesswidener")
}

dependencies {
	ploceus.dependOsl(libs.versions.osl.get(), GameSide.CLIENT)
}

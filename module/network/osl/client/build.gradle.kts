import net.ornithemc.ploceus.api.GameSide

plugins {
	id("server_stats.module")
}

dependencies {
	implementation(project(":module:entrypoint:common"))
	implementation(project(":module:impl"))
	implementation(project(":module:util"))
	implementation(project(":module:network:osl:common"))
	ploceus.dependOsl(libs.versions.osl.bundle.get(), GameSide.CLIENT)
}

loom {
	accessWidenerPath = file("src/main/resources/server_stats.network.accesswidener")
}

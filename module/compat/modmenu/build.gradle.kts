plugins {
	id("server_stats.module")
}

dependencies {
	modImplementation(libs.modmenu)
	implementation(project(":module:util"))
}

plugins {
	id("server_stats.library")
}

dependencies {
	implementation(project(":module:impl"))
	implementation(project(":module:util"))
	implementation(libs.osl.networking.server.get())
}

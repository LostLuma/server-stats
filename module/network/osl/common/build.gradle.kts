plugins {
	id("server_stats.library")
}

dependencies {
	implementation(project(":module:common"))
	implementation(libs.osl.networking.server.get())
}

plugins {
	id("server_stats.module")
}

dependencies {
	api(project(":module:api"))
	implementation(project(":module:util"))
}

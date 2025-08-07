plugins {
	id("server_stats.library")
}

dependencies {
	implementation(project(":module:api"))
	implementation(project(":module:entrypoint:common"))
	implementation(project(":module:util"))
}

plugins {
	id("server_stats.module")
}

dependencies {
	implementation(project(":module:common"))
	implementation(project(":module:network:vanilla:common"))
}

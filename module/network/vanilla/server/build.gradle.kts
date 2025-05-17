plugins {
	id("server_stats.module")
}

dependencies {
	implementation(project(":module:impl"))
	implementation(project(":module:util"))
	implementation(project(":module:network:vanilla:common"))
}

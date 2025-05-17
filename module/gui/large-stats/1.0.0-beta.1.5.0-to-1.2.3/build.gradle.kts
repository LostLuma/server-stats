plugins {
	id("server_stats.module")
}

dependencies {
	implementation(project(":module:api"))
	implementation(project(":module:util"))
}

loom {
	accessWidenerPath = file("src/main/resources/server_stats.large_stats.accesswidener")
}

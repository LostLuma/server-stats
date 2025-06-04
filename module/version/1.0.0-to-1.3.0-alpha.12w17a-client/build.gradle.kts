plugins {
	id("server_stats.module")
}

dependencies {
	implementation(project(":module:impl"))
	implementation(project(":module:util"))
}

loom {
	accessWidenerPath = file("src/main/resources/server_stats.accesswidener")
}

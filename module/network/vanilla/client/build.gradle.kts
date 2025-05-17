plugins {
	id("server_stats.module")
}

dependencies {
	implementation(project(":module:impl"))
	implementation(project(":module:util"))
	implementation(project(":module:network:vanilla:common"))
}

loom {
	accessWidenerPath = file("src/main/resources/server_stats.network.accesswidener")
}

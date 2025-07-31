plugins {
	id("server_stats.module")
}

dependencies {
	implementation(project(":module:impl"))
}

loom {
	accessWidenerPath = file("src/main/resources/server_stats.provider.client.accesswidener")
}

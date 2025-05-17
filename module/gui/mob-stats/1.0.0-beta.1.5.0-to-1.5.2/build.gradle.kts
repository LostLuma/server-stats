plugins {
	id("server_stats.module")
}

dependencies {
	implementation(project(":module:api"))
	implementation(project(":module:util"))
	implementation(project(":module:gui:util:1.0.0-beta.1.5.0-to-1.0.0-beta.1.8.1"))
}

loom {
	accessWidenerPath = file("src/main/resources/server_stats.mob_stats.accesswidener")
}

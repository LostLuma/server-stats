plugins {
	id("server_stats.module")
}

dependencies {
	implementation(project(":module:api"))
	implementation(project(":module:util"))
	implementation(project(":module:gui:util:1.0.0-beta.1.5.0-to-1.0.0-beta.1.9.0-pre.4"))
}

loom {
	accessWidenerPath = file("src/main/resources/server_stats.gui.mob_stats.accesswidener")
}

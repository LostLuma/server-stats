plugins {
	id("server_stats.module")
}

dependencies {
	implementation(project(":module:common"))
}

loom {
	accessWidenerPath = file("src/main/resources/server_stats.gui.accesswidener")
}

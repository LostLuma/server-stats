plugins {
	id("server_stats.module")
}

dependencies {
	implementation(project(":module:api"))
}

loom {
	accessWidenerPath = file("src/main/resources/server_stats.statistic.combat.accesswidener")
}

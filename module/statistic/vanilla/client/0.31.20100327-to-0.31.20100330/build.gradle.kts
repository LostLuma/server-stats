plugins {
	id("server_stats.module")
}

dependencies {
	implementation(project(":module:api"))
	implementation(project(":module:statistic:vanilla:registry"))
}

loom {
	accessWidenerPath = file("src/main/resources/server_stats.statistic.vanilla.client.accesswidener")
}

plugins {
	id("server_stats.module")
}

dependencies {
	implementation(project(":module:api"))
	implementation(project(":module:statistic:vanilla:registry"))
}

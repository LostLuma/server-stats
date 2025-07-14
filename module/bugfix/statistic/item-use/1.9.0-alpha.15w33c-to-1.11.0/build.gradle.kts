plugins {
	id("server_stats.module")
}

loom {
	accessWidenerPath = file("src/main/resources/server_stats.bugfix.item_use.accesswidener")
}

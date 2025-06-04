plugins {
	id("base")
}

fun moduleName(long: Boolean): String {
	val path = project.path.split(":")

	if (path.size < 3) {
		return "server-stats"
	}

	// Ignore last segment if long is false.
	val remove = if (long) { 0 } else { 1 }
	return path.subList(2, path.size - remove).joinToString(".")
}

fun mavenGroup(): String {
	val name = moduleName(false)
	val base = project.property("maven_group").toString()

	return if (name == "server-stats") {
		base
	} else if (name.isEmpty()) {
		"${base}.server-stats"
	} else {
		"${base}.server-stats.${name}"
	}
}

fun modVersion(): String {
	val base = project.property("mod_version").toString()
	val isCi = providers.environmentVariable("CI").isPresent

	return if (isCi) {
		base
	} else {
		"${base}+local"
	}
}

// Each project needs a unique identifier
// And archive name, otherwise Gradle and
// Loom treat them as interchangeable ...
group = mavenGroup()
version = modVersion()

base {
	archivesName = moduleName(true)
}

tasks.withType<ProcessResources> {
	inputs.property("version", version)

	filesMatching("fabric.mod.json") {
		expand(inputs.properties)
	}
}

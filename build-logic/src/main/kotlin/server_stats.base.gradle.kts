plugins {
	id("base")
}

fun moduleName(): String {
	val path = project.path.split(":");

	if (path.size < 3) {
		return "server-stats";
	} else {
		val module = path.subList(2, path.size);
		return module.joinToString(".");
	}
}

fun mavenGroup(): String {
	val name = moduleName();
	val base = project.property("maven_group").toString();

	if (name == "server-stats") {
		return base;
	} else {
		return base + "." + name.replace("-", "_");
	}
}

fun modVersion(): String {
	val base = project.property("mod_version").toString();
	val isCi = providers.environmentVariable("CI").isPresent;

	if (isCi) {
		return base;
	} else {
		return "${base}+local";
	}
}

// Each project needs a unique identifier
// And archive name, otherwise Gradle and
// Loom treat them as interchangeable ...
group = mavenGroup()
version = modVersion()

base {
	archivesName = moduleName()
}

tasks.withType<ProcessResources> {
	inputs.property("version", version)

	filesMatching("fabric.mod.json") {
		expand(inputs.properties)
	}
}

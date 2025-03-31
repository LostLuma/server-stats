plugins {
	id("base")
}

fun mavenGroup(): String {
	val path = project.path.split(":");
	val base = project.property("maven_group").toString();

    /*
	if (path.size < 2) {
		return base;
	} else {
		return base + "." + path[1].replace("-", "_");
	}
     */

    return base + project.path.replace(":", ".");
}

// Each project needs a unique identifier,
// Otherwise projects with the same name don't work.
group = mavenGroup()
version = project.property("mod_version").toString()

tasks.withType<ProcessResources> {
	inputs.property("version", project.property("mod_version"))

	filesMatching("quilt.mod.json") {
		expand(inputs.properties)
	}
}

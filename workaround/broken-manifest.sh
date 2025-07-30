#!/bin/bash

# The Minecraft Jar URLs in the original manifests no longer work
# The manifests shipped in the workaround directory link to Omniarchive instead
mkdir -p ~/.gradle/caches/fabric-loom/a1.0.6
cp workaround/manifest/a1.0.6.json ~/.gradle/caches/fabric-loom/a1.0.6/skyrising_minecraft_info.json

mkdir -p ~/.gradle/caches/fabric-loom/a1.0.10
cp workaround/manifest/a1.0.10.json ~/.gradle/caches/fabric-loom/a1.0.10/skyrising_minecraft_info.json

mkdir -p ~/.gradle/caches/fabric-loom/a1.2.0
cp workaround/manifest/a1.2.0.json ~/.gradle/caches/fabric-loom/a1.2.0/skyrising_minecraft_info.json

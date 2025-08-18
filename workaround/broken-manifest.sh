#!/bin/bash

# The Minecraft Jar URLs in the original manifests no longer work
# The manifests shipped in the workaround directory link to Omniarchive instead
mkdir -p ~/.gradle/caches/fabric-loom/a1.0.1_01
cp workaround/manifest/a1.0.1_01.json ~/.gradle/caches/fabric-loom/a1.0.1_01/skyrising_minecraft_info.json

mkdir -p ~/.gradle/caches/fabric-loom/a1.0.5-2149
cp workaround/manifest/a1.0.5-2149.json ~/.gradle/caches/fabric-loom/a1.0.5-2149/skyrising_minecraft_info.json

mkdir -p ~/.gradle/caches/fabric-loom/a1.0.6
cp workaround/manifest/a1.0.6.json ~/.gradle/caches/fabric-loom/a1.0.6/skyrising_minecraft_info.json

mkdir -p ~/.gradle/caches/fabric-loom/a1.0.8_01
cp workaround/manifest/a1.0.8_01.json ~/.gradle/caches/fabric-loom/a1.0.8_01/skyrising_minecraft_info.json

mkdir -p ~/.gradle/caches/fabric-loom/a1.0.10
cp workaround/manifest/a1.0.10.json ~/.gradle/caches/fabric-loom/a1.0.10/skyrising_minecraft_info.json

mkdir -p ~/.gradle/caches/fabric-loom/a1.1.1
cp workaround/manifest/a1.1.1.json ~/.gradle/caches/fabric-loom/a1.1.1/skyrising_minecraft_info.json

mkdir -p ~/.gradle/caches/fabric-loom/a1.2.0
cp workaround/manifest/a1.2.0.json ~/.gradle/caches/fabric-loom/a1.2.0/skyrising_minecraft_info.json

![The mod's icon](https://files.lostluma.net/ZyjaRV.png)

# Server Stats

Implementation of server-side (per world) statistics for legacy versions of Minecraft.

## Information

Allows recording player statistics on legacy servers using the modern statistics system introduced in 1.7.  
Previously statistics were stored client-side, shared between all your worlds, and often reset themselves.

To see your own statistics you can either install Server Stats on the client as well as the server to sync  
them whenever you join a single- or multiplayer world using the mod or upgrade to Minecraft 1.7.x or above.

> [!TIP]  
> To see your own statistics in 11w48a (1.1 snapshot) and earlier install [OSL](https://modrinth.com/mod/osl) on client and server.

As Server Stats is intended to also be used by players eventually upgrading to Minecraft 1.8 (and beyond!)  
it also includes a statistics upgrade system to convert ID-based statistics to resource location based ones.  

> [!IMPORTANT]  
> Every player must join the server once in a version between 1.8 and 1.12.2 to upgrade their own statistics!

## Installation

Server Stats runs on [Quilt Loader](https://quiltmc.org/en/) using the [OrnitheMC Toolchain](https://ornithemc.net/) for legacy Minecraft versions.  
To install the mod use the Ornithe installer, then simply drop Server Stats into your `mods` folder.

You can download the release version of Server Stats from either [Modrinth](https://modrinth.com/mod/server-stats) or [GitHub](https://github.com/LostLuma/server-stats/releases).

## Supported Minecraft Versions

Server Stats supports a wide range of Minecraft versions including some snapshots,  
however please note that some non-mainline versions are (currently) not supported.

Officially supported versions:

- Beta 1.5 to Beta 1.8.1
- Release 1.0 to 12w17a (1.3 snapshot)
- Release 1.3.2 to Release 1.12.2

Leaked Beta builds, Beta 1.9 / 1.0 Pre-Releases, and certain 1.3 snapshots are unsupported.  
The mod may function on these versions without issue, but you will receive no support doing so.

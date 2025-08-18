![The mod's icon](https://files.lostluma.net/ZyjaRV.png)

# Server Stats

Implementation of server-side (per world) statistics for legacy versions of Minecraft.

## Features

Allows recording player statistics on older versions using the modern statistics system introduced in 1.7.  
Without Server Stats statistics are stored client-side, shared between all worlds, and can randomly reset.

### Bug Fixes

Server Stats contains bug fixes for most achievement- and statistic-related issues in supported versions.  
To find out whether a specific bug is fixed with Server Stats take a look at the [bugfix source code directory](https://code.lostluma.net/LostLuma/server-stats/src/branch/stardust/module/bugfix).

> [!WARNING]
> Server Stats disallows putting items back into the furnace result slot in Beta 1.5 and before.

> [!WARNING]
> Infdev versions from `20100327` to `20100608` do not support custom game directories, causing issues  
> when using multiple instances. Server Stats sets the game directory to the instance directory on startup.

### Data Upgrade

As Server Stats is also intended to be used by players eventually upgrading to Minecraft 1.8 (and beyond!)  
it also includes a statistics upgrade system to convert ID-based statistics to resource location based ones.

## Information

To see your own statistics you can either install Server Stats on the client as well as the server to sync  
them whenever you join a single- or multiplayer world using the mod or update to Minecraft 1.7.x or above.

> [!TIP]  
> To see your own statistics in 11w48a (1.1 Snapshot) and earlier install [OSL](https://modrinth.com/mod/osl) on both the client and server.

## Installation

Server Stats runs on Fabric and Quilt Loader using the [Ornithe Toolchain](https://ornithemc.net/) for legacy versions.  
To install the mod use the Ornithe installer, then simply drop Server Stats into your `mods` folder.

You can download the release version of Server Stats from either [Modrinth](https://modrinth.com/mod/server-stats) or [code.lostluma.net](https://code.lostluma.net/LostLuma/server-stats/releases).

## Supported Minecraft Versions

Server Stats supports a wide range of Minecraft versions including most snapshots:

- Infdev 20100327 to 12w17a (Release 1.3 Snapshot)
- Release 1.3.2 to Release 1.12.2

However, non-mainline versions (such as April Fools) are currently not supported.  
The mod may function on these versions without issue, but they have not been validated.

> [!WARNING]
> Collection of some vanilla statistics, such as items crafted, is not supported on Alpha servers.

## Mod Support

Server Stats is intended to be usable in conjunction with mods adding their own statistics by default.

### Custom Vanilla Stats

All vanilla statistics and achievements added by other mods will be saved and synchronized by the mod,  
if they have been properly registered using `Stat#register()` and are awarded on the dedicated server.

> [!WARNING]
> Due to the `Item.onResult` method over-counting crafted item amounts in versions 12w24a (1.2 Snapshot)  
> and before Server Stats removes its function in counting statistics and has implemented it differently  
> for vanilla crafting interfaces (the inventory, crafting table, and furnace). Mods adding new crafting  
> methods will instead need to call `PlayerEntity.incrementStat` with the real amount crafted themselves.

### Combat Statistics Screen

Statistics for killing and being killed by mobs added by mods are automatically collected by Server Stats.  
However, by default Server Stats will not display a mob face in the mob stats screen for mobs it doesn't know.

You can change this by shipping a texture in this location: ``assets/server_stats/textures/mob_face/mob_name.png``

### Using the Server Stats API

Coming soon :3

### Incompatible Mods

- [Achievement Fix](https://modrinth.com/mod/achievement-fix): Server Stats contains the same (and more) bug fixes

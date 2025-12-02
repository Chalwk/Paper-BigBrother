# BigBrother

<img src="assets/logo.png" alt="BigBrother" width="256" height="256">

Monitor commands, signs, anvils, books, and portals with fine-grained control and customizable filters.

![Paper](https://img.shields.io/badge/Paper-1.21.4-green?logo=paper&logoColor=white)
[![MIT License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Latest Release](https://img.shields.io/github/v/release/Chalwk/Paper-BigBrother?sort=semver)](https://github.com/Chalwk/Paper-BigBrother/releases/latest)

## Features

- **CommandSpy**: Monitor all commands executed by players with configurable exclusions
- **SignSpy**: Track sign interactions and edits, displaying written content
- **AnvilSpy**: Monitor anvil usage with item renaming information
- **BookSpy**: Track book writing and editing activities
- **PortalSpy**: Monitor portal travel between dimensions
- **Granular Permissions**: Fine-grained control over each spy feature
- **Configurable Filters**: Exclude specific commands, players, and worlds
- **Custom Notifications**: Tailored notification formats for each spy type
- **Global & Individual Toggles**: Enable/disable entire system or specific features
- **Player Management**: Toggle spy features for individual players

## Commands

### Global Management

- `/bigbrother` or `/bb` - Toggle all BigBrother spy features for yourself
- `/bigbrother reload` - Reload the plugin configuration
- `/bigbrother status` - Check which spy features you have enabled

### Spy Feature Toggles

- `/bigbrother commands` - Toggle command spy for yourself
- `/bigbrother commands <player>` - Toggle command spy for another player
- `/bigbrother signs` - Toggle sign spy for yourself
- `/bigbrother signs <player>` - Toggle sign spy for another player
- `/bigbrother anvils` - Toggle anvil spy for yourself
- `/bigbrother anvils <player>` - Toggle anvil spy for another player
- `/bigbrother books` - Toggle book spy for yourself
- `/bigbrother books <player>` - Toggle book spy for another player
- `/bigbrother portals` - Toggle portal spy for yourself
- `/bigbrother portals <player>` - Toggle portal spy for another player

## Permissions

### Main Permissions

- `bigbrother.use` - Allows using BigBrother commands (default: op)
- `bigbrother.reload` - Allows reloading configuration (default: op)
- `bigbrother.*` - Wildcard for all BigBrother permissions (default: op)

### Spy Feature Permissions

#### Command Spy

- `bigbrother.commandspy.toggle` - Toggle command spy for self
- `bigbrother.commandspy.toggle.others` - Toggle command spy for others

#### Sign Spy

- `bigbrother.signspy.toggle` - Toggle sign spy for self
- `bigbrother.signspy.toggle.others` - Toggle sign spy for others

#### Anvil Spy

- `bigbrother.anvilspy.toggle` - Toggle anvil spy for self
- `bigbrother.anvilspy.toggle.others` - Toggle anvil spy for others

#### Book Spy

- `bigbrother.bookspy.toggle` - Toggle book spy for self
- `bigbrother.bookspy.toggle.others` - Toggle book spy for others

#### Portal Spy

- `bigbrother.portalspy.toggle` - Toggle portal spy for self
- `bigbrother.portalspy.toggle.others` - Toggle portal spy for others

### Permission Groups

- `bigbrother.spy.*` - All spy features
- `bigbrother.commandspy.*` - All command spy permissions
- `bigbrother.signspy.*` - All sign spy permissions
- `bigbrother.anvilspy.*` - All anvil spy permissions
- `bigbrother.bookspy.*` - All book spy permissions
- `bigbrother.portalspy.*` - All portal spy permissions

## Configuration

### Global Settings

```yaml
# Enable/disable BigBrother globally
global_toggle: true

# Whether BigBrother is enabled for players by default
enabled_by_default: true
```

### Spy Feature Settings

Each spy feature can be individually configured:

```yaml
spy:
  command:
    enabled: true
    notification: "&7[&eCommandSpy&7] &f{player} &7{action}"
    permission: "bigbrother.commandspy.toggle"

  sign:
    enabled: true
    notification: "&7[&aSignSpy&7] &f{player} &7{action}"
    permission: "bigbrother.signspy.toggle"

  anvil:
    enabled: true
    notification: "&7[&6AnvilSpy&7] &f{player} &7{action}"
    permission: "bigbrother.anvilspy.toggle"

  book:
    enabled: true
    notification: "&7[&dBookSpy&7] &f{player} &7{action}"
    permission: "bigbrother.bookspy.toggle"

  portal:
    enabled: true
    notification: "&7[&5PortalSpy&7] &f{player} &7{action}"
    permission: "bigbrother.portalspy.toggle"
```

### Filter Settings

```yaml
filters:
  # Commands that won't trigger CommandSpy notifications
  excluded_commands:
    - "msg"
    - "w"
    - "tell"
    - "whisper"
    - "me"
    - "say"
    - "help"
    - "?"

  # Players who won't trigger any spy notifications
  excluded_players: [ ]

  # Worlds where spy features won't trigger
  excluded_worlds:
    - "world_nether"
    - "world_the_end"
```

### Messages

```yaml
messages:
  no_permission: "&cYou don't have permission to use this feature!"
  player_not_found: "&cPlayer not found!"
  reloaded: "&aConfiguration reloaded!"
  usage: |
    &eBigBrother Commands:
    &7/bigbrother &8- &fToggle all spy features
    &7/bigbrother reload &8- &fReload configuration
    &7/bigbrother status &8- &fCheck your spy status
    &7/bigbrother commands [player] &8- &fToggle command spy
    &7/bigbrother signs [player] &8- &fToggle sign spy
    &7/bigbrother anvils [player] &8- &fToggle anvil spy
    &7/bigbrother books [player] &8- &fToggle book spy
    &7/bigbrother portals [player] &8- &fToggle portal spy
```

### Available Placeholders

- `{player}` - The player performing the action
- `{action}` - Description of the action performed
- `{command}` - Command executed (for CommandSpy)
- `{lines}` - Sign text (for SignSpy)
- `{old_name}` → `{new_name}` - Item renaming (for AnvilSpy)
- `{title}` - Book title (for BookSpy)
- `{from_world}` → `{to_world}` - World travel (for PortalSpy)

## Usage Examples

### Basic Usage

```bash
# Enable all spy features for yourself
/bigbrother

# Check which features are enabled
/bigbrother status

# Monitor commands from a suspicious player
/bigbrother commands Notch

# Monitor sign writing activities
/bigbrother signs

# Track anvil usage for item renaming
/bigbrother anvils

# Watch book writing activities
/bigbrother books

# Monitor portal travel between dimensions
/bigbrother portals
```

### Administrator Usage

```bash
# Reload configuration after changes
/bigbrother reload

# Toggle command spy for another player
/bigbrother commands Alex

# Toggle multiple features for another player
/bigbrother signs Steve
/bigbrother anvils Steve

# Disable BigBrother for yourself
/bigbrother
```

### Scenario Examples

```bash
# Investigating suspicious activity
/bigbrother commands Herobrine
/bigbrother signs Herobrine
/bigbrother portals Herobrine

# Monitoring new staff members
/bigbrother commands NewModerator
/bigbrother books NewModerator

# Tracking item duplication attempts
/bigbrother anvils SuspiciousPlayer
/bigbrother books SuspiciousPlayer

# World-specific monitoring
# (Adjust excluded_worlds in config for this)
/bigbrother commands PlayerInNether
/bigbrother portals PlayerInNether
```

## Installation

### Prerequisites

- Java 21 or higher
- Paper 1.21.4 or compatible server

### Installation Steps

1. **Download** the latest JAR file from the [Releases](../../releases) section
2. **Place** the JAR file in your server's `plugins` folder
3. **Restart** your server to generate configuration files
4. **Configure** the plugin by editing `plugins/BigBrother/config.yml`
5. **Set Permissions** using your preferred permission system (LuckPerms recommended)
6. **Test** the features with appropriate permissions

### Building from Source

```bash
# Clone the repository
git clone https://github.com/Chalwk/Paper-BigBrother.git

# Navigate to project directory
cd Paper-BigBrother

# Build the plugin
./gradlew build

# Find the JAR in: build/libs/BigBrother-1.0.0.jar
```

## Customization

### Adding Custom Filters

Extend the filter system to suit your server's needs:

```yaml
filters:
  excluded_commands:
    - "msg"
    - "tell"
    - "whisper"
    - "me"
    - "say"
    - "help"
    - "?"
    # Add more commands as needed
    - "mail"
    - "message"
    - "pm"

  excluded_players:
    - "TrustedAdmin"
    - "ServerOwner"
    - "BotPlayer"

  excluded_worlds:
    - "creative_world"
    - "minigames_world"
    - "resource_world"
```

### Custom Notification Formats

Customize notification appearance for each spy type:

```yaml
spy:
  command:
    notification: "&8[&e⚠&8] &6CMD &8| &f{player} &7→ &e{command}"

  sign:
    notification: "&8[&a📝&8] &2SIGN &8| &f{player} &7wrote: &f{lines}"

  anvil:
    notification: "&8[&6⚒&8] &6ANVIL &8| &f{player} &7renamed: &e{old_name} &7→ &a{new_name}"

  book:
    notification: "&8[&d📖&8] &5BOOK &8| &f{player} &7wrote book: &d{title}"

  portal:
    notification: "&8[&5🌀&8] &5PORTAL &8| &f{player} &7traveled: &e{from_world} &7→ &a{to_world}"
```

### Color Codes Reference

Use Minecraft color codes (&) in your format strings:

- `&0` - Black
- `&1` - Dark Blue
- `&2` - Dark Green
- `&3` - Dark Aqua
- `&4` - Dark Red
- `&5` - Dark Purple
- `&6` - Gold
- `&7` - Gray
- `&8` - Dark Gray
- `&9` - Blue
- `&a` - Green
- `&b` - Aqua
- `&c` - Red
- `&d` - Light Purple
- `&e` - Yellow
- `&f` - White
- `&k` - Obfuscated
- `&l` - Bold
- `&m` - Strikethrough
- `&n` - Underline
- `&o` - Italic
- `&r` - Reset

## License

BigBrother is licensed under the [MIT License](LICENSE).
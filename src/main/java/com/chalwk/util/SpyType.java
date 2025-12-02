package com.chalwk.util;

public enum SpyType {
    COMMAND("commands", "bigbrother.commandspy.toggle", "bigbrother.commandspy.toggle.others"),
    SIGN("signs", "bigbrother.signspy.toggle", "bigbrother.signspy.toggle.others"),
    ANVIL("anvils", "bigbrother.anvilspy.toggle", "bigbrother.anvilspy.toggle.others"),
    BOOK("books", "bigbrother.bookspy.toggle", "bigbrother.bookspy.toggle.others"),
    PORTAL("portals", "bigbrother.portalspy.toggle", "bigbrother.portalspy.toggle.others");

    private final String command;
    private final String permission;
    private final String permissionOthers;

    SpyType(String command, String permission, String permissionOthers) {
        this.command = command;
        this.permission = permission;
        this.permissionOthers = permissionOthers;
    }

    public static SpyType fromCommand(String command) {
        for (SpyType type : values()) {
            if (type.getCommand().equalsIgnoreCase(command)) {
                return type;
            }
        }
        return null;
    }

    public String getCommand() {
        return command;
    }

    public String getPermission() {
        return permission;
    }

    public String getPermissionOthers() {
        return permissionOthers;
    }
}
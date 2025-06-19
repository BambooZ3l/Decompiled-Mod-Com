/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.annotation.config.ConfigFile
 *  com.hrznstudio.titanium.annotation.config.ConfigVal
 *  com.hrznstudio.titanium.annotation.config.ConfigVal$InRangeInt
 */
package com.buuz135.portality.proxy;

import com.hrznstudio.titanium.annotation.config.ConfigFile;
import com.hrznstudio.titanium.annotation.config.ConfigVal;

@ConfigFile
public class PortalityConfig {
    @ConfigVal(comment="The amount of energy it will be consumed to teleport an entity")
    @ConfigVal.InRangeInt(min=1)
    public static int TELEPORT_ENERGY_AMOUNT = 500;
    @ConfigVal(comment="If true players will get the wither effect if there isn't enough power to teleport")
    public static boolean HURT_PLAYERS = true;
    @ConfigVal(comment="If true players will be launched out of the portal instead of standing still in front of it")
    public static boolean LAUNCH_PLAYERS = true;
    @ConfigVal(comment="How long the portal structure it can be")
    public static int MAX_PORTAL_LENGTH = 16;
    @ConfigVal(comment="How wide a portal can be without counting the controller(radius)")
    @ConfigVal.InRangeInt(min=1)
    public static int MAX_PORTAL_WIDTH = 7;
    @ConfigVal(comment="How tall a portal can be (diameter)")
    @ConfigVal.InRangeInt(min=3)
    public static int MAX_PORTAL_HEIGHT = 15;
    @ConfigVal(comment="Portal energy buffer")
    @ConfigVal.InRangeInt(min=1)
    public static int MAX_PORTAL_POWER = 100000;
    @ConfigVal(comment="Portal energy buffer insertion rate")
    @ConfigVal.InRangeInt(min=1)
    public static int MAX_PORTAL_POWER_IN = 2000;
    @ConfigVal(comment="How much power it will be consumed to open the portal interdimensionally")
    @ConfigVal.InRangeInt(min=1)
    public static int PORTAL_POWER_OPEN_INTERDIMENSIONAL = 10000;
    @ConfigVal(comment="How much power it will be consumed/tick based on the portal length and if it is the caller. (portalLength*ThisValue). If it is the portal the created the link the power will be double")
    @ConfigVal.InRangeInt(min=1)
    public static int POWER_PORTAL_TICK = 1;
    @ConfigVal(comment="Max distance multiplier that a portal can be linked, based on length. PortalLength*ThisValue")
    @ConfigVal.InRangeInt(min=1)
    public static int DISTANCE_MULTIPLIER = 200;
}


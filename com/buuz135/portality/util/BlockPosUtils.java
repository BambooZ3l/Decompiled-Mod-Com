/*
 * Decompiled with CFR 0.152.
 */
package com.buuz135.portality.util;

import com.buuz135.portality.proxy.PortalityConfig;

public class BlockPosUtils {
    public static int getMaxDistance(int length) {
        return length * PortalityConfig.DISTANCE_MULTIPLIER;
    }
}


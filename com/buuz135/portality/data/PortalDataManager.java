/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.saveddata.SavedData
 */
package com.buuz135.portality.data;

import com.buuz135.portality.data.PortalInformation;
import com.buuz135.portality.data.PortalLinkData;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;

public class PortalDataManager
extends SavedData {
    public static final String NAME = "Portality";
    private List<PortalInformation> informationList = new ArrayList<PortalInformation>();

    public static void addInformation(Level world, PortalInformation information) {
        if (world instanceof ServerLevel) {
            PortalDataManager dataManager = PortalDataManager.getData((LevelAccessor)world);
            dataManager.getInformationList().add(information);
            dataManager.m_77762_();
        }
    }

    public static void removeInformation(LevelAccessor world, BlockPos blockPos) {
        if (world instanceof ServerLevel) {
            PortalDataManager dataManager = PortalDataManager.getData(world);
            dataManager.getInformationList().removeIf(information1 -> information1.getLocation().equals((Object)blockPos));
            dataManager.m_77762_();
        }
    }

    @Nullable
    public static PortalInformation getInfoFromID(Level world, UUID uuid) {
        PortalDataManager dataManager = PortalDataManager.getData((LevelAccessor)world);
        if (dataManager != null) {
            for (PortalInformation information : dataManager.getInformationList()) {
                if (!information.getId().equals(uuid)) continue;
                return information;
            }
        }
        return null;
    }

    @Nullable
    public static PortalInformation getInfoFromPos(Level world, BlockPos pos) {
        PortalDataManager dataManager = PortalDataManager.getData((LevelAccessor)world);
        if (dataManager != null) {
            for (PortalInformation information : dataManager.getInformationList()) {
                if (!information.getLocation().equals((Object)pos)) continue;
                return information;
            }
        }
        return null;
    }

    @Nullable
    public static PortalInformation getInfoFromLink(Level world, PortalLinkData data) {
        PortalDataManager dataManager = PortalDataManager.getData((LevelAccessor)world);
        for (PortalInformation information : dataManager.getInformationList()) {
            if (information.getDimension() != data.getDimension() || !information.getLocation().equals((Object)data.getPos())) continue;
            return information;
        }
        return null;
    }

    public static void setPortalPrivacy(Level world, BlockPos pos, boolean privacy) {
        PortalDataManager dataManager = PortalDataManager.getData((LevelAccessor)world);
        if (dataManager != null) {
            for (PortalInformation information : dataManager.getInformationList()) {
                if (!information.getLocation().equals((Object)pos)) continue;
                information.setPrivate(privacy);
                dataManager.m_77762_();
            }
        }
    }

    public static void setPortalName(Level world, BlockPos pos, String name) {
        PortalDataManager dataManager = PortalDataManager.getData((LevelAccessor)world);
        if (dataManager != null) {
            for (PortalInformation information : dataManager.getInformationList()) {
                if (!information.getLocation().equals((Object)pos)) continue;
                information.setName(name);
                dataManager.m_77762_();
            }
        }
    }

    public static void setPortalInterdimensional(Level world, BlockPos pos, boolean interdimensional) {
        PortalDataManager dataManager = PortalDataManager.getData((LevelAccessor)world);
        if (dataManager != null) {
            for (PortalInformation information : dataManager.getInformationList()) {
                if (!information.getLocation().equals((Object)pos)) continue;
                information.setInterdimensional(interdimensional);
                dataManager.m_77762_();
            }
        }
    }

    public static void setPortalDisplay(Level world, BlockPos pos, ItemStack stack) {
        PortalDataManager dataManager = PortalDataManager.getData((LevelAccessor)world);
        if (dataManager != null) {
            for (PortalInformation information : dataManager.getInformationList()) {
                if (!information.getLocation().equals((Object)pos)) continue;
                information.setDisplay(stack);
                dataManager.m_77762_();
            }
        }
    }

    @Nullable
    public static PortalDataManager getData(LevelAccessor world) {
        if (world instanceof ServerLevel) {
            ServerLevel serverWorld = ((ServerLevel)world).m_142572_().m_129880_(Level.f_46428_);
            PortalDataManager data = (PortalDataManager)serverWorld.m_8895_().m_164861_(PortalDataManager::load, PortalDataManager::new, NAME);
            return data;
        }
        return null;
    }

    public static void setActiveStatus(Level world, BlockPos pos, boolean active) {
        PortalDataManager dataManager = PortalDataManager.getData((LevelAccessor)world);
        if (dataManager != null) {
            for (PortalInformation information : dataManager.getInformationList()) {
                if (!information.getLocation().equals((Object)pos)) continue;
                information.setActive(active);
                dataManager.m_77762_();
            }
        }
    }

    public static PortalDataManager load(CompoundTag nbt) {
        PortalDataManager portalDataManager = new PortalDataManager();
        CompoundTag root = nbt.m_128469_(NAME);
        for (String key : root.m_128431_()) {
            CompoundTag info = root.m_128469_(key);
            portalDataManager.getInformationList().add(PortalInformation.readFromNBT(info));
        }
        return portalDataManager;
    }

    public CompoundTag m_7176_(CompoundTag compound) {
        CompoundTag tag = new CompoundTag();
        for (PortalInformation information : this.informationList) {
            tag.m_128365_(information.getId().toString(), (Tag)information.writetoNBT());
        }
        compound.m_128365_(NAME, (Tag)tag);
        return compound;
    }

    public List<PortalInformation> getInformationList() {
        return this.informationList;
    }
}


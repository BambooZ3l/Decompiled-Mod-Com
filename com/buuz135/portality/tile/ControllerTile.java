/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hrznstudio.titanium.block.BasicTileBlock
 *  com.hrznstudio.titanium.block.tile.ActiveTile
 *  com.hrznstudio.titanium.block.tile.PoweredTile
 *  com.hrznstudio.titanium.client.screen.addon.StateButtonInfo
 *  com.hrznstudio.titanium.component.energy.EnergyStorageComponent
 *  javax.annotation.Nonnull
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.AABB
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.RegistryObject
 */
package com.buuz135.portality.tile;

import com.buuz135.portality.block.ControllerBlock;
import com.buuz135.portality.block.module.IPortalModule;
import com.buuz135.portality.data.PortalDataManager;
import com.buuz135.portality.data.PortalInformation;
import com.buuz135.portality.data.PortalLinkData;
import com.buuz135.portality.gui.ChangeColorScreen;
import com.buuz135.portality.gui.ControllerScreen;
import com.buuz135.portality.gui.PortalsScreen;
import com.buuz135.portality.gui.RenameControllerScreen;
import com.buuz135.portality.gui.button.PortalSettingButton;
import com.buuz135.portality.gui.button.TextPortalButton;
import com.buuz135.portality.handler.ChunkLoaderHandler;
import com.buuz135.portality.handler.StructureHandler;
import com.buuz135.portality.handler.TeleportHandler;
import com.buuz135.portality.network.PortalNetworkMessage;
import com.buuz135.portality.proxy.CommonProxy;
import com.buuz135.portality.proxy.PortalityConfig;
import com.buuz135.portality.proxy.PortalitySoundHandler;
import com.buuz135.portality.proxy.client.IPortalColor;
import com.buuz135.portality.proxy.client.TickeableSound;
import com.buuz135.portality.util.BlockPosUtils;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block.tile.ActiveTile;
import com.hrznstudio.titanium.block.tile.PoweredTile;
import com.hrznstudio.titanium.client.screen.addon.StateButtonInfo;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;

public class ControllerTile
extends PoweredTile<ControllerTile>
implements IPortalColor {
    private static String NBT_FORMED = "Formed";
    private static String NBT_LENGTH = "Length";
    private static String NBT_WIDTH = "Width";
    private static String NBT_HEIGHT = "Height";
    private static String NBT_PORTAL = "Portal";
    private static String NBT_LINK = "Link";
    private static String NBT_DISPLAY = "Display";
    private static String NBT_ONCE = "Once";
    private static String NBT_COLOR = "Color";
    private static String NBT_TOKENS = "Tokens";
    private boolean isFormed = false;
    private boolean onceCall = false;
    private boolean display = true;
    private PortalInformation information;
    private PortalLinkData linkData;
    private int color;
    private HashMap<String, CompoundTag> teleportationTokens = new LinkedHashMap<String, CompoundTag>();
    @OnlyIn(value=Dist.CLIENT)
    private TickeableSound sound;
    private TeleportHandler teleportHandler;
    private StructureHandler structureHandler;

    public ControllerTile(BlockPos pos, BlockState state) {
        super((BasicTileBlock)((RegistryObject)CommonProxy.BLOCK_CONTROLLER.getLeft()).get(), (BlockEntityType)((RegistryObject)CommonProxy.BLOCK_CONTROLLER.getRight()).get(), pos, state);
        this.color = Integer.parseInt("0094ff", 16);
        this.teleportHandler = new TeleportHandler(this);
        this.structureHandler = new StructureHandler(this);
        this.setShowEnergy(false);
        this.addButton(new PortalSettingButton(-22, 12, () -> () -> OpenGui.open(1, this), new StateButtonInfo[]{new StateButtonInfo(0, PortalSettingButton.RENAME, new String[]{"portality.display.change_name"})}){

            @Override
            public int getState() {
                return 0;
            }
        }.setId(1));
        this.addButton(new PortalSettingButton(-22, 78, () -> () -> OpenGui.open(3, this), new StateButtonInfo[]{new StateButtonInfo(0, PortalSettingButton.CHANGE_COLOR, new String[]{"portality.display.change_color"})}){

            @Override
            public int getState() {
                return 0;
            }
        }.setId(5));
        this.addButton(new TextPortalButton(5, 90, 80, 16, "portality.display.call_portal").setClientConsumer(() -> screen -> OpenGui.open(2, this)).setId(4).setPredicate((playerEntity, compoundNBT) -> PortalNetworkMessage.sendInformationToPlayer((ServerPlayer)playerEntity, this.isInterdimensional(), this.m_58899_(), BlockPosUtils.getMaxDistance(this.getLength()), this.teleportationTokens)));
        this.addButton(new PortalSettingButton(-22, 34, () -> () -> {}, new StateButtonInfo[]{new StateButtonInfo(0, PortalSettingButton.PUBLIC, new String[]{"portality.display.make_private"}), new StateButtonInfo(1, PortalSettingButton.PRIVATE, new String[]{"portality.display.make_public"})}){

            @Override
            public int getState() {
                return ControllerTile.this.information != null && ControllerTile.this.information.isPrivate() ? 1 : 0;
            }
        }.setPredicate((playerEntity, compoundNBT) -> {
            if (this.information.getOwner().equals(playerEntity.m_142081_())) {
                this.togglePrivacy();
            }
        }).setId(2));
        this.addButton(new PortalSettingButton(-22, 56, () -> () -> {}, new StateButtonInfo[]{new StateButtonInfo(0, PortalSettingButton.NAME_SHOWN, new String[]{"portality.display.hide_name"}), new StateButtonInfo(1, PortalSettingButton.NAME_HIDDEN, new String[]{"portality.display.show_name"})}){

            @Override
            public int getState() {
                return ControllerTile.this.display ? 0 : 1;
            }
        }.setPredicate((playerEntity, compoundNBT) -> {
            if (this.information.getOwner().equals(playerEntity.m_142081_())) {
                this.setDisplayNameEnabled(!this.isDisplayNameEnabled());
            }
        }).setId(3));
        this.addButton(new TextPortalButton(90, 90, 80, 16, "portality.display.close_portal").setPredicate((playerEntity, compoundNBT) -> this.closeLink()).setId(5));
    }

    public void serverTick(Level level, BlockPos pos, BlockState state, ControllerTile blockEntity) {
        super.serverTick(level, pos, state, (ActiveTile)blockEntity);
        if (this.isActive()) {
            this.teleportHandler.tick();
            if (this.linkData != null) {
                for (Entity entity : this.f_58857_.m_45976_(Entity.class, this.getPortalArea())) {
                    this.teleportHandler.addEntityToTeleport(entity, this.linkData);
                }
            }
            this.workModules();
        }
        if (this.isActive() && this.linkData != null) {
            this.getEnergyStorage().extractEnergy((this.linkData.isCaller() ? 2 : 1) * this.structureHandler.getLength() * PortalityConfig.POWER_PORTAL_TICK, false);
            if (this.getEnergyStorage().getEnergyStored() == 0 || !this.isFormed) {
                this.closeLink();
            }
        }
        if (this.f_58857_.m_46467_() % 10L == 0L) {
            if (this.structureHandler.shouldCheckForStructure()) {
                this.isFormed = this.structureHandler.checkArea();
                if (this.isFormed) {
                    this.structureHandler.setShouldCheckForStructure(false);
                    PortalDataManager.setPortalInterdimensional(this.f_58857_, this.f_58858_, this.structureHandler.getModules().stream().map(blockPos -> this.f_58857_.m_8055_(blockPos)).filter(blockState -> blockState.m_60734_() instanceof IPortalModule && ((IPortalModule)blockState.m_60734_()).allowsInterdimensionalTravel()).count() > 0L);
                } else {
                    this.structureHandler.cancelFrameBlocks();
                }
            }
            if (this.isFormed) {
                this.getPortalInfo();
                if (this.linkData != null) {
                    BlockEntity tileEntity;
                    ChunkLoaderHandler.addPortalAsChunkloader(this);
                    if (this.f_58857_.m_142572_() == null || this.f_58857_.m_142572_().m_129880_(this.linkData.getDimension()) == null) {
                        this.closeLink();
                    } else if (!(this.linkData.isToken() || (tileEntity = this.f_58857_.m_142572_().m_129880_(this.linkData.getDimension()).m_7702_(this.linkData.getPos())) instanceof ControllerTile && ((ControllerTile)tileEntity).getLinkData() != null && ((ControllerTile)tileEntity).getLinkData().getDimension().equals((Object)this.f_58857_.m_46472_()) && ((ControllerTile)tileEntity).getLinkData().getPos().equals((Object)this.f_58858_))) {
                        this.closeLink();
                    }
                }
            }
            this.markForUpdate();
        }
    }

    public void clientTick(Level level, BlockPos pos, BlockState state, ControllerTile blockEntity) {
        super.clientTick(level, pos, state, (BlockEntity)blockEntity);
        if (this.isActive()) {
            this.teleportHandler.tick();
            if (this.linkData != null) {
                for (Entity entity : this.f_58857_.m_45976_(Entity.class, this.getPortalArea())) {
                    this.teleportHandler.addEntityToTeleport(entity, this.linkData);
                }
            }
        }
        this.tickSound();
    }

    @Nonnull
    public ControllerTile getSelf() {
        return this;
    }

    @OnlyIn(value=Dist.CLIENT)
    private void tickSound() {
        if (this.isActive()) {
            if (this.sound == null) {
                this.sound = new TickeableSound(this.f_58857_, this.f_58858_, (SoundEvent)PortalitySoundHandler.PORTAL.get());
                Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)this.sound);
            } else {
                this.sound.increase();
            }
        } else if (this.sound != null) {
            if (this.sound.m_7783_() > 0.0f) {
                this.sound.decrease();
            } else {
                this.sound.setDone();
                this.sound = null;
            }
        }
    }

    protected void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128379_(NBT_FORMED, this.isFormed);
        compound.m_128405_(NBT_LENGTH, this.structureHandler.getLength());
        compound.m_128405_(NBT_WIDTH, this.structureHandler.getWidth());
        compound.m_128405_(NBT_HEIGHT, this.structureHandler.getHeight());
        compound.m_128379_(NBT_ONCE, this.onceCall);
        compound.m_128379_(NBT_DISPLAY, this.display);
        compound.m_128405_(NBT_COLOR, this.color);
        if (this.information != null) {
            compound.m_128365_(NBT_PORTAL, (Tag)this.information.writetoNBT());
        }
        if (this.linkData != null) {
            compound.m_128365_(NBT_LINK, (Tag)this.linkData.writeToNBT());
        }
        CompoundTag tokens = new CompoundTag();
        for (String s : this.teleportationTokens.keySet()) {
            tokens.m_128365_(s, (Tag)this.teleportationTokens.get(s));
        }
        compound.m_128365_(NBT_TOKENS, (Tag)tokens);
    }

    public void m_142466_(CompoundTag compound) {
        this.isFormed = compound.m_128471_(NBT_FORMED);
        this.structureHandler.setLength(compound.m_128451_(NBT_LENGTH));
        this.structureHandler.setWidth(compound.m_128451_(NBT_WIDTH));
        this.structureHandler.setHeight(compound.m_128451_(NBT_HEIGHT));
        if (compound.m_128441_(NBT_PORTAL)) {
            this.information = PortalInformation.readFromNBT(compound.m_128469_(NBT_PORTAL));
        }
        if (compound.m_128441_(NBT_LINK)) {
            this.linkData = PortalLinkData.readFromNBT(compound.m_128469_(NBT_LINK));
        }
        this.onceCall = compound.m_128471_(NBT_ONCE);
        this.display = compound.m_128471_(NBT_DISPLAY);
        if (compound.m_128441_(NBT_COLOR)) {
            this.color = compound.m_128451_(NBT_COLOR);
        }
        this.teleportationTokens = new LinkedHashMap<String, CompoundTag>();
        if (compound.m_128441_(NBT_TOKENS)) {
            CompoundTag tokens = compound.m_128469_(NBT_TOKENS);
            for (String s : tokens.m_128431_()) {
                this.teleportationTokens.put(s, tokens.m_128469_(s));
            }
        }
        super.m_142466_(compound);
    }

    public void breakController() {
        this.closeLink();
        this.structureHandler.cancelFrameBlocks();
    }

    private void workModules() {
        boolean interdimensional = false;
        for (BlockPos pos : this.structureHandler.getModules()) {
            Block block = this.f_58857_.m_8055_(pos).m_60734_();
            if (!(block instanceof IPortalModule)) continue;
            if (((IPortalModule)block).allowsInterdimensionalTravel()) {
                interdimensional = true;
            }
            if (!this.isActive()) continue;
            ((IPortalModule)block).work(this, pos);
        }
        PortalDataManager.setPortalInterdimensional(this.f_58857_, this.f_58858_, interdimensional);
    }

    public AABB getPortalArea() {
        if (!(this.f_58857_.m_8055_(this.f_58858_).m_60734_() instanceof ControllerBlock)) {
            return new AABB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        }
        Direction facing = (Direction)this.f_58857_.m_8055_(this.f_58858_).m_61143_((Property)ControllerBlock.FACING_HORIZONTAL);
        BlockPos corner1 = this.f_58858_.m_5484_(facing.m_122427_(), this.structureHandler.getWidth()).m_142300_(Direction.UP);
        BlockPos corner2 = this.f_58858_.m_5484_(facing.m_122428_(), this.structureHandler.getWidth()).m_5484_(Direction.UP, this.structureHandler.getHeight() - 1).m_5484_(facing.m_122424_(), this.structureHandler.getLength() - 1);
        return new AABB(corner1, corner2);
    }

    public AABB getRenderBoundingBox() {
        return this.getPortalArea();
    }

    private void getPortalInfo() {
        this.information = PortalDataManager.getInfoFromPos(this.f_58857_, this.f_58858_);
        this.markForUpdate();
    }

    public void togglePrivacy() {
        PortalDataManager.setPortalPrivacy(this.f_58857_, this.f_58858_, !this.information.isPrivate());
        this.getPortalInfo();
        this.markForUpdate();
    }

    public boolean isFormed() {
        return this.isFormed;
    }

    public boolean isPrivate() {
        return this.information != null && this.information.isPrivate();
    }

    public UUID getOwner() {
        if (this.information != null) {
            return this.information.getOwner();
        }
        return null;
    }

    public UUID getID() {
        if (this.information != null) {
            return this.information.getId();
        }
        return null;
    }

    public String getPortalDisplayName() {
        if (this.information != null) {
            return this.information.getName();
        }
        return "";
    }

    public void setDisplayName(String name) {
        if (this.information != null) {
            this.information.setName(name);
        }
    }

    public boolean isInterdimensional() {
        return this.information != null && this.information.isInterdimensional();
    }

    public ItemStack getDisplay() {
        if (this.information != null) {
            return this.information.getDisplay();
        }
        return ItemStack.f_41583_;
    }

    public void linkTo(PortalLinkData data, PortalLinkData.PortalCallType type) {
        if (type == PortalLinkData.PortalCallType.FORCE) {
            this.closeLink();
        }
        if (this.linkData != null) {
            return;
        }
        if (type == PortalLinkData.PortalCallType.SINGLE) {
            this.onceCall = true;
        }
        if (data.isCaller()) {
            ServerLevel world;
            BlockEntity entity;
            if (!data.isToken() && (entity = (world = this.f_58857_.m_142572_().m_129880_(data.getDimension())).m_7702_(data.getPos())) instanceof ControllerTile) {
                data.setName(((ControllerTile)entity).getPortalDisplayName());
                ((ControllerTile)entity).linkTo(new PortalLinkData((ResourceKey<Level>)this.f_58857_.m_46472_(), this.f_58858_, false, this.getPortalDisplayName(), false), type);
            }
            int power = PortalityConfig.PORTAL_POWER_OPEN_INTERDIMENSIONAL;
            if (data.getDimension().m_135782_().equals((Object)this.f_58857_.m_46472_().m_135782_())) {
                power = (int)this.f_58858_.m_123331_((Vec3i)data.getPos()) * this.structureHandler.getLength();
            }
            this.getEnergyStorage().extractEnergy(power, false);
        }
        PortalDataManager.setActiveStatus(this.f_58857_, this.f_58858_, true);
        this.linkData = data;
        this.markForUpdate();
    }

    public void closeLink() {
        if (this.linkData != null) {
            BlockEntity entity;
            ServerLevel world;
            PortalDataManager.setActiveStatus(this.f_58857_, this.f_58858_, false);
            if (!this.linkData.isToken() && (world = this.f_58857_.m_142572_().m_129880_(this.linkData.getDimension())) != null && (entity = world.m_7702_(this.linkData.getPos())) instanceof ControllerTile) {
                this.linkData = null;
                ((ControllerTile)entity).closeLink();
            }
            this.linkData = null;
        }
        ChunkLoaderHandler.removePortalAsChunkloader(this);
        this.markForUpdate();
    }

    public boolean isActive() {
        return this.information != null && this.information.isActive();
    }

    public PortalLinkData getLinkData() {
        return this.linkData;
    }

    public boolean isDisplayNameEnabled() {
        return this.display;
    }

    public void setDisplayNameEnabled(ItemStack display) {
        PortalDataManager.setPortalDisplay(this.f_58857_, this.f_58858_, display);
        this.getPortalInfo();
        this.markForUpdate();
    }

    public void setDisplayNameEnabled(boolean display) {
        this.display = display;
        this.markForUpdate();
    }

    public List<BlockPos> getModules() {
        return this.structureHandler.getModules();
    }

    public boolean teleportedEntity() {
        if (this.onceCall) {
            this.onceCall = false;
            this.closeLink();
            return true;
        }
        return false;
    }

    public boolean isShouldCheckForStructure() {
        return this.structureHandler.shouldCheckForStructure();
    }

    public void setShouldCheckForStructure(boolean shouldCheckForStructure) {
        this.structureHandler.setShouldCheckForStructure(shouldCheckForStructure);
    }

    public int getWidth() {
        return this.structureHandler.getWidth();
    }

    public int getHeight() {
        return this.structureHandler.getHeight();
    }

    public int getLength() {
        return this.structureHandler.getLength();
    }

    public PortalInformation getInformation() {
        this.getPortalInfo();
        return this.information;
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
        this.structureHandler.getModules().stream().filter(blockPos -> this.f_58857_.m_7702_(blockPos) instanceof IPortalColor).map(blockPos -> (IPortalColor)this.f_58857_.m_7702_(blockPos)).forEach(iPortalColor -> iPortalColor.setColor(color));
        this.structureHandler.getFrameBlocks().stream().filter(blockPos -> !(this.f_58857_.m_7702_(blockPos) instanceof ControllerTile)).filter(blockPos -> this.f_58857_.m_7702_(blockPos) instanceof IPortalColor).map(blockPos -> (IPortalColor)this.f_58857_.m_7702_(blockPos)).forEach(iPortalColor -> iPortalColor.setColor(color));
        this.markForUpdate();
    }

    @OnlyIn(value=Dist.CLIENT)
    public InteractionResult onActivated(Player playerIn, InteractionHand hand, Direction facing, double hitX, double hitY, double hitZ) {
        if (super.onActivated(playerIn, hand, facing, hitX, hitY, hitZ) != InteractionResult.SUCCESS) {
            Minecraft.m_91087_().m_18689_(() -> OpenGui.open(0, this));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    protected EnergyStorageComponent<ControllerTile> createEnergyStorage() {
        return new EnergyStorageComponent(PortalityConfig.MAX_PORTAL_POWER, PortalityConfig.MAX_PORTAL_POWER_IN, 10, 20);
    }

    public boolean addTeleportationToken(ItemStack stack) {
        this.teleportationTokens.put(stack.m_41786_().getString(), stack.m_41783_());
        this.markForUpdate();
        return true;
    }

    public HashMap<String, CompoundTag> getTeleportationTokens() {
        return this.teleportationTokens;
    }

    @OnlyIn(value=Dist.CLIENT)
    public static class OpenGui {
        public static void open(int id, ControllerTile controller) {
            switch (id) {
                case 0: {
                    Minecraft.m_91087_().m_91152_((Screen)new ControllerScreen(controller));
                    return;
                }
                case 1: {
                    Minecraft.m_91087_().m_91152_((Screen)new RenameControllerScreen(controller));
                    return;
                }
                case 2: {
                    Minecraft.m_91087_().m_91152_((Screen)new PortalsScreen(controller));
                    return;
                }
                case 3: {
                    Minecraft.m_91087_().m_91152_((Screen)new ChangeColorScreen(controller));
                    return;
                }
            }
        }
    }
}


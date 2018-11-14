/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility.glowstone_1_12;

import com.github.crashdemons.playerheads.ProfileUtils;
import com.github.crashdemons.playerheads.compatibility.CompatibilityProvider;
import com.github.crashdemons.playerheads.compatibility.SkullDetails;
import com.github.crashdemons.playerheads.compatibility.SkullType;
import com.github.crashdemons.playerheads.compatibility.Version;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import net.glowstone.entity.meta.profile.GlowPlayerProfile;
import net.glowstone.util.nbt.CompoundTag;
import com.destroystokyo.paper.profile.*;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * CompatibilityProvider Implementation for 1.12 support.
 * @author crashdemons (crashenator at gmail.com)
 */
@SuppressWarnings( "deprecation" )
public class Provider implements CompatibilityProvider {
    public Provider(){}
    @Override public String getType(){ return "glowstone"; }
    @Override public String getVersion(){ return "1.12"; }
    @Override public OfflinePlayer getOwningPlayerDirect(SkullMeta skullItemMeta){ return ProfileUtils.getProfilePlayer(skullItemMeta); }
    @Override public OfflinePlayer getOwningPlayerDirect(Skull skullBlockState){ return ProfileUtils.getProfilePlayer(skullBlockState); }
    @Override public String getOwnerDirect(SkullMeta skullItemMeta){ return skullItemMeta.getOwner(); }
    @Override public String getOwnerDirect(Skull skullBlockState){ return skullBlockState.getOwner(); }
    @Override public boolean setOwningPlayer(SkullMeta skullItemMeta, OfflinePlayer op){ return skullItemMeta.setOwner(op.getName()); }
    @Override public void    setOwningPlayer(Skull skullBlockState, OfflinePlayer op){ skullBlockState.setOwner(op.getName()); }
    @Override public boolean setOwner(SkullMeta skullItemMeta, String owner){ return skullItemMeta.setOwner(owner); }
    @Override public boolean setOwner(Skull skullBlockState, String owner){ return skullBlockState.setOwner(owner); }
    @Override public ItemStack getItemInMainHand(Player p){ return p.getEquipment().getItemInHand(); }
    @Override public void setItemInMainHand(Player p,ItemStack s){ p.getEquipment().setItemInHand(s); }
    @Override public SkullDetails getSkullDetails(SkullType type){ return new SkullDetails_18(type); }
    @Override public boolean getKeepInventory(World world){ return Boolean.valueOf(world.getGameRuleValue("keepInventory")); }
    @Override public SkullType getSkullType(ItemStack s){
        if(s.getType()!=Material.SKULL_ITEM) return null;
        short dmg = s.getDurability();
        try{
            return SkullType.values()[dmg];//TODO: needs testing !
        }catch(Exception e){
            return null;
        }
    }
    @Override public SkullType getSkullType(BlockState s){
        if(s.getType()!=Material.SKULL) return null;
        Skull skullState = (Skull) s;
        return adaptSkullType(skullState.getSkullType());
    }
    
    @Override public OfflinePlayer getOwningPlayer(SkullMeta skull){
        //OfflinePlayer op = getOwningPlayer(skull);//skullMeta.getOwningPlayer();
        //if(op!=null) return op;
        return ProfileUtils.getProfilePlayer(skull);//same method as above in 1.8 implementation
    }
    @Override public OfflinePlayer getOwningPlayer(Skull skull){
        //OfflinePlayer op = getOwningPlayer(skull);//skullMeta.getOwningPlayer();
        //if(op!=null) return op;
        return ProfileUtils.getProfilePlayer(skull);//same method as above in 1.8 implementation
    }
    
    @Override public String getOwner(SkullMeta skull){
        String owner=null;
        OfflinePlayer op = getOwningPlayer(skull);//skullMeta.getOwningPlayer();
        //if(op==null) op = ProfileUtils.getProfilePlayer(skull);//this does happen on textured heads with a profile but without an OwningPlayer // same method as above in 1.8 implementation
        if(op!=null) owner=op.getName();
        if(owner==null) owner=getOwnerDirect(skull);//skullMeta.getOwner();
        return owner;
    }
    @Override public String getOwner(Skull skull){
        String owner=null;
        OfflinePlayer op = getOwningPlayer(skull);//skullMeta.getOwningPlayer();
        //if(op==null) op = ProfileUtils.getProfilePlayer(skull);//this does happen on textured heads with a profile but without an OwningPlayer // same method as above in 1.8 implementation
        if(op!=null) owner=op.getName();
        if(owner==null) owner=getOwnerDirect(skull);//skullMeta.getOwner();
        return owner;
    }
    
    
    
    @Override public boolean isHead(ItemStack s){ return getSkullType(s)!=null; }
    @Override public boolean isHead(BlockState s){ return getSkullType(s)!=null; }
    @Override public boolean isPlayerhead(ItemStack s){ return getSkullType(s)==SkullType.PLAYER; }
    @Override public boolean isPlayerhead(BlockState s){ return getSkullType(s)==SkullType.PLAYER; }
    @Override public boolean isMobhead(ItemStack s){ SkullType t=getSkullType(s); return (t!=null && t!=SkullType.PLAYER);}
    @Override public boolean isMobhead(BlockState s){ SkullType t=getSkullType(s); return (t!=null && t!=SkullType.PLAYER);}
    @Override public String getCompatibleNameFromEntity(Entity e){
        if(Version.checkUnder(1, 11)){//skeleton and zombie variants were separated after 1.11, and have their own EntityType
            if(e instanceof Horse){
                Horse.Variant var = ((Horse) e).getVariant();
                switch (var) {
                    case DONKEY:
                        return "DONKEY";
                    case MULE:
                        return "MULE";
                    case SKELETON_HORSE:
                        return "SKELETON_HORSE";
                    case UNDEAD_HORSE:
                        return "ZOMBIE_HORSE";
                    default:
                        return "HORSE";
                }
            }
            if(e instanceof Zombie){
                if(((Zombie) e).isVillager()) return "ZOMBIE_VILLAGER";
                if(e.getName().equalsIgnoreCase("HUSK")) return "HUSK";//there is no ZombieType/Zombie.Variant enum in bukkit 1.10 so we have no way of knowing what mobs are husks before 1.11
            }
            if(e instanceof Skeleton){//in 1.10, skeleton variants can be retrieved by SkeletonType
                SkeletonType skeleType = ((Skeleton) e).getSkeletonType();
                if(skeleType == SkeletonType.WITHER) return "WITHER_SKELETON";
                else if(skeleType.name().equalsIgnoreCase("STRAY")) return "STRAY";//added in 1.10, since this in 1.8 code we can't directly reference it.
            }
        }
        return e.getType().name().toUpperCase();
    }
    
    private CompoundTag createNBT(UUID uuid, String textures){
        CompoundTag texture_nbt = new CompoundTag();
        texture_nbt.putString("Value",textures);
        CompoundTag textures_nbt = new CompoundTag();
        textures_nbt.putCompoundList("textures", Arrays.asList(texture_nbt) );
        
        CompoundTag owner_nbt = new CompoundTag();
        if(uuid!=null) owner_nbt.putString("Id", uuid.toString());
        owner_nbt.putCompound("Properties",textures_nbt);
        
        //CompoundTag container = new CompoundTag();
        //container.putCompound("Owner", owner_nbt);
        return owner_nbt;
    }
    
    private PlayerProfile createProfile(UUID uuid, String textures){
        System.out.println("UUID: "+uuid.toString());
        CompoundTag ownerNBT = createNBT(uuid,textures);
        System.out.println("Constructed NBT: "+ownerNBT.toString());
        
        GlowPlayerProfile profile = null;
        try{
            CompletableFuture<GlowPlayerProfile> futureProfile = GlowPlayerProfile.fromNbt(ownerNBT);
            profile=futureProfile.get();
            //profile.setId(uuid);//I get Not Yet Implemented when using this... sigh.
            profile.complete();
        }catch(Exception e){
            e.printStackTrace();
            throw new IllegalStateException("Profile construction failed",e);
        }
        //GlowPlayerProfile profile=new GlowPlayerProfile(null,uuid,false);
        //PlayerProfile profile = Bukkit.createProfile(uuid);
        //Set<ProfileProperty> properties = profile.getProperties();
        //properties.add(new ProfileProperty("textures", textures));
        System.out.println("Interpreted NBT: "+profile.toNbt().toString());
        //GlowPlayerProfile.fromNbt(CompoundTag)
        return profile;
    }
    @Override public boolean setProfile(ItemMeta headMeta, UUID uuid, String texture){
        //return ProfileUtils.setProfile(headMeta, uuid, texture);
        //TODO: find glowstone implementations for texturing!
        OfflinePlayer op=Bukkit.getOfflinePlayer(uuid);
        SkullMeta skullMeta = (SkullMeta) headMeta;
        skullMeta.setPlayerProfile(createProfile(uuid,texture));
        return true;
    }
    @Override public boolean setProfile(Skull headBlockState, UUID uuid, String texture){
        //return ProfileUtils.setProfile(headBlockState, uuid, texture);
        //TODO: find glowstone implementations for texturing!
        //OfflinePlayer op=Bukkit.getOfflinePlayer(uuid);
        //setPlayerProfile(headBlockState, createProfile(uuid,texture));
        //headBlockState.setPlayerProfile(createProfile(uuid,texture));// doesn't exist in this version of paper-api
        return false;
    }
    
    
    
    private SkullType adaptSkullType(org.bukkit.SkullType bukkitType){
        try{
            return SkullType.values()[bukkitType.ordinal()];
        }catch(Exception e){
            return null;
        }
    }
    private org.bukkit.SkullType adaptSkullType(SkullType compatType){
        if(compatType==SkullType.DRAGON && Version.checkUnder(1, 9)) return org.bukkit.SkullType.PLAYER;
        try{
            return org.bukkit.SkullType.values()[compatType.ordinal()];
        }catch(Exception e){
            return null;
        }
    }
}
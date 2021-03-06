package com.jacksonplayz.thinkingwithaperture.items;

import com.jacksonplayz.thinkingwithaperture.ThinkingWithAperture;
import net.minecraft.util.SoundEvent;

public class ItemModRecord extends net.minecraft.item.ItemRecord {

    public ItemModRecord(String recordName, SoundEvent sound) {
        super(recordName, sound);
        this.setRegistryName("record_" + recordName);
        this.setUnlocalizedName("record");
        this.setCreativeTab(ThinkingWithAperture.TAB);
    }
}
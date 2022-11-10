package com.sihenzhang.crockpot.data;

import com.sihenzhang.crockpot.CrockPot;
import com.sihenzhang.crockpot.CrockPotRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class CrockPotEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public CrockPotEntityTypeTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, CrockPot.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(EntityTypeTags.IMPACT_PROJECTILES).add(CrockPotRegistry.PARROT_EGG_ENTITY.get());
    }

    @Override
    public String getName() {
        return "CrockPot Entity Type Tags";
    }
}
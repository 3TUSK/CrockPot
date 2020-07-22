package com.sihenzhang.crockpot.recipe;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.sihenzhang.crockpot.recipe.requirements.Requirement;
import com.sihenzhang.crockpot.recipe.requirements.RequirementType;
import com.sihenzhang.crockpot.recipe.requirements.RequirementUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@ParametersAreNonnullByDefault
public class Recipe implements INBTSerializable<CompoundNBT>, Predicate<RecipeInput> {
    List<Pair<Requirement, RequirementType>> requirements = new LinkedList<>();
    int priority, weight, cookTime, potLevel;
    ItemStack result;

    public Recipe(int priority, int weight, int cookTime, int potLevel, ItemStack result) {
        this.priority = priority;
        this.weight = weight;
        this.result = result;
        this.cookTime = cookTime;
        this.potLevel = potLevel;
    }

    public Recipe(CompoundNBT nbt) {
        deserializeNBT(nbt);
    }

    public int getPriority() {
        return priority;
    }

    public int getWeight() {
        return weight;
    }

    public int getCookTime() {
        return cookTime;
    }

    public ItemStack getResult() {
        return result;
    }

    public int getPotLevel() {
        return potLevel;
    }

    public void addRequirement(Requirement requirement, RequirementType type) {
        this.requirements.add(new Pair<>(requirement, type));
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        ListNBT req = new ListNBT();
        for (Pair<Requirement, RequirementType> p : requirements) {
            CompoundNBT e = new CompoundNBT();
            e.putString("type", p.getSecond().name());
            e.put("requirement", p.getFirst().serializeNBT());
            req.add(e);
        }
        nbt.put("requirements", req);
        nbt.putInt("priority", priority);
        nbt.putInt("weight", weight);
        nbt.put("result", result.serializeNBT());
        nbt.putInt("cookTime", cookTime);
        nbt.putInt("potLevel", potLevel);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.priority = nbt.getInt("priority");
        this.weight = nbt.getInt("weight");
        this.cookTime = nbt.getInt("cookTime");
        this.potLevel = nbt.getInt("potLevel");
        this.result = ItemStack.read((CompoundNBT) Objects.requireNonNull(nbt.get("result")));
        ListNBT requirements = (ListNBT) nbt.get("requirements");
        assert requirements != null;
        for (INBT r : requirements) {
            CompoundNBT cast = (CompoundNBT) r;
            this.requirements.add(
                    new Pair<>(
                            RequirementUtil.deserialize((CompoundNBT) Objects.requireNonNull(cast.get("requirement"))),
                            RequirementType.valueOf(cast.getString("type"))
                    )
            );
        }
    }

    @Override
    public boolean test(RecipeInput recipeInput) {
        if (recipeInput.potLevel < this.potLevel) return false;
        for (Pair<Requirement, RequirementType> req : this.requirements) {
            if (req.getFirst().test(recipeInput)) {
                if (req.getSecond() == RequirementType.SUFFICIENT) return true;
            } else {
                if (req.getSecond() == RequirementType.REQUIRED) return false;
            }
        }
        return true;
    }

    public static class Serializer implements JsonDeserializer<Recipe>, JsonSerializer<Recipe> {
        @Override
        public Recipe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                return new Recipe(JsonToNBT.getTagFromJson(json.toString()));
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public JsonElement serialize(Recipe src, Type typeOfSrc, JsonSerializationContext context) {
            JsonReader reader = new JsonReader(new StringReader(src.serializeNBT().toString()));
            reader.setLenient(true);
            return new JsonParser().parse(reader);
        }
    }
}
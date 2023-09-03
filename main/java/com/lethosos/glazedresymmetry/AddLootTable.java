package com.lethosos.glazedresymmetry;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

	public class AddLootTable extends LootModifier {

		protected AddLootTable(LootItemCondition[] conditionsIn, ResourceLocation lootTable) {
			super(conditionsIn);
			this.lootTable = lootTable;
		}
		
		public static final Supplier<Codec<AddLootTable>> CODEC 
	    	= Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst)
					.and(ResourceLocation.CODEC.fieldOf("lootTable").forGetter((m) -> m.lootTable))
					.apply(inst, AddLootTable::new)));

	    private final ResourceLocation lootTable;

		@Nonnull
	    @Override
	    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
	    	LootTable extraTable = context.getLootTable(this.lootTable);
			extraTable.getRandomItemsRaw(context, generatedLoot::add);
	        return generatedLoot;
	    }

	    @Override
	    public Codec<? extends IGlobalLootModifier> codec() {
	        return CODEC.get();
	    }
	}

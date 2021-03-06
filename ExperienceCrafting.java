package io.github.anon10w1z.expcrafting;

import java.util.List;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;

/**
 * Experience Crafting main mod file
 */
@Mod(modid = "expcrafting", name = "Experience Crafting", version = "1.0", dependencies = "after:*")
public class ExperienceCrafting {
	/**
	 * Initializes Experience Crafting
	 * @param event The FMLInitializationEvent
	 */
	@EventHandler
	public void init(FMLInitializationEvent event) {
		RecipeSorter.register("wrapped_recipe", WrappedRecipe.class, Category.SHAPED, "");
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < recipes.size(); ++i)
			recipes.set(i, new WrappedRecipe(recipes.get(i)));
		MinecraftForge.EVENT_BUS.register(new ExperienceCraftingEventHandler());
		FMLCommonHandler.instance().bus().register(new ExperienceCraftingEventHandler());
	}
}

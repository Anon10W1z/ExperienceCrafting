package io.github.anon10w1z.expcrafting;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class WrappedRecipe implements IRecipe {
	public static Map<Item, Integer> itemXpRecipeMap = new HashMap<Item, Integer>() {
		{
			for (Object itemObject : Item.itemRegistry) {
				Item item = (Item) itemObject;
				this.put(item, 0);
				if (item instanceof ItemTool)
					switch (((ItemTool) item).getToolMaterial()) {
					case WOOD:
						this.put(item, 0);
						break;
					case STONE:
						this.put(item, 1);
						break;
					case IRON:
						this.put(item, 2);
						break;
					case GOLD:
						this.put(item, 3);
						break;
					case EMERALD:
						this.put(item, 5);
						break;
					default:
					}
				if (item instanceof ItemHoe)
					this.put(item, 5);
				if (item instanceof ItemArmor)
					switch (((ItemArmor) item).getArmorMaterial()) {
					case LEATHER:
						this.put(item, 0);
						break;
					case IRON:
						this.put(item, 2);
						break;
					case GOLD:
						this.put(item, 3);
					case DIAMOND:
						this.put(item, 5);
					default:
					}
			}
			this.put(Items.blaze_powder, 10);
			this.put(Items.brewing_stand, 15);
			this.put(Items.ender_eye, 20);
			this.put(Items.golden_apple, 10);
			this.put(Items.magma_cream, 10);
			this.put(Items.quartz, 12);
			this.put(Items.repeater, 10);

			for (Object blockObject : Block.blockRegistry)
				this.put(Item.getItemFromBlock((Block) blockObject), 0);
			this.put(Item.getItemFromBlock(Blocks.beacon), 15);
			this.put(Item.getItemFromBlock(Blocks.enchanting_table), 12);
			this.put(Item.getItemFromBlock(Blocks.ender_chest), 15);
		}
	};

	private IRecipe wrappingRecipe;

	public WrappedRecipe(IRecipe wrappingRecipe) {
		this.wrappingRecipe = wrappingRecipe;
	}

	@Override
	public boolean matches(InventoryCrafting inventoryCrafting, World world) {
		EntityPlayer player = findPlayer(inventoryCrafting);
		int playerMaxExpLevel = ExperienceCraftingPlayerProperties.getMaxExperienceLevel(player);
		return this.wrappingRecipe.matches(inventoryCrafting, world) && (player == null || playerMaxExpLevel >= itemXpRecipeMap.get(this.getCraftingResult(inventoryCrafting).getItem()) || player.capabilities.isCreativeMode);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
		return this.wrappingRecipe.getCraftingResult(inventoryCrafting);
	}

	@Override
	public int getRecipeSize() {
		return this.wrappingRecipe.getRecipeSize();
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.wrappingRecipe.getRecipeOutput();
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inventoryCrafting) {
		return this.wrappingRecipe.getRemainingItems(inventoryCrafting);
	}

	private static EntityPlayer findPlayer(InventoryCrafting inv) {
		try {
			Field eventHandlerField = ReflectionHelper.findField(InventoryCrafting.class, "eventHandler", "field_70465_c");
			Field containerPlayerPlayerField = ReflectionHelper.findField(ContainerPlayer.class, "thePlayer", "field_82862_h");
			Field slotCraftingPlayerField = ReflectionHelper.findField(SlotCrafting.class, "thePlayer", "field_75238_b");
			Container container = (Container) eventHandlerField.get(inv);
			if (container instanceof ContainerPlayer)
				return (EntityPlayer) containerPlayerPlayerField.get(container);
			else if (container instanceof ContainerWorkbench)
				return (EntityPlayer) slotCraftingPlayerField.get(container.getSlot(0));
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

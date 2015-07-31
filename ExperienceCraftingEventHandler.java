package io.github.anon10w1z.expcrafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * The Experience Crafting event handler
 */
public class ExperienceCraftingEventHandler {
	/**
	 * Ensures the max experience level for every player is kept in sync
	 * @param event The LivingUpdateEvent
	 */
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			ExperienceCraftingPlayerProperties.updateMaxExperienceLevel(player);
		}
	}
	
	/**
	 * Copies the max experience level for a player upon death/dimension change
	 * @param event The player clone event
	 */
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event) {
		ExperienceCraftingPlayerProperties oldPlayerProperties = (ExperienceCraftingPlayerProperties) event.original.getExtendedProperties("EXPERIENCE_CRAFTING_PROPERTIES");
		if (event.entityPlayer.getExtendedProperties(ExperienceCraftingPlayerProperties.PROPERTIES_NAME) == null)
			event.entityPlayer.registerExtendedProperties(ExperienceCraftingPlayerProperties.PROPERTIES_NAME, new ExperienceCraftingPlayerProperties());
		ExperienceCraftingPlayerProperties newPlayerProperties = (ExperienceCraftingPlayerProperties) event.entityPlayer.getExtendedProperties("EXPERIENCE_CRAFTING_PROPERTIES");
		newPlayerProperties.maxExperienceLevel = oldPlayerProperties.maxExperienceLevel;
	}
}

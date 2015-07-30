package io.github.anon10w1z.expcrafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class ExperienceCraftingEventHandler {
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			if (player.getExtendedProperties("EXPERIENCE_CRAFTING_PROPERTIES") == null)
				player.registerExtendedProperties("EXPERIENCE_CRAFTING_PROPERTIES", new ExperienceCraftingPlayerProperties());
			ExperienceCraftingPlayerProperties.updateMaxExperienceLevel(player);
		}
	}
	
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event) {
		ExperienceCraftingPlayerProperties oldPlayerProperties = (ExperienceCraftingPlayerProperties) event.original.getExtendedProperties("EXPERIENCE_CRAFTING_PROPERTIES");
		if (event.entityPlayer.getExtendedProperties("EXPERIENCE_CRAFTING_PROPERTIES") == null)
			event.entityPlayer.registerExtendedProperties("EXPERIENCE_CRAFTING_PROPERTIES", new ExperienceCraftingPlayerProperties());
		ExperienceCraftingPlayerProperties newPlayerProperties = (ExperienceCraftingPlayerProperties) event.entityPlayer.getExtendedProperties("EXPERIENCE_CRAFTING_PROPERTIES");
		newPlayerProperties.maxExperienceLevel = oldPlayerProperties.maxExperienceLevel;
	}
}

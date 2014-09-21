package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Premium Deck Series: Fire and Lightning")
public final class PremiumDeckSeriesFireAndLightning extends SimpleExpansion
{
	public PremiumDeckSeriesFireAndLightning()
	{
		super();

		this.addCards(Rarity.LAND, "Mountain");
		this.addCards(Rarity.COMMON, "Chain Lightning", "Cinder Pyromancer", "Fireblast", "Keldon Marauders", "Lightning Bolt", "Mogg Flunkies", "Teetering Peaks", "Thunderbolt", "Vulshok Sorcerer");
		this.addCards(Rarity.UNCOMMON, "Barbarian Ring", "Boggart Ram-Gang", "Browbeat", "Fire Servant", "Fireball", "Flames of the Blood Hand", "Ghitu Encampment", "Hellspark Elemental", "Jackal Pup", "Keldon Champion", "Mogg Fanatic", "Pillage", "Price of Progress", "Spark Elemental", "Sudden Impact");
		this.addCards(Rarity.RARE, "Ball Lightning", "Figure of Destiny", "Grim Lavamancer", "Hammer of Bogardan", "Jaya Ballard, Task Mage", "Reverberate");
	}
}

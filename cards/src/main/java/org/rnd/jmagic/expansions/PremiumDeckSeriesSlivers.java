package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Premium Deck Series: Slivers")
public final class PremiumDeckSeriesSlivers extends SimpleExpansion
{
	public PremiumDeckSeriesSlivers()
	{
		super();

		this.addCards(Rarity.LAND, "Forest", "Island", "Mountain", "Plains", "Swamp");
		this.addCards(Rarity.COMMON, "Amoeboid Changeling", "Aphetto Dredging", "Clot Sliver", "Distant Melody", "Frenzy Sliver", "Gemhide Sliver", "Heart Sliver", "Homing Sliver", "Metallic Sliver", "Muscle Sliver", "Quick Sliver", "Rupture Spire", "Terramorphic Expanse", "Virulent Sliver", "Winged Sliver");
		this.addCards(Rarity.UNCOMMON, "Acidic Sliver", "Ancient Ziggurat", "Armor Sliver", "Barbed Sliver", "Crystalline Sliver", "Fury Sliver", "Heartstone", "Hibernation Sliver", "Might Sliver", "Necrotic Sliver", "Spectral Sliver", "Spined Sliver", "Victual Sliver", "Vivid Creek", "Vivid Grove");
		this.addCards(Rarity.RARE, "Brood Sliver", "Coat of Arms", "Fungus Sliver", "Rootbound Crag", "Wild Pair");
		this.addCards(Rarity.MYTHIC, "Sliver Overlord");
	}
}

package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Deckmasters")
public final class Deckmasters extends SimpleExpansion
{
	public Deckmasters()
	{
		super();

		this.addCards(Rarity.LAND, "Forest", "Mountain", "Swamp");
		this.addCards(Rarity.SPECIAL, "Abyssal Specter", "Balduvian Bears", "Balduvian Horde", "Barbed Sextant", "Bounty of the Hunt", "Contagion", "Dark Banishing", "Dark Ritual", "Death Spark", "Elkin Bottle", "Elvish Bard", "Folk of the Pines", "Foul Familiar", "Fyndhorn Elves", "Giant Growth", "Giant Trap Door Spider", "Goblin Mutant", "Guerrilla Tactics", "Hurricane", "Icy Manipulator", "Incinerate", "Jokulhaups", "Karplusan Forest", "Lava Burst", "Lhurgoyf", "Necropotence", "Orcish Cannoneers", "Phantasmal Fiend", "Phyrexian War Beast", "Pillage", "Pyroclasm", "Shatter", "Soul Burn", "Storm Shaman", "Sulfurous Springs", "Underground River", "Walking Wall", "Woolly Spider", "Yavimaya Ancients", "Yavimaya Ants");
	}
}

package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Starter 2000")
public final class Starter2000 extends SimpleExpansion
{
	public Starter2000()
	{
		super();

		this.addCards(Rarity.LAND, "Forest", "Forest", "Island", "Island", "Mountain", "Mountain", "Plains", "Plains", "Swamp", "Swamp");
		this.addCards(Rarity.COMMON, "Angelic Blessing", "Armored Pegasus", "Bog Imp", "Coercion", "Counterspell", "Disenchant", "Drudge Skeletons", "Durkwood Boars", "Eager Cadet", "Flame Spirit", "Flight", "Giant Growth", "Giant Octopus", "Goblin Hero", "Hand of Death", "Hero's Resolve", "Inspiration", "Knight Errant", "Lava Axe", "Llanowar Elves", "Merfolk of the Pearl Trident", "Mons's Goblin Raiders", "Monstrous Growth", "Ogre Warrior", "Prodigal Sorcerer", "Python", "Royal Falcon", "Samite Healer", "Scathe Zombies", "Sea Eagle", "Shock", "Spined Wurm", "Stone Rain", "Terror", "Time Ebb", "Venerable Monk", "Wild Griffin", "Willow Elf", "Wind Drake");
		this.addCards(Rarity.UNCOMMON, "Breath of Life", "Moon Sprite", "Obsianus Golem", "Orcish Oriflamme", "Rod of Ruin", "Soul Net");
		this.addCards(Rarity.RARE, "Rhox", "Trained Orgg", "Vizzerdrix");
	}
}

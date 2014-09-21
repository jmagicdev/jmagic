package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Introductory Two-Player Set")
public final class IntroductoryTwoPlayerSet extends SimpleExpansion
{
	public IntroductoryTwoPlayerSet()
	{
		super();

		this.addCards(Rarity.LAND, "Forest", "Island", "Mountain", "Plains", "Swamp");
		this.addCards(Rarity.SPECIAL, "Alabaster Potion", "Battering Ram", "Bog Imp", "Bog Wraith", "Circle of Protection: Black", "Circle of Protection: Red", "Clockwork Beast", "Cursed Land", "Dark Ritual", "Detonate", "Disintegrate", "Durkwood Boars", "Elven Riders", "Elvish Archers", "Energy Flux", "Feedback", "Fireball", "Glasses of Urza", "Grizzly Bears", "Healing Salve", "Hill Giant", "Ironclaw Orcs", "Jayemdae Tome", "Lost Soul", "Merfolk of the Pearl Trident", "Mesa Pegasus", "Mons's Goblin Raiders", "Murk Dwellers", "Orcish Artillery", "Orcish Oriflamme", "Pearled Unicorn", "Phantom Monster", "Power Sink", "Pyrotechnics", "Raise Dead", "Reverse Damage", "Rod of Ruin", "Scathe Zombies", "Scryb Sprites", "Sorceress Queen", "Terror", "Twiddle", "Unsummon", "Untamed Wilds", "Vampire Bats", "Wall of Bone", "War Mammoth", "Warp Artifact", "Weakness", "Whirling Dervish", "Winter Blast", "Zephyr Falcon");
	}
}

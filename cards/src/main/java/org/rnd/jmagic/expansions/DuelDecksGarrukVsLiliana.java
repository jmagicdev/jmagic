package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Duel Decks: Garruk vs. Liliana")
public final class DuelDecksGarrukVsLiliana extends SimpleExpansion
{
	public DuelDecksGarrukVsLiliana()
	{
		super();

		this.addCards(Rarity.LAND, "Forest", "Swamp");
		this.addCards(Rarity.COMMON, "Basking Rootwalla", "Beast", "Blastoderm", "Deathgreeter", "Drudge Skeletons", "Elephant", "Faerie Macabre", "Giant Growth", "Hideous End", "Ichor Slick", "Invigorate", "Krosan Tusker", "Lignify", "Nature's Lore", "Phyrexian Rager", "Polluted Mire", "Rancor", "Ravenous Rats", "Serrated Arrows", "Sign in Blood", "Slippery Karst", "Snuff Out", "Tendrils of Corruption", "Twisted Abomination", "Urborg Syphon-Mage", "Vampire Bats", "Vicious Hunger", "Vine Trellis", "Wild Mongrel", "Wirewood Savage");
		this.addCards(Rarity.UNCOMMON, "Albino Troll", "Beast Attack", "Corrupt", "Elephant Guide", "Enslave", "Fleshbag Marauder", "Genju of the Cedars", "Genju of the Fens", "Ghost-Lit Stalker", "Harmonize", "Howling Banshee", "Indrik Stomphowler", "Keening Banshee", "Overrun", "Rise from the Grave", "Stampeding Wildebeests", "Treetop Village", "Wall of Bone", "Windstorm");
		this.addCards(Rarity.RARE, "Bad Moon", "Mutilate", "Plated Slagwurm", "Ravenous Baloth", "Rude Awakening", "Skeletal Vampire");
		this.addCards(Rarity.MYTHIC, "Garruk Wildspeaker", "Liliana Vess");
	}
}

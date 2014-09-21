package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Duel Decks: Izzet vs. Golgari")
public final class DuelDecksIzzetVsGolgari extends SimpleExpansion
{
	public DuelDecksIzzetVsGolgari()
	{
		super();

		this.addCards(Rarity.LAND, "Forest", "Island", "Mountain", "Swamp");
		this.addCards(Rarity.COMMON, "Barren Moor", "Brain Weevil", "Brainstorm", "Call to Heel", "Elves of Deep Shadow", "Feast or Famine", "Force Spike", "Forgotten Cave", "Goblin Electromancer", "Golgari Rot Farm", "Golgari Rotwurm", "Golgari Signet", "Greater Mossdog", "Izzet Boilerworks", "Izzet Chronarch", "Izzet Signet", "Kiln Fiend", "Lonely Sandbar", "Magma Spray", "Ogre Savant", "Putrid Leech", "Pyromatics", "Quicksilver Dagger", "Ravenous Rats", "Shambling Shell", "Steamcore Weird", "Stinkweed Imp", "Train of Thought", "Tranquil Thicket", "Wee Dragonauts", "Yoke of the Damned");
		this.addCards(Rarity.UNCOMMON, "Boneyard Wurm", "Dakmor Salvage", "Dissipate", "Dreg Mangler", "Eternal Witness", "Fire // Ice", "Gelectrode", "Ghoul's Feast", "Golgari Germination", "Golgari Thug", "Grim Flowering", "Isochron Scepter", "Izzet Charm", "Izzet Guildmage", "Korozda Guildmage", "Life // Death", "Nightmare Void", "Nivix, Aerie of the Firemind", "Overwhelming Intellect", "Plagued Rusalka", "Putrefy", "Reassembling Skeleton", "Reminisce", "Sadistic Hypnotist", "Shrewd Hatchling", "Stingerfling Spider", "Street Spasm", "Svogthos, the Restless Tomb", "Thunderheads", "Vacuumelt", "Vigor Mortis");
		this.addCards(Rarity.RARE, "Djinn Illuminatus", "Doomgape", "Galvanoth", "Gleancrawler", "Golgari Grave-Troll", "Invoke the Firemind", "Life from the Loam", "Prophetic Bolt", "Sphinx-Bone Wand", "Twilight's Call");
		this.addCards(Rarity.MYTHIC, "Jarad, Golgari Lich Lord", "Niv-Mizzet, the Firemind");
	}
}

package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Beatdown Box Set")
public final class BeatdownBoxSet extends SimpleExpansion
{
	public BeatdownBoxSet()
	{
		super();

		this.addCards(Rarity.LAND, "Forest", "Island", "Mountain", "Swamp");
		this.addCards(Rarity.COMMON, "Bloodrock Cyclops", "Bone Harvest", "Brainstorm", "Cloud Elemental", "Coercion", "Counterspell", "Crash of Rhinos", "Dark Ritual", "Deadly Insect", "Death Stroke", "Diabolic Edict", "Drain Life", "Feral Shadow", "Fireball", "Fog", "Fog Elemental", "Gaseous Form", "Giant Crab", "Giant Growth", "Gravedigger", "Hollow Dogs", "Impulse", "Kird Ape", "Lava Axe", "Lightning Bolt", "Llanowar Elves", "Lowland Giant", "Plated Spider", "Polluted Mire", "Power Sink", "Quirion Elves", "Raging Goblin", "Rampant Growth", "Remote Isle", "Scaled Wurm", "Shambling Strider", "Shock", "Skittering Horror", "Skittering Skirge", "Slippery Karst", "Smoldering Crater", "Snapping Drake", "Sonic Burst", "Talruum Minotaur", "Tar Pit Warrior", "Terror", "Thunderbolt", "Tolarian Winds", "Viashino Warrior", "Vigilant Drake", "Wayward Soul", "Wild Growth", "Woolly Spider", "Yavimaya Wurm");
		this.addCards(Rarity.UNCOMMON, "Air Elemental", "Cloud Djinn", "Crashing Boars", "Diabolic Vision", "Dwarven Ruins", "Ebon Stronghold", "Erhnam Djinn", "Havenwood Battleground", "Hulking Cyclops", "Killer Whale", "Segmented Wurm", "Sengir Vampire", "Svyelunite Temple", "Thundering Giant");
		this.addCards(Rarity.RARE, "Balduvian Horde", "Ball Lightning", "Blizzard Elemental", "Clockwork Avian", "Clockwork Beast", "Fallen Angel", "Force of Nature", "Leviathan", "Mahamoti Djinn", "Shivan Dragon");
	}
}

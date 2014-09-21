package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Duel Decks: Speed vs. Cunning")
public final class DuelDecksSpeedVsCunning extends SimpleExpansion
{
	public DuelDecksSpeedVsCunning()
	{
		super();

		this.addCards(Rarity.LAND, "Island", "Mountain", "Plains", "Swamp");
		this.addCards(Rarity.COMMON, "Act of Treason", "Aquamorph Entity", "Bone Splinters", "Coral Trickster", "Dregscape Zombie", "Echo Tracer", "Evolving Wilds", "Faerie Invaders", "Fathom Seer", "Fiery Fall", "Fleeting Distraction", "Goblin", "Goblin Deathraiders", "Hussar Patrol", "Impulse", "Infantry Veteran", "Kathari Bomber", "Kor Hookmaster", "Krenko's Command", "Leonin Snarecaster", "Lone Missionary", "Mana Leak", "Master Decoy", "Orcish Cannonade", "Reckless Abandon", "Repeal", "Shock", "Sparkmage Apprentice", "Stave Off", "Swift Justice", "Terramorphic Expanse", "Traumatic Visions", "Whiplash Trap");
		this.addCards(Rarity.UNCOMMON, "Arc Trail", "Arrow Volley Trap", "Beetleback Chief", "Dauntless Onslaught", "Faerie Impostor", "Flame-Kin Zealot", "Fleshbag Marauder", "Frenzied Goblin", "Ghitu Encampment", "Goblin Bombardment", "Goblin Warchief", "Hellraiser Goblin", "Inferno Trap", "Jeskai Elder", "Lightning Helix", "Mardu Heart-Piercer", "Mystic Monastery", "Nomad Outpost", "Oni of Wild Places", "Scourge Devil", "Shambling Remains", "Stonecloaker", "Willbender");
		this.addCards(Rarity.RARE, "Banefire", "Fury of the Horde", "Hell's Thunder", "Hold the Line", "Krenko, Mob Boss", "Lightning Angel", "Ogre Battledriver", "Sphinx of Uthuun", "Steam Augury", "Thousand Winds");
		this.addCards(Rarity.MYTHIC, "Arcanis the Omnipotent", "Zurgo Helmsmasher");
	}
}

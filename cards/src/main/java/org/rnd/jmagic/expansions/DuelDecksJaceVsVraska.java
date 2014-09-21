package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Duel Decks: Jace vs. Vraska")
public final class DuelDecksJaceVsVraska extends SimpleExpansion
{
	public DuelDecksJaceVsVraska()
	{
		super();

		this.addCards(Rarity.LAND, "Forest", "Island", "Swamp");
		this.addCards(Rarity.COMMON, "AEther Adept", "Archaeomancer", "Claustrophobia", "Consume Strength", "Crosstown Courier", "Death-Hood Cobra", "Dream Stalker", "Errant Ephemeron", "Festerhide Boar", "Gatecreeper Vine", "Golgari Guildgate", "Griptide", "Grisly Spectacle", "Halimar Depths", "Highway Robber", "Hypnotic Cloud", "Into the Roil", "Krovikan Mist", "Last Kiss", "Leyline Phantom", "Memory Lapse", "Mold Shambler", "Oran-Rief Recluse", "Phantasmal Bear", "Prohibit", "Pulse Tracker", "Putrid Leech", "Ray of Command", "Sadistic Augermage", "Sea Gate Oracle", "Shadow Alley Denizen", "Slate Street Ruffian", "Stab Wound", "Stealer of Secrets", "Stonefare Crocodile", "Thought Scour", "Tragic Slip");
		this.addCards(Rarity.UNCOMMON, "AEther Figment", "Acidic Slime", "Agoraphobia", "Chronomaton", "Control Magic", "Corpse Traders", "Dread Statuary", "Drooling Groodion", "Jace's Ingenuity", "Jace's Phantasm", "Marsh Casualties", "Merfolk Wayfinder", "Nekrataal", "Night's Whisper", "Phantasmal Dragon", "Remand", "Riftwing Cloudskate", "River Boa", "Rogue's Passage", "Summoner's Bane", "Tainted Wood", "Tavern Swindler", "Treasured Find", "Wight of Precinct Six");
		this.addCards(Rarity.RARE, "Aeon Chronicler", "Body Double", "Future Sight", "Jace's Mindseeker", "Ohran Viper", "Reaper of the Wilds", "Spawnwrithe", "Spelltwine", "Underworld Connections", "Vinelasher Kudzu");
		this.addCards(Rarity.MYTHIC, "Jace, Architect of Thought", "Vraska the Unseen");
	}
}

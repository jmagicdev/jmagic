package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Duel Decks: Heroes vs. Monsters")
public final class DuelDecksHeroesVsMonsters extends SimpleExpansion
{
	public DuelDecksHeroesVsMonsters()
	{
		super();

		this.addCards(Rarity.LAND, "Forest", "Mountain", "Plains");
		this.addCards(Rarity.COMMON, "Armory Guard", "Auramancer", "Blood Ogre", "Bonds of Faith", "Boros Guildgate", "Cavalry Pegasus", "Dawnstrike Paladin", "Deadly Recluse", "Freewind Equenaut", "Ghor-Clan Savage", "Gorehorn Minotaurs", "Krosan Tusker", "Moment of Heroism", "Orcish Lumberjack", "Pay No Heed", "Prey Upon", "Satyr Hedonist", "Shower of Sparks", "Smite the Monstrous", "Somberwald Vigilante", "Stand Firm", "Terrifying Presence", "Thraben Valiant", "Valley Rannet", "Volt Charge", "Zhur-Taa Druid");
		this.addCards(Rarity.UNCOMMON, "Battle Mastery", "Beast Within", "Condemn", "Crowned Ceratok", "Daily Regimen", "Destructive Revelry", "Dragon Blood", "Fencing Ace", "Fires of Yavimaya", "Griffin Guide", "Gustcloak Sentinel", "Kavu Predator", "Kazandu Refuge", "Llanowar Reborn", "Magma Jet", "Miraculous Recovery", "New Benalia", "Ordeal of Purphoros", "Pyroclasm", "Pyrokinesis", "Regrowth", "Righteousness", "Skarrg, the Rage Pits", "Skarrgan Skybreaker", "Stun Sniper", "Truefire Paladin", "Undying Rage");
		this.addCards(Rarity.RARE, "Anax and Cymede", "Conquering Manticore", "Crater Hellion", "Deus of Calamity", "Figure of Destiny", "Kamahl, Pit Fighter", "Nobilis of War", "Skarrgan Firebird", "Troll Ascetic", "Winds of Rath");
		this.addCards(Rarity.MYTHIC, "Polukranos, World Eater", "Sun Titan");
	}
}

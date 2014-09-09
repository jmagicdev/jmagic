package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Theros")
public final class Theros extends SimpleExpansion
{
	public Theros()
	{
		super();

		this.addCards(Rarity.LAND, "Forest", "Forest", "Forest", "Forest", "Island", "Island", "Island", "Island", "Mountain", "Mountain", "Mountain", "Mountain", "Plains", "Plains", "Plains", "Plains", "Swamp", "Swamp", "Swamp", "Swamp");
		this.addCards(Rarity.COMMON, "Agent of Horizons", "Akroan Crusader", "Annul", "Aqueous Form", "Asphodel Wanderer", "Baleful Eidolon", "Battlewise Valor", "Benthic Giant", "Blood-Toll Harpy", "Boon of Erebos", "Borderland Minotaur", "Boulderfall", "Breaching Hippocamp", "Bronze Sable", "Cavalry Pegasus", "Cavern Lampad", "Chosen by Heliod", "Coastline Chimera", "Commune with the Gods", "Crackling Triton", "Deathbellow Raider", "Defend the Hearth", "Demolish", "Disciple of Phenax", "Divine Verdict", "Dragon Mantle", "Ephara's Warden", "Fade into Antiquity", "Fate Foretold", "Felhide Minotaur", "Feral Invocation", "Fleetfeather Sandals", "Fleshmad Steed", "Gods Willing", "Gray Merchant of Asphodel", "Griptide", "Guardians of Meletis", "Hopeful Eidolon", "Ill-Tempered Cyclops", "Lagonna-Band Elder", "Lash of the Whip", "Last Breath", "Leafcrown Dryad", "Leonin Snarecaster", "Lightning Strike", "Loathsome Catoblepas", "Lost in a Labyrinth", "March of the Returned", "Messenger's Speed", "Minotaur Skullcleaver", "Mnemonic Wall", "Nessian Asp", "Nessian Courser", "Nimbus Naiad", "Nylea's Disciple", "Nylea's Presence", "Observant Alseid", "Omenspeaker", "Opaline Unicorn", "Pharika's Cure", "Pheres-Band Centaurs", "Portent of Betrayal", "Prescient Chimera", "Priest of Iroas", "Rage of Purphoros", "Ray of Dissolution", "Read the Bones", "Returned Centaur", "Returned Phalanx", "Satyr Hedonist", "Satyr Rambler", "Savage Surge", "Scholar of Athreos", "Scourgemark", "Sedge Scorpion", "Setessan Battle Priest", "Setessan Griffin", "Shredding Winds", "Silent Artisan", "Sip of Hemlock", "Spark Jolt", "Spearpoint Oread", "Staunch-Hearted Warrior", "Stymied Hopes", "Thassa's Bounty", "Time to Feed", "Titan's Strength", "Traveler's Amulet", "Traveling Philosopher", "Triton Shorethief", "Two-Headed Cerberus", "Unknown Shores", "Vaporkin", "Viper's Kiss", "Voyage's End", "Voyaging Satyr", "Vulpine Goliath", "Wavecrash Triton", "Wild Celebrants", "Wingsteed Rider", "Yoked Ox");
		this.addCards(Rarity.UNCOMMON, "Akroan Hoplite", "Anvilwrought Raptor", "Arena Athlete", "Artisan's Sorrow", "Battlewise Hoplite", "Burnished Hart", "Centaur Battlemaster", "Chronicler of Heroes", "Coordinated Assault", "Cutthroat Maneuver", "Dark Betrayal", "Dauntless Onslaught", "Decorated Griffin", "Destructive Revelry", "Dissolve", "Erebos's Emissary", "Evangel of Heliod", "Fanatic of Mogis", "Favored Hoplite", "Flamecast Wheel", "Flamespeaker Adept", "Gainsay", "Glare of Heresy", "Heliod's Emissary", "Horizon Chimera", "Horizon Scholar", "Hunt the Hunter", "Insatiable Harpy", "Karametra's Acolyte", "Keepsake Gorgon", "Kragma Warcaller", "Magma Jet", "Mogis's Marauder", "Nemesis of Mortals", "Nylea's Emissary", "Ordeal of Erebos", "Ordeal of Heliod", "Ordeal of Nylea", "Ordeal of Purphoros", "Ordeal of Thassa", "Peak Eruption", "Phalanx Leader", "Pharika's Mender", "Prowler's Helm", "Purphoros's Emissary", "Rescue from the Underworld", "Satyr Piper", "Sea God's Revenge", "Sealock Monster", "Sentry of the Underworld", "Shipwreck Singer", "Spellheart Chimera", "Stoneshock Giant", "Thassa's Emissary", "Tormented Hero", "Triton Fortune Hunter", "Triton Tactics", "Vanquish the Foul", "Warriors' Lesson", "Witches' Eye");
		this.addCards(Rarity.RARE, "Abhorrent Overlord", "Agent of the Fates", "Akroan Horse", "Anax and Cymede", "Anger of the Gods", "Anthousa, Setessan Hero", "Arbor Colossus", "Artisan of Forms", "Bident of Thassa", "Boon Satyr", "Bow of Nylea", "Celestial Archon", "Chained to the Rocks", "Colossus of Akros", "Curse of the Swine", "Daxos of Meletis", "Ember Swallower", "Fabled Hero", "Firedrinker Satyr", "Fleecemane Lion", "Gift of Immortality", "Hammer of Purphoros", "Hero's Downfall", "Hundred-Handed One", "Labyrinth Champion", "Meletis Charlatan", "Mistcutter Hydra", "Nighthowler", "Nykthos, Shrine to Nyx", "Polis Crusher", "Prognostic Sphinx", "Prophet of Kruphix", "Psychic Intrusion", "Pyxis of Pandemonium", "Rageblood Shaman", "Reaper of the Wilds", "Reverent Hunter", "Shipbreaker Kraken", "Soldier of the Pantheon", "Spear of Heliod", "Steam Augury", "Swan Song", "Sylvan Caryatid", "Temple of Abandon", "Temple of Deceit", "Temple of Mystery", "Temple of Silence", "Temple of Triumph", "Thoughtseize", "Titan of Eternal Fire", "Triad of Fates", "Tymaret, the Murder King", "Whip of Erebos");
		this.addCards(Rarity.MYTHIC, "Ashen Rider", "Ashiok, Nightmare Weaver", "Elspeth, Sun's Champion", "Erebos, God of the Dead", "Heliod, God of the Sun", "Hythonia the Cruel", "Master of Waves", "Medomai the Ageless", "Nylea, God of the Hunt", "Polukranos, World Eater", "Purphoros, God of the Forge", "Stormbreath Dragon", "Thassa, God of the Sea", "Underworld Cerberus", "Xenagos, the Reveler");
	}
}

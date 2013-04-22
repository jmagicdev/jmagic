package org.rnd.jmagic.engine;

/**
 * Represents an expansion symbol. This file is only to contain sets that affect
 * legality.
 */
public enum Expansion
{
	GATECRASH("Gatecrash", "GTC"), // February '13
	RETURN_TO_RAVNICA("Return to Ravnica", "RTR"), // October '12
	MAGIC_2013("Magic 2013", "M13"), // July '12
	PLANECHASE_2012("Planechase 2012 Edition", "P12"), // June '12
	AVACYN_RESTORED("Avacyn Restored", "AVR"), // May '12
	DARK_ASCENSION("Dark Ascension", "DKA"), // January '12
	INNISTRAD("Innistrad", "ISD"), // September '11
	FTV_LEGENDS("From the Vault: Legends", "FVL"), // August '11
	MAGIC_2012("Magic 2012", "M12"), // July '11
	COMMANDER("Magic: the Gathering-Commander", "COM"), // June '11
	NEW_PHYREXIA("New Phyrexia", "NPH"), // May '11
	MIRRODIN_BESIEGED("Mirrodin Besieged", "MBS"), // February '11
	SCARS_OF_MIRRODIN("Scars of Mirrodin", "SOM"), // September '10
	RELICS("From the Vault: Relics", "FVR"), // August '10
	MAGIC_2011("Magic 2011", "M11"), // July '10
	RISE_OF_THE_ELDRAZI("Rise of the Eldrazi", "ROE"), // April '10
	WORLDWAKE("Worldwake", "WWK"), // February '10
	ZENDIKAR("Zendikar", "ZEN"), // September '09
	MAGIC_2010("Magic 2010", "M10"), // July '09
	ALARA_REBORN("Alara Reborn", "ARB"), // April '09
	CONFLUX("Conflux", "CON"), // February '09
	SHARDS_OF_ALARA("Shards of Alara", "ALA"), // October '08
	DRAGONS("From the Vault: Dragons", "FVD"), // August '08
	EVENTIDE("Eventide", "EVE"), // July '08
	SHADOWMOOR("Shadowmoor", "SHM"), // May '08
	MORNINGTIDE("Morningtide", "MOR"), // February '08
	LORWYN("Lorwyn", "LRW"), // October '07
	TENTH_EDITION("Tenth Edition", "10E"), // July '07
	FUTURE_SIGHT("Future Sight", "FUT"), // May '07
	PLANAR_CHAOS("Planar Chaos", "PLC"), // February '07
	TIME_SPIRAL("Time Spiral", "TSP"), // October '06
	COLDSNAP("Coldsnap", "CSP"), // July '06
	DISSENSION("Dissension", "DIS"), // May '06
	GUILDPACT("Guildpact", "GPT"), // February '06
	RAVNICA("Ravnica: City of Guilds", "RAV"), // October '05
	NINTH_EDITION("Ninth Edition", "9ED"), // July '05
	SAVIORS_OF_KAMIGAWA("Saviors of Kamigawa", "SOK"), // June '05
	BETRAYERS_OF_KAMIGAWA("Betrayers of Kamigawa", "BOK"), // February '05
	CHAMPIONS_OF_KAMIGAWA("Champions of Kamigawa", "CHK"), // October '04
	FIFTH_DAWN("Fifth Dawn", "5DN"), // June '04
	DARKSTEEL("Darksteel", "DST"), // February '04
	MIRRODIN("Mirrodin", "MRD"), // October '03
	EIGHTH_EDITION("Eighth Edition", "8ED"), // July '03
	SCOURGE("Scourge", "SCG"), // May '03
	LEGIONS("Legions", "LGN"), // February '03
	ONSLAUGHT("Onslaught", "ONS"), // October '02
	JUDGMENT("Judgment", "JUD"), // May '02
	TORMENT("Torment", "TOR"), // February '02
	ODYSSEY("Odyssey", "OD"), // October '01
	APOCALYPSE("Apocalypse", "AP"), // June '01
	SEVENTH_EDITION("Seventh Edition", "7E"), // April '01
	PLANESHIFT("Planeshift", "PS"), // February '01
	INVASION("Invasion", "IN"), // October '00
	STARTER_2000("Starter 2000", "S00"), // July '00
	PROPHECY("Prophecy", "PR"), // June '00
	NEMESIS("Nemesis", "NE"), // February '00
	MERCADIAN_MASQUES("Mercadian Masques", "MM"), // October '99
	STARTER("Starter 1999", "S99"), // July '99
	URZAS_DESTINY("Urza's Destiny", "UD"), // June '99
	PORTAL_THREE_KINGDOMS("Portal Three Kingdoms", "P3K"), // May '99
	SIXTH_EDITION("Classic Sixth Edition", "6E"), // April '99
	URZAS_LEGACY("Urza's Legacy", "UL"), // February '99
	URZAS_SAGA("Urza's Saga", "US"), // October '98
	EXODUS("Exodus", "EX"), // June '98
	PORTAL_SECOND_AGE("Portal Second Age", "P2"), // June '98
	STRONGHOLD("Stronghold", "ST"), // March '98
	TEMPEST("Tempest", "TE"), // October '97
	// TODO : Should Weatherlight and Portal be switched?
	WEATHERLIGHT("Weatherlight", "WL"), // June '97
	PORTAL("Portal", "P1"), // June '97
	FIFTH_EDITION("Fifth Edition", "5E"), // March '97
	VISIONS("Visions", "VI"), // February '97
	MIRAGE("Mirage", "MI"), // October '96
	ALLIANCES("Alliances", "AI"), // June '96
	HOMELANDS("Homelands", "HL"), // October '95
	ICE_AGE("Ice Age", "IA"), // June '95
	FOURTH_EDITION("Fourth Edition", "4E"), // April '95
	FALLEN_EMPIRES("Fallen Empires", "FE"), // November '94
	THE_DARK("The Dark", "DK"), // August '94
	LEGENDS("Legends", "LE"), // June '94
	REVISED("Revised Edition", "RV"), // April '94
	ANTIQUITIES("Antiquities", "AQ"), // March '94
	ARABIAN_NIGHTS("Arabian Nights", "AN"), // December '93
	UNLIMITED("Unlimited Edition", "U"), // December '93
	BETA("Limited Edition Beta", "B"), // October '93
	ALPHA("Limited Edition Alpha", "A"), // August '93

	PROMO("Promo card", "PRM"), //
	PLANECHASE("Planechase", "PLC"), //
	TEST("jMagic Test Cards", "JMK");

	private final String fullName;
	public final String code;

	Expansion(String fullName, String code)
	{
		this.fullName = fullName;
		this.code = code;
	}

	@Override
	public String toString()
	{
		return this.fullName;
	}
}

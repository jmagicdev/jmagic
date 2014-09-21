package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Duel Decks: Elspeth vs. Tezzeret")
public final class DuelDecksElspethVsTezzeret extends SimpleExpansion
{
	public DuelDecksElspethVsTezzeret()
	{
		super();

		this.addCards(Rarity.LAND, "Island", "Plains");
		this.addCards(Rarity.COMMON, "AEther Spellbomb", "Arcbound Worker", "Blinding Beam", "Burrenton Bombardier", "Clockwork Condor", "Conclave Equenaut", "Darksteel Citadel", "Echoing Truth", "Faerie Mechanist", "Frogmite", "Glory Seeker", "Goldmeadow Harrier", "Infantry Veteran", "Journey to Nowhere", "Kabira Crossroads", "Kemba's Skyguard", "Kor Hookmaster", "Kor Skyfisher", "Mighty Leap", "Moonglove Extract", "Mosquito Guard", "Raise the Alarm", "Razor Barrier", "Seat of the Synod", "Silver Myr", "Steel Wall", "Sunlance", "Temple Acolyte", "Thoughtcast", "Trinket Mage");
		this.addCards(Rarity.UNCOMMON, "Abolish", "Argivian Restoration", "Assembly-Worker", "Celestial Crusader", "Clockwork Hydra", "Conclave Phalanx", "Contagion Clasp", "Daru Encampment", "Elite Vanguard", "Elixir of Immortality", "Energy Chamber", "Esperzoa", "Everflowing Chalice", "Foil", "Juggernaut", "Kor Aeronaut", "Mishra's Factory", "Qumulox", "Runed Servitor", "Saltblast", "Seasoned Marshal", "Serrated Biskelion", "Stalking Stones", "Stormfront Riders", "Swell of Courage", "Swords to Plowshares", "Synod Centurion", "Thirst for Knowledge", "Trip Noose");
		this.addCards(Rarity.RARE, "Angel of Salvation", "Catapult Master", "Crusade", "Loyal Sentry", "Master of Etherium", "Pentavus", "Razormane Masticore", "Rustic Clachan", "Steel Overseer", "Triskelion");
		this.addCards(Rarity.MYTHIC, "Elspeth, Knight-Errant", "Tezzeret the Seeker");
	}
}

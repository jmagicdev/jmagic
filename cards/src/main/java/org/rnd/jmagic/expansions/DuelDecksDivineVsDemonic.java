package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Duel Decks: Divine vs. Demonic")
public final class DuelDecksDivineVsDemonic extends SimpleExpansion
{
	public DuelDecksDivineVsDemonic()
	{
		super();

		this.addCards(Rarity.LAND, "Plains", "Swamp");
		this.addCards(Rarity.COMMON, "Abyssal Gatekeeper", "Angelic Page", "Angelsong", "Barren Moor", "Cackling Imp", "Charging Paladin", "Dark Banishing", "Dark Ritual", "Demon", "Demon's Jester", "Duress", "Dusk Imp", "Faith's Fetters", "Foul Imp", "Healing Salve", "Overeager Apprentice", "Pacifism", "Secluded Steppe", "Spirit", "Stinkweed Imp", "Thrull", "Unholy Strength", "Venerable Monk");
		this.addCards(Rarity.UNCOMMON, "Abyssal Specter", "Angel of Mercy", "Angel's Feather", "Angelic Benediction", "Angelic Protector", "Barter in Blood", "Breeding Pit", "Consume Spirit", "Corrupt", "Cruel Edict", "Daggerclaw Imp", "Demon's Horn", "Demonic Tutor", "Icatian Priest", "Marble Diamond", "Oni Possession", "Otherworldly Journey", "Righteous Cause", "Serra Advocate", "Serra's Boon", "Serra's Embrace", "Soot Imp", "Souldrinker", "Sustainer of the Realm");
		this.addCards(Rarity.RARE, "Fallen Angel", "Kuro, Pitlord", "Luminous Angel", "Promise of Power", "Reiver Demon", "Reya Dawnbringer", "Serra Angel", "Twilight Shepherd");
		this.addCards(Rarity.MYTHIC, "Akroma, Angel of Wrath", "Lord of the Pit");
	}
}

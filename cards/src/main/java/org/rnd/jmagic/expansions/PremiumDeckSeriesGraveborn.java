package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Premium Deck Series: Graveborn")
public final class PremiumDeckSeriesGraveborn extends SimpleExpansion
{
	public PremiumDeckSeriesGraveborn()
	{
		super();

		this.addCards(Rarity.LAND, "Swamp");
		this.addCards(Rarity.COMMON, "Duress", "Exhume", "Faceless Butcher", "Last Rites", "Polluted Mire", "Putrid Imp", "Twisted Abomination");
		this.addCards(Rarity.UNCOMMON, "Animate Dead", "Buried Alive", "Cabal Therapy", "Crystal Vein", "Diabolic Servitude", "Dread Return", "Ebon Stronghold", "Hidden Horror", "Reanimate", "Sickening Dreams", "Zombie Infestation");
		this.addCards(Rarity.RARE, "Avatar of Woe", "Blazing Archon", "Crosis, the Purger", "Entomb", "Inkwell Leviathan", "Terastodon", "Verdant Force");
		this.addCards(Rarity.MYTHIC, "Sphinx of the Steel Wind");
	}
}

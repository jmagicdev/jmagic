package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Duel Decks: Jace vs. Chandra")
public final class DuelDecksJaceVsChandra extends SimpleExpansion
{
	public DuelDecksJaceVsChandra()
	{
		super();

		this.addCards(Rarity.LAND, "Island", "Mountain");
		this.addCards(Rarity.COMMON, "AEthersnipe", "Chartooth Cougar", "Condescend", "Counterspell", "Daze", "Elemental Shaman", "Errant Ephemeron", "Fathom Seer", "Fireblast", "Firebolt", "Fireslinger", "Flamekin Brawler", "Gush", "Incinerate", "Ingot Chewer", "Inner-Flame Acolyte", "Man-o'-War", "Martyr of Frost", "Mulldrifter", "Ophidian", "Oxidda Golem", "Repulse", "Seal of Fire", "Soulbright Flamekin", "Spire Golem", "Voidmage Apprentice");
		this.addCards(Rarity.UNCOMMON, "Air Elemental", "Bottle Gnomes", "Brine Elemental", "Cone of Flame", "Fact or Fiction", "Fireball", "Flame Javelin", "Flametongue Kavu", "Flamewave Invoker", "Fledgling Mawcor", "Furnace Whelp", "Keldon Megaliths", "Magma Jet", "Mind Stone", "Pyre Charger", "Riftwing Cloudskate", "Slith Firewalker", "Terrain Generator", "Wall of Deceit", "Waterspout Djinn", "Willbender");
		this.addCards(Rarity.RARE, "Ancestral Vision", "Demonfire", "Guile", "Hostility", "Quicksilver Dragon", "Rakdos Pit Dragon");
		this.addCards(Rarity.MYTHIC, "Chandra Nalaar", "Jace Beleren");
	}
}

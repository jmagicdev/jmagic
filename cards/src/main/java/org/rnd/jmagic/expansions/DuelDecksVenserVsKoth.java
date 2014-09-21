package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Duel Decks: Venser vs. Koth")
public final class DuelDecksVenserVsKoth extends SimpleExpansion
{
	public DuelDecksVenserVsKoth()
	{
		super();

		this.addCards(Rarity.LAND, "Island", "Mountain", "Plains");
		this.addCards(Rarity.COMMON, "Armillary Sphere", "Augury Owl", "Azorius Chancery", "Chartooth Cougar", "Cosi's Ravager", "Downhill Charge", "Fiery Hellhound", "Jedit's Dragoons", "Kor Cartographer", "Neurok Invisimancer", "Overrule", "Pilgrim's Eye", "Plated Geopede", "Preordain", "Primal Plasma", "Pygmy Pyrosaur", "Revoke Existence", "Safe Passage", "Scroll Thief", "Searing Blaze", "Seismic Strike", "Sigil of Sleep", "Soaring Seacliff", "Spire Barrage", "Steel of the Godhead", "Vulshok Berserker", "Vulshok Sorcerer", "Wayfarer's Bauble", "Whitemane Lion");
		this.addCards(Rarity.UNCOMMON, "AEther Membrane", "Angelic Shield", "Anger", "Bloodfire Kavu", "Cache Raiders", "Coral Fighters", "Cryptic Annelid", "Earth Servant", "Flood Plain", "Geyser Glider", "Greater Stone Spirit", "Jaws of Stone", "Minamo Sightbender", "Mistmeadow Witch", "New Benalia", "Oblivion Ring", "Path to Exile", "Sawtooth Loon", "Sejiri Refuge", "Sky Spirit", "Slith Strider", "Stone Giant", "Vanish into Memory", "Vulshok Battlegear", "Vulshok Morningstar", "Wall of Denial");
		this.addCards(Rarity.RARE, "Bloodfire Colossus", "Clone", "Galepowder Mage", "Journeyer's Kite", "Lithophage", "Sphinx of Uthuun", "Sunblast Angel", "Torchling", "Volley of Boulders", "Windreaver");
		this.addCards(Rarity.MYTHIC, "Koth of the Hammer", "Venser, the Sojourner");
	}
}

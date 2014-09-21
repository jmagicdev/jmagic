package org.rnd.jmagic.expansions;

import org.rnd.jmagic.engine.*;

@Name("Modern Event Deck 2014")
public final class ModernEventDeck2014 extends SimpleExpansion
{
	public ModernEventDeck2014()
	{
		super();

		this.addCards(Rarity.LAND, "Plains", "Swamp");
		this.addCards(Rarity.COMMON, "Duress", "Raise the Alarm", "Soul Warden");
		this.addCards(Rarity.UNCOMMON, "Burrenton Forge-Tender", "Dismember", "Ghost Quarter", "Inquisition of Kozilek", "Intangible Virtue", "Lingering Souls", "Path to Exile", "Relic of Progenitus", "Shrine of Loyal Legions", "Spectral Procession", "Tidehollow Sculler", "Zealous Persecution");
		this.addCards(Rarity.RARE, "Caves of Koilos", "City of Brass", "Honor of the Pure", "Isolated Chapel", "Kataki, War's Wage", "Vault of the Archangel", "Windbrisk Heights");
		this.addCards(Rarity.MYTHIC, "Elspeth, Knight-Errant", "Sword of Feast and Famine");
	}
}

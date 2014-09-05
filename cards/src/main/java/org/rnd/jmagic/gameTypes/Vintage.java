package org.rnd.jmagic.gameTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;

public class Vintage extends GameType
{
	public Vintage()
	{
		super("Vintage");

		this.addRule(new DeckSizeMinimum(60));
		this.addRule(new MaximumCardCount(4));
		this.addRule(new SideboardSize(15));
		this.addRule(new SideboardAsWishboard());
		this.addRule(new CardPool());
	}

	@Name("Vintage")
	@Description("Vintage, as of 2012 September 20")
	public static class CardPool extends org.rnd.jmagic.engine.gameTypes.CardPool
	{
		public CardPool()
		{
			super(true);

			this.banCard("Advantageous Proclamation");
			this.banCard("Amulet of Quoz"); // Will never implement
			this.banCard("Backup Plan");
			this.banCard("Brago's Favor");
			this.banCard("Bronze Tablet"); // Will never implement
			this.banCard("Chaos Orb"); // Will never implement
			this.banCard("Contract from Below"); // Will never implement
			this.banCard("Darkpact"); // Will never implement
			this.banCard("Demonic Attorney"); // Will never implement
			this.banCard("Double Stroke");
			this.banCard("Falling Star"); // Will never implement
			this.banCard("Immediate Action");
			this.banCard("Iterative Analysis");
			this.banCard("Jeweled Bird"); // Will never implement
			this.banCard("Muzzio's Preparations");
			this.banCard("Power Play");
			this.banCard("Rebirth"); // Will never implement
			this.banCard("Secret Summoning");
			this.banCard("Secrets of Paradise");
			this.banCard("Sentinel Dispatch");
			this.banCard("Shahrazad");
			this.banCard("Tempest Efreet"); // Will never implement
			this.banCard("Timmerian Fiends"); // Will never implement
			this.banCard("Unexpected Potential");
			this.banCard("Worldknit");

			this.restrictCard("Ancestral Recall");
			this.restrictCard("Balance");
			this.restrictCard("Black Lotus");
			this.restrictCard("Brainstorm");
			this.restrictCard("Channel");
			this.restrictCard("Demonic Consultation");
			this.restrictCard("Demonic Tutor");
			this.restrictCard("Fastbond");
			this.restrictCard("Flash");
			this.restrictCard("Gifts Ungiven");
			this.restrictCard("Imperial Seal");
			this.restrictCard("Library of Alexandria");
			this.restrictCard("Lion's Eye Diamond");
			this.restrictCard("Lotus Petal");
			this.restrictCard("Mana Crypt");
			this.restrictCard("Mana Vault");
			this.restrictCard("Memory Jar");
			this.restrictCard("Merchant Scroll");
			this.restrictCard("Mind's Desire");
			this.restrictCard("Mox Emerald");
			this.restrictCard("Mox Jet");
			this.restrictCard("Mox Pearl");
			this.restrictCard("Mox Ruby");
			this.restrictCard("Mox Sapphire");
			this.restrictCard("Mystical Tutor");
			this.restrictCard("Necropotence");
			this.restrictCard("Ponder");
			this.restrictCard("Sol Ring");
			this.restrictCard("Strip Mine");
			this.restrictCard("Thirst for Knowledge");
			this.restrictCard("Time Vault");
			this.restrictCard("Time Walk");
			this.restrictCard("Timetwister");
			this.restrictCard("Tinker");
			this.restrictCard("Tolarian Academy");
			this.restrictCard("Trinisphere");
			this.restrictCard("Vampiric Tutor");
			this.restrictCard("Wheel of Fortune");
			this.restrictCard("Windfall");
			this.restrictCard("Yawgmoth's Bargain");
			this.restrictCard("Yawgmoth's Will");
		}
	}
}

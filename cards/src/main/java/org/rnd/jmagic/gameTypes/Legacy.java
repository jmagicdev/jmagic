package org.rnd.jmagic.gameTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;

public class Legacy extends GameType
{
	public Legacy()
	{
		super("Legacy");

		this.addRule(new DeckSizeMinimum(60));
		this.addRule(new MaximumCardCount(4));
		this.addRule(new SideboardSize(15));
		this.addRule(new SideboardAsWishboard());
		this.addRule(new CardPool());
	}

	@Name("Legacy")
	@Description("Legacy, as of 2012 September 20")
	public static class CardPool extends org.rnd.jmagic.engine.gameTypes.CardPool
	{
		public CardPool()
		{
			super(true);

			this.banCard("Advantageous Proclamation");
			this.banCard("Amulet of Quoz"); // Will never implement
			this.banCard("Ancestrall Recall");
			this.banCard("Backup Plan");
			this.banCard("Balance");
			this.banCard("Bazaar of Baghdad");
			this.banCard("Black Lotus");
			this.banCard("Black Vise");
			this.banCard("Brago's Favor");
			this.banCard("Bronze Tablet"); // Will never implement
			this.banCard("Channel");
			this.banCard("Chaos Orb"); // Will never implement
			this.banCard("Contract from Below"); // Will never implement
			this.banCard("Darkpact"); // Will never implement
			this.banCard("Demonic Attorney"); // Will never implement
			this.banCard("Demonic Consultation");
			this.banCard("Demonic Tutor");
			this.banCard("Double Stroke");
			this.banCard("Earthcraft");
			this.banCard("Falling Star"); // Will never implement
			this.banCard("Fastbond");
			this.banCard("Flash");
			this.banCard("Frantic Search");
			this.banCard("Goblin Recruiter");
			this.banCard("Gush");
			this.banCard("Hermit Druid");
			this.banCard("Immediate Action");
			this.banCard("Imperial Seal");
			this.banCard("Iterative Analysis");
			this.banCard("Jeweled Bird"); // Will never implement
			this.banCard("Library of Alexandria");
			this.banCard("Mana Crypt");
			this.banCard("Mana Drain");
			this.banCard("Mana Vault");
			this.banCard("Memory Jar");
			this.banCard("Mental Misstep");
			this.banCard("Mind Twist");
			this.banCard("Mind's Desire");
			this.banCard("Mishra's Workshop");
			this.banCard("Mox Emerald");
			this.banCard("Mox Jet");
			this.banCard("Mox Pearl");
			this.banCard("Mox Ruby");
			this.banCard("Mox Sapphire");
			this.banCard("Muzzio's Preparations");
			this.banCard("Mystical Tutor");
			this.banCard("Necropotence");
			this.banCard("Oath of Druids");
			this.banCard("Power Play");
			this.banCard("Rebirth"); // Will never implement
			this.banCard("Secret Summoning");
			this.banCard("Secrets of Paradise");
			this.banCard("Sentinel Dispatch");
			this.banCard("Shahrazad");
			this.banCard("Skullclamp");
			this.banCard("Sol Ring");
			this.banCard("Strip Mine");
			this.banCard("Survival of the Fittest");
			this.banCard("Tempest Efreet"); // Will never implement
			this.banCard("Time Vault");
			this.banCard("Time Walk");
			this.banCard("Timetwister");
			this.banCard("Timmerian Fiends"); // Will never implement
			this.banCard("Tinker");
			this.banCard("Tolarian Academy");
			this.banCard("Unexpected Potential");
			this.banCard("Vampiric Tutor");
			this.banCard("Wheel of Fortune");
			this.banCard("Windfall");
			this.banCard("Worldgorger Dragon");
			this.banCard("Worldknit");
			this.banCard("Yawgmoth's Bargain");
			this.banCard("Yawgmoth's Will");
		}
	}
}

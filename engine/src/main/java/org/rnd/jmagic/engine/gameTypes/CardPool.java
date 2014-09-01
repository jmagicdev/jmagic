package org.rnd.jmagic.engine.gameTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.GameType.*;

/** Represents a card pool (for example, Standard). */
public abstract class CardPool implements GameTypeRule
{
	private java.util.Collection<Expansion> allowedSets;

	private java.util.Collection<String> bannedList;
	private java.util.Collection<String> restrictedList;

	public CardPool()
	{
		this(false);
	}

	public CardPool(boolean eternal)
	{
		if(eternal)
			this.allowedSets = java.util.EnumSet.complementOf(java.util.EnumSet.of(Expansion.TEST));
		else
			this.allowedSets = java.util.EnumSet.noneOf(Expansion.class);

		this.bannedList = new java.util.LinkedList<String>();
		this.restrictedList = new java.util.LinkedList<String>();
	}

	public void allowSet(Expansion e)
	{
		this.allowedSets.add(e);
	}

	public void banCard(String c)
	{
		this.bannedList.add(c);
	}

	public void restrictCard(String c)
	{
		this.restrictedList.add(c);
	}

	@Override
	public boolean checkExpansion(Expansion ex)
	{
		return this.allowedSets.contains(ex);
	}

	@Override
	public boolean checkCard(Class<? extends Card> card)
	{
		if(this.bannedList.contains(card))
			return false;
		return true;
	}

	@Override
	public boolean checkDeck(java.util.Map<String, java.util.List<Class<? extends Card>>> deck)
	{
		if(this.restrictedList.isEmpty())
			return true;

		// keys are card classes, values are numbers of that card
		java.util.Collection<Class<? extends Card>> restrictedCardsPresent = new java.util.LinkedList<Class<? extends Card>>();
		for(java.util.List<Class<? extends Card>> deckPart: deck.values())
			for(Class<? extends Card> card: deckPart)
				if(this.restrictedList.contains(card))
				{
					if(restrictedCardsPresent.contains(card))
						return false;
					restrictedCardsPresent.add(card);
				}

		return true;
	}

	@Override
	public java.util.Collection<java.util.Map<String, java.util.List<Class<? extends Card>>>> exemptDecklists()
	{
		return java.util.Collections.emptySet();
	}

	@Override
	public boolean isBaseCardPool()
	{
		return true;
	}

	@Override
	public void modifyGameState(GameState physicalState)
	{
		// Card pools don't modify the game state.
	}

	@Name("THS Block Constructed")
	@Description("Theros Block Constructed, as of 2014 September 1 (Theros through Journey Into Nyx)")
	public static class Block extends CardPool
	{
		public Block()
		{
			super(false);

			this.allowSet(Expansion.INNISTRAD);
			this.allowSet(Expansion.DARK_ASCENSION);
			this.allowSet(Expansion.AVACYN_RESTORED);

			this.banCard("Intangible Virtue");
			this.banCard("Lingering Souls");
		}
	}

	@Name("Standard")
	@Description("Standard, as of 2014 September 1 (Return to Ravnica through Magic 2015)")
	public static class Standard extends CardPool
	{
		public Standard()
		{
			super(false);

			this.allowSet(Expansion.RETURN_TO_RAVNICA);
			this.allowSet(Expansion.GATECRASH);
			this.allowSet(Expansion.DRAGONS_MAZE);
			this.allowSet(Expansion.MAGIC_2014);
			this.allowSet(Expansion.THEROS);
			this.allowSet(Expansion.BORN_OF_THE_GODS);
			this.allowSet(Expansion.JOURNEY_INTO_NYX);
			this.allowSet(Expansion.MAGIC_2015);
		}
	}

	@Name("Modern")
	@Description("Modern, as of 2014 September 1 (Eighth Edition through Magic 2015)")
	public static class Modern extends CardPool
	{
		public Modern()
		{
			super(false);

			this.allowSet(Expansion.EIGHTH_EDITION);
			this.allowSet(Expansion.MIRRODIN);
			this.allowSet(Expansion.DARKSTEEL);
			this.allowSet(Expansion.FIFTH_DAWN);
			this.allowSet(Expansion.CHAMPIONS_OF_KAMIGAWA);
			this.allowSet(Expansion.BETRAYERS_OF_KAMIGAWA);
			this.allowSet(Expansion.SAVIORS_OF_KAMIGAWA);
			this.allowSet(Expansion.NINTH_EDITION);
			this.allowSet(Expansion.RAVNICA);
			this.allowSet(Expansion.GUILDPACT);
			this.allowSet(Expansion.DISSENSION);
			this.allowSet(Expansion.COLDSNAP);
			this.allowSet(Expansion.TIME_SPIRAL);
			this.allowSet(Expansion.PLANAR_CHAOS);
			this.allowSet(Expansion.FUTURE_SIGHT);
			this.allowSet(Expansion.TENTH_EDITION);
			this.allowSet(Expansion.LORWYN);
			this.allowSet(Expansion.MORNINGTIDE);
			this.allowSet(Expansion.SHADOWMOOR);
			this.allowSet(Expansion.EVENTIDE);
			this.allowSet(Expansion.SHARDS_OF_ALARA);
			this.allowSet(Expansion.CONFLUX);
			this.allowSet(Expansion.ALARA_REBORN);
			this.allowSet(Expansion.MAGIC_2010);
			this.allowSet(Expansion.ZENDIKAR);
			this.allowSet(Expansion.WORLDWAKE);
			this.allowSet(Expansion.RISE_OF_THE_ELDRAZI);
			this.allowSet(Expansion.MAGIC_2011);
			this.allowSet(Expansion.SCARS_OF_MIRRODIN);
			this.allowSet(Expansion.MIRRODIN_BESIEGED);
			this.allowSet(Expansion.NEW_PHYREXIA);
			this.allowSet(Expansion.MAGIC_2012);
			this.allowSet(Expansion.INNISTRAD);
			this.allowSet(Expansion.DARK_ASCENSION);
			this.allowSet(Expansion.AVACYN_RESTORED);
			this.allowSet(Expansion.MAGIC_2013);
			this.allowSet(Expansion.RETURN_TO_RAVNICA);
			this.allowSet(Expansion.GATECRASH);
			this.allowSet(Expansion.DRAGONS_MAZE);
			this.allowSet(Expansion.MAGIC_2014);
			this.allowSet(Expansion.THEROS);
			this.allowSet(Expansion.BORN_OF_THE_GODS);
			this.allowSet(Expansion.JOURNEY_INTO_NYX);
			this.allowSet(Expansion.MAGIC_2015);

			this.banCard("Ancestral Vision");
			this.banCard("Ancient Den");
			this.banCard("Blazing Shoal");
			this.banCard("Bloodbraid Elf");
			this.banCard("Chrome Mox");
			this.banCard("Cloudpost");
			this.banCard("Dark Depths");
			this.banCard("Deathrite Shaman");
			this.banCard("Dread Return");
			this.banCard("Glimpse of Nature");
			this.banCard("Golgari Grave Troll");
			this.banCard("Great Furnace");
			this.banCard("Green Sun's Zenith");
			this.banCard("Hypergenesis");
			this.banCard("Jace, the Mind Sculptor");
			this.banCard("Mental Misstep");
			this.banCard("Ponder");
			this.banCard("Preordain");
			this.banCard("Punishing Fire");
			this.banCard("Rite of Flame");
			this.banCard("Seat of the Synod");
			this.banCard("Second Sunrise");
			this.banCard("Seething Song");
			this.banCard("Sensei's Divining Top");
			this.banCard("Stoneforge Mystic");
			this.banCard("Skullclamp");
			this.banCard("Sword of the Meek");
			this.banCard("Tree of Tales");
			this.banCard("Umezawa's Jitte");
			this.banCard("Vault of Whispers");
		}
	}

	@Name("Legacy")
	@Description("Legacy, as of 2014 September 1")
	public static class Legacy extends CardPool
	{
		public Legacy()
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

	@Name("Vintage")
	@Description("Vintage, as of 2014 September 1")
	public static class Vintage extends CardPool
	{
		public Vintage()
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

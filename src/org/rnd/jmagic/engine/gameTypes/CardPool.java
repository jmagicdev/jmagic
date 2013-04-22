package org.rnd.jmagic.engine.gameTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.GameType.*;
import org.rnd.jmagic.cards.*;

/** Represents a card pool (for example, Standard). */
public abstract class CardPool implements GameTypeRule
{
	private java.util.Collection<Expansion> allowedSets;

	private java.util.Collection<Class<? extends Card>> bannedList;
	private java.util.Collection<Class<? extends Card>> restrictedList;

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

		this.bannedList = new java.util.LinkedList<Class<? extends Card>>();
		this.restrictedList = new java.util.LinkedList<Class<? extends Card>>();
	}

	public void allowSet(Expansion e)
	{
		this.allowedSets.add(e);
	}

	public void banCard(Class<? extends Card> c)
	{
		this.bannedList.add(c);
	}

	public void restrictCard(Class<? extends Card> c)
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

	@Name("ISD Block Constructed")
	@Description("Innistrad Block Constructed, as of 2012 June 20 (Innistrad through Avacyn Restored)")
	public static class Block extends CardPool
	{
		public Block()
		{
			super(false);

			this.allowSet(Expansion.INNISTRAD);
			this.allowSet(Expansion.DARK_ASCENSION);
			this.allowSet(Expansion.AVACYN_RESTORED);

			this.banCard(IntangibleVirtue.class);
			this.banCard(LingeringSouls.class);
		}
	}

	@Name("Standard")
	@Description("Standard, as of 2012 September 20 (Innistrad through Return to Ravnica)")
	public static class Standard extends CardPool
	{
		public Standard()
		{
			super(false);

			this.allowSet(Expansion.INNISTRAD);
			this.allowSet(Expansion.DARK_ASCENSION);
			this.allowSet(Expansion.AVACYN_RESTORED);
			this.allowSet(Expansion.MAGIC_2013);
			this.allowSet(Expansion.RETURN_TO_RAVNICA);
		}
	}

	@Name("Extended")
	@Description("Extended, as of 2012 September 20 (Zendikar through Return to Ravnica)")
	public static class Extended extends CardPool
	{
		public Extended()
		{
			super(false);

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

			this.banCard(JacetheMindSculptor.class);
			this.banCard(MentalMisstep.class);
			this.banCard(Ponder.class);
			this.banCard(Preordain.class);
			this.banCard(StoneforgeMystic.class);
		}
	}

	@Name("Modern")
	@Description("Modern, as of 2012 September 20 (Eighth Edition through Return to Ravnica)")
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

			this.banCard(AncestralVision.class);
			this.banCard(AncientDen.class);
			this.banCard(Bitterblossom.class);
			this.banCard(BlazingShoal.class);
			this.banCard(ChromeMox.class);
			this.banCard(Cloudpost.class);
			this.banCard(DarkDepths.class);
			this.banCard(DreadReturn.class);
			// this.banCard(GlimpseofNature.class);
			this.banCard(GolgariGraveTroll.class);
			this.banCard(GreatFurnace.class);
			this.banCard(GreenSunsZenith.class);
			this.banCard(Hypergenesis.class);
			this.banCard(JacetheMindSculptor.class);
			this.banCard(MentalMisstep.class);
			this.banCard(Ponder.class);
			this.banCard(Preordain.class);
			this.banCard(PunishingFire.class);
			this.banCard(RiteofFlame.class);
			this.banCard(SeatoftheSynod.class);
			this.banCard(SenseisDiviningTop.class);
			this.banCard(StoneforgeMystic.class);
			this.banCard(Skullclamp.class);
			this.banCard(SwordoftheMeek.class);
			this.banCard(TreeofTales.class);
			this.banCard(UmezawasJitte.class);
			this.banCard(VaultofWhispers.class);
			this.banCard(WildNacatl.class);
		}
	}

	@Name("Legacy")
	@Description("Legacy, as of 2012 September 20")
	public static class Legacy extends CardPool
	{
		public Legacy()
		{
			super(true);

			// this.banCard(AmuletofQuoz.class); // Will never implement
			// this.banCard(AncestrallRecall.class);
			// this.banCard(Balance.class);
			this.banCard(BazaarofBaghdad.class);
			this.banCard(BlackLotus.class);
			// this.banCard(BlackVise.class);
			// this.banCard(BronzeTablet.class); // Will never implement
			// this.banCard(Channel.class);
			// this.banCard(ChaosOrb.class); // Will never implement
			// this.banCard(ContractfromBelow.class); // Will never implement
			// this.banCard(Darkpact.class); // Will never implement
			// this.banCard(DemonicAttorney.class); // Will never implement
			this.banCard(DemonicConsultation.class);
			this.banCard(DemonicTutor.class);
			// this.banCard(Earthcraft.class);
			// this.banCard(FallingStar.class); // Will never implement
			this.banCard(Fastbond.class);
			// this.banCard(Flash.class);
			// this.banCard(FranticSearch.class);
			// this.banCard(GoblinRecruiter.class);
			this.banCard(Gush.class);
			// this.banCard(HermitDruid.class);
			this.banCard(ImperialSeal.class);
			// this.banCard(JeweledBird.class); // Will never implement
			this.banCard(LibraryofAlexandria.class);
			this.banCard(ManaCrypt.class);
			this.banCard(ManaDrain.class);
			this.banCard(ManaVault.class);
			// this.banCard(MemoryJar.class);
			this.banCard(MentalMisstep.class);
			// this.banCard(MindTwist.class);
			this.banCard(MindsDesire.class);
			this.banCard(MishrasWorkshop.class);
			this.banCard(MoxEmerald.class);
			this.banCard(MoxJet.class);
			this.banCard(MoxPearl.class);
			this.banCard(MoxRuby.class);
			this.banCard(MoxSapphire.class);
			this.banCard(MysticalTutor.class);
			// this.banCard(Necropotence.class);
			// this.banCard(OathofDruids.class);
			// this.banCard(Rebirth.class); // Will never implement
			// this.banCard(Shahrazad.class);
			this.banCard(Skullclamp.class);
			this.banCard(SolRing.class);
			this.banCard(StripMine.class);
			this.banCard(SurvivaloftheFittest.class);
			// this.banCard(TempestEfreet.class); // Will never implement
			this.banCard(TimeVault.class);
			this.banCard(TimeWalk.class);
			this.banCard(Timetwister.class);
			// this.banCard(TimmerianFiends.class); // Will never implement
			this.banCard(Tinker.class);
			this.banCard(TolarianAcademy.class);
			this.banCard(VampiricTutor.class);
			this.banCard(WheelofFortune.class);
			// this.banCard(Windfall.class);
			this.banCard(WorldgorgerDragon.class);
			// this.banCard(YawgmothsBargain.class);
			this.banCard(YawgmothsWill.class);
		}
	}

	@Name("Vintage")
	@Description("Vintage, as of 2012 September 20")
	public static class Vintage extends CardPool
	{
		public Vintage()
		{
			super(true);

			// this.banCard(AmuletofQuoz.class); // Will never implement
			// this.banCard(BronzeTablet.class); // Will never implement
			// this.banCard(ChaosOrb.class); // Will never implement
			// this.banCard(ContractfromBelow.class); // Will never implement
			// this.banCard(Darkpact.class); // Will never implement
			// this.banCard(DemonicAttorney.class); // Will never implement
			// this.banCard(FallingStar.class); // Will never implement
			// this.banCard(JeweledBird.class); // Will never implement
			// this.banCard(Rebirth.class); // Will never implement
			// this.banCard(Shahrazad.class);
			// this.banCard(TempestEfreet.class); // Will never implement
			// this.banCard(TimmerianFiends.class); // Will never implement

			this.restrictCard(AncestralRecall.class);
			// this.restrictCard(Balance.class);
			this.restrictCard(BlackLotus.class);
			this.restrictCard(Brainstorm.class);
			this.restrictCard(BurningWish.class);
			// this.restrictCard(Channel.class);
			this.restrictCard(DemonicConsultation.class);
			this.restrictCard(DemonicTutor.class);
			this.restrictCard(Fastbond.class);
			// this.restrictCard(Flash.class);
			this.restrictCard(GiftsUngiven.class);
			this.restrictCard(ImperialSeal.class);
			this.restrictCard(LibraryofAlexandria.class);
			this.restrictCard(LionsEyeDiamond.class);
			this.restrictCard(LotusPetal.class);
			this.restrictCard(ManaCrypt.class);
			this.restrictCard(ManaVault.class);
			// this.restrictCard(MemoryJar.class);
			this.restrictCard(MerchantScroll.class);
			this.restrictCard(MindsDesire.class);
			this.restrictCard(MoxEmerald.class);
			this.restrictCard(MoxJet.class);
			this.restrictCard(MoxPearl.class);
			this.restrictCard(MoxRuby.class);
			this.restrictCard(MoxSapphire.class);
			this.restrictCard(MysticalTutor.class);
			// this.restrictCard(Necropotence.class);
			this.restrictCard(Ponder.class);
			this.restrictCard(Regrowth.class);
			this.restrictCard(SolRing.class);
			this.restrictCard(StripMine.class);
			this.restrictCard(ThirstforKnowledge.class);
			this.restrictCard(TimeVault.class);
			this.restrictCard(TimeWalk.class);
			this.restrictCard(Timetwister.class);
			this.restrictCard(Tinker.class);
			this.restrictCard(TolarianAcademy.class);
			this.restrictCard(Trinisphere.class);
			this.restrictCard(VampiricTutor.class);
			this.restrictCard(WheelofFortune.class);
			// this.restrictCard(Windfall.class);
			// this.restrictCard(YawgmothsBargain.class);
			this.restrictCard(YawgmothsWill.class);
		}
	}
}

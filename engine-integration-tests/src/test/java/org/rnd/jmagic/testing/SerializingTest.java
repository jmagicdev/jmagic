package org.rnd.jmagic.testing;

import org.junit.*;

import static org.junit.Assert.*;

import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;

public class SerializingTest extends JUnitTest
{
	private void common(boolean stressTest)
	{
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		// Player 0's first turn
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		if(!stressTest)
			return;

		respondWith(getLandAction(SacredFoundry.class));
		respondWith(Answer.YES);

		respondWith(getSpellAction(SteppeLynx.class));
		respondWith(getIntrinsicAbilityAction(SubType.PLAINS));
		donePlayingManaAbilities();
		pass();
		pass();

		// Player 1's first turn
		goToPhase(Phase.PhaseType.BEGINNING);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getLandAction(Island.class));

		// Player 0's second turn
		goToPhase(Phase.PhaseType.BEGINNING);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getLandAction(VerdantCatacombs.class));

		// Resolve Steppe Lynx's ability
		pass();
		pass();

		respondWith(getAbilityAction(org.rnd.jmagic.cardTemplates.FetchLand.Fetch.class));
		pass();
		pass();
		respondWith(pullChoice(Forest.class));

		// Resolve Steppe Lynx's ability
		pass();
		pass();

		respondWith(getSpellAction(Tarmogoyf.class));
		respondWith(getIntrinsicAbilityAction(SubType.FOREST));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
		donePlayingManaAbilities();
		pass();
		pass();

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(SteppeLynx.class));

		// Player 1's second turn
		goToPhase(Phase.PhaseType.BEGINNING);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getLandAction(Island.class));

		// Player 0's third turn
		goToPhase(Phase.PhaseType.BEGINNING);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		pass();

		// Spell 1
		respondWith(getSpellAction(HighTide.class));
		respondWith(getIntrinsicAbilityAction(SubType.ISLAND));
		donePlayingManaAbilities();
		pass();
		pass();

		pass();

		// Spell 2
		respondWith(getSpellAction(Reset.class));
		respondWith(getIntrinsicAbilityAction(SubType.ISLAND));
		donePlayingManaAbilities();
		pass();
		pass();

		pass();

		// Spell 3
		respondWith(getSpellAction(HighTide.class));
		respondWith(getIntrinsicAbilityAction(SubType.ISLAND));
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLUE));
		pass();
		pass();

		pass();

		// Spell 4
		respondWith(getSpellAction(HighTide.class));
		donePlayingManaAbilities();
		pass();
		pass();

		pass();

		// Spell 5
		respondWith(getSpellAction(Reset.class));
		respondWith(getIntrinsicAbilityAction(SubType.ISLAND));
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLUE, Color.BLUE));
		pass();
		pass();

		pass();

		// Spell 6
		respondWith(getSpellAction(HighTide.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLUE));
		pass();
		pass();

		pass();

		// Spell 7
		respondWith(getSpellAction(Meditate.class));
		respondWith(getIntrinsicAbilityAction(SubType.ISLAND));
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLUE, Color.BLUE, Color.BLUE));
		pass();
		pass();

		pass();

		// Spell 8 (will storm for 7)
		respondWith(getSpellAction(BrainFreeze.class));
		respondWith(getTarget(player(0)));
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLUE, Color.BLUE));

		// Spell 9
		respondWith(getSpellAction(Remand.class));
		respondWith(getIntrinsicAbilityAction(SubType.ISLAND));
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLUE, Color.BLUE));
		pass();
		pass();

		pass();

		// Spell 10 (will storm for 9)
		respondWith(getSpellAction(BrainFreeze.class));
		respondWith(getTarget(player(0)));
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLUE, Color.BLUE));

		// Spell 11 (will storm for 10)
		respondWith(getSpellAction(BrainFreeze.class));
		respondWith(getTarget(player(0)));
		donePlayingManaAbilities();

		assertEquals(5, this.game.actualState.stack().objects.size());

		// Resolve a storm trigger
		pass();
		pass();

		for(int i = 0; i < 10; i++)
			respondWith(Answer.NO);

		assertEquals(14, this.game.actualState.stack().objects.size());

		// Resolve 10 storm copies, plus an original
		for(int i = 0; i < 11; i++)
		{
			pass();
			pass();
		}

		assertEquals(3, this.game.actualState.stack().objects.size());

		// Resolve another storm trigger
		pass();
		pass();

		for(int i = 0; i < 9; i++)
			respondWith(Answer.NO);

		assertEquals(11, this.game.actualState.stack().objects.size());

		// Resolve 9 storm copies, plus an original
		for(int i = 0; i < 10; i++)
		{
			pass();
			pass();
		}

		assertEquals(1, this.game.actualState.stack().objects.size());

		// Resolve another storm trigger
		pass();
		pass();

		for(int i = 0; i < 7; i++)
			respondWith(Answer.NO);

		assertEquals(7, this.game.actualState.stack().objects.size());

		// Resolve 10 storm copies
		for(int i = 0; i < 7; i++)
		{
			pass();
			pass();
		}

		// Player 0's fourth turn (skip Player 1's turn because of Meditate,
		// fail to draw a card, Player 1 wins)
		goToPhase(Phase.PhaseType.BEGINNING);
		pass();
		pass();

		assertEquals(player(1), this.winner);
	}

	@Ignore
	@Test
	public void compareAgainst()
	{
		this.addDeck(AridMesa.class, AridMesa.class, AridMesa.class, AridMesa.class, ScaldingTarn.class, ScaldingTarn.class, ScaldingTarn.class, ScaldingTarn.class, VerdantCatacombs.class, VerdantCatacombs.class, VerdantCatacombs.class, Forest.class, Mountain.class, Plains.class, SacredFoundry.class, SacredFoundry.class, StompingGround.class, StompingGround.class, StompingGround.class, TempleGarden.class, TreetopVillage.class, KirdApe.class, KirdApe.class, KirdApe.class, KirdApe.class, SteppeLynx.class, SteppeLynx.class, SteppeLynx.class, WildNacatl.class, WildNacatl.class, WildNacatl.class, WildNacatl.class, Tarmogoyf.class, Tarmogoyf.class, Tarmogoyf.class, PlatedGeopede.class, PlatedGeopede.class, PlatedGeopede.class, PlatedGeopede.class, KnightoftheReliquary.class, KnightoftheReliquary.class, LightningBolt.class, LightningBolt.class, LightningBolt.class, LightningBolt.class, LightningHelix.class, LightningHelix.class, LightningHelix.class, LightningHelix.class, MoltenRain.class, MoltenRain.class, MoltenRain.class, MoltenRain.class, PathtoExile.class, PathtoExile.class, PathtoExile.class, Tarmogoyf.class, SteppeLynx.class, SacredFoundry.class, VerdantCatacombs.class);
		this.addDeck(Island.class, Island.class, Island.class, Island.class, Island.class, Island.class, Island.class, Island.class, Island.class, Island.class, FloodedStrand.class, FloodedStrand.class, FloodedStrand.class, PollutedDelta.class, PollutedDelta.class, PollutedDelta.class, Brainstorm.class, Brainstorm.class, Brainstorm.class, Brainstorm.class, CrypticCommand.class, CrypticCommand.class, CrypticCommand.class, CrypticCommand.class, FlashofInsight.class, FlashofInsight.class, FlashofInsight.class, FlashofInsight.class, Impulse.class, Impulse.class, Impulse.class, Impulse.class, Meditate.class, Meditate.class, Opt.class, Opt.class, Opt.class, Peek.class, Reset.class, Reset.class, Turnabout.class, Turnabout.class, Turnabout.class, Disrupt.class, Disrupt.class, Disrupt.class, Remand.class, Remand.class, BrainFreeze.class, Remand.class, BrainFreeze.class, Meditate.class, HighTide.class, HighTide.class, HighTide.class, Reset.class, Reset.class, Island.class, HighTide.class, Island.class);
		this.common(true);
	}

	@Test
	public void serializable() throws ClassNotFoundException, java.io.IOException, InterruptedException
	{
		final java.nio.channels.Pipe gameToPlayerOne = java.nio.channels.Pipe.open();
		final java.nio.channels.Pipe gameToPlayerTwo = java.nio.channels.Pipe.open();
		final java.nio.channels.Pipe playerOneToGame = java.nio.channels.Pipe.open();
		final java.nio.channels.Pipe playerTwoToGame = java.nio.channels.Pipe.open();

		final PlayerInterface playerOneTest = createInterface(AridMesa.class, AridMesa.class, AridMesa.class, AridMesa.class, ScaldingTarn.class, ScaldingTarn.class, ScaldingTarn.class, ScaldingTarn.class, VerdantCatacombs.class, VerdantCatacombs.class, VerdantCatacombs.class, Forest.class, Mountain.class, Plains.class, SacredFoundry.class, SacredFoundry.class, StompingGround.class, StompingGround.class, StompingGround.class, TempleGarden.class, TreetopVillage.class, KirdApe.class, KirdApe.class, KirdApe.class, KirdApe.class, SteppeLynx.class, SteppeLynx.class, SteppeLynx.class, WildNacatl.class, WildNacatl.class, WildNacatl.class, WildNacatl.class, Tarmogoyf.class, Tarmogoyf.class, Tarmogoyf.class, PlatedGeopede.class, PlatedGeopede.class, PlatedGeopede.class, PlatedGeopede.class, KnightoftheReliquary.class, KnightoftheReliquary.class, LightningBolt.class, LightningBolt.class, LightningBolt.class, LightningBolt.class, LightningHelix.class, LightningHelix.class, LightningHelix.class, LightningHelix.class, MoltenRain.class, MoltenRain.class, MoltenRain.class, MoltenRain.class, PathtoExile.class, PathtoExile.class, PathtoExile.class, Tarmogoyf.class, SteppeLynx.class, SacredFoundry.class, VerdantCatacombs.class);
		final PlayerInterface playerTwoTest = createInterface(Island.class, Island.class, Island.class, Island.class, Island.class, Island.class, Island.class, Island.class, Island.class, Island.class, FloodedStrand.class, FloodedStrand.class, FloodedStrand.class, PollutedDelta.class, PollutedDelta.class, PollutedDelta.class, Brainstorm.class, Brainstorm.class, Brainstorm.class, Brainstorm.class, CrypticCommand.class, CrypticCommand.class, CrypticCommand.class, CrypticCommand.class, FlashofInsight.class, FlashofInsight.class, FlashofInsight.class, FlashofInsight.class, Impulse.class, Impulse.class, Impulse.class, Impulse.class, Meditate.class, Meditate.class, Opt.class, Opt.class, Opt.class, Peek.class, Reset.class, Reset.class, Turnabout.class, Turnabout.class, Turnabout.class, Disrupt.class, Disrupt.class, Disrupt.class, Remand.class, Remand.class, BrainFreeze.class, Remand.class, BrainFreeze.class, Meditate.class, HighTide.class, HighTide.class, HighTide.class, Reset.class, Reset.class, Island.class, HighTide.class, Island.class);

		final java.util.concurrent.atomic.AtomicReference<Exception> playerOneException = new java.util.concurrent.atomic.AtomicReference<Exception>();
		Thread playerOne = new Thread("PlayerOne")
		{
			@Override
			public void run()
			{
				try
				{
					// Always create the output stream first and flush it before
					// creating the input stream so a stream header is
					// immediately available
					java.io.ObjectOutputStream out = org.rnd.util.ChannelRouter.wrapChannelInObjectOutputStream(playerOneToGame.sink());
					out.flush();

					java.io.ObjectInputStream in = org.rnd.util.ChannelRouter.wrapChannelInObjectInputStream(gameToPlayerOne.source());

					org.rnd.jmagic.comms.StreamPlayer.run(in, out, playerOneTest);
				}
				catch(Game.InterruptedGameException e)
				{
					// This is expected when this thread is blocked while
					// waiting for a response from the test engine
				}
				catch(ClassNotFoundException e)
				{
					playerOneException.set(e);
				}
				catch(java.io.IOException e)
				{
					playerOneException.set(e);
				}
			}
		};
		playerOne.start();

		final java.util.concurrent.atomic.AtomicReference<Exception> playerTwoException = new java.util.concurrent.atomic.AtomicReference<Exception>();
		Thread playerTwo = new Thread("PlayerTwo")
		{
			@Override
			public void run()
			{
				try
				{
					// Always create the output stream first and flush it before
					// creating the input stream so a stream header is
					// immediately available
					java.io.ObjectOutputStream out = org.rnd.util.ChannelRouter.wrapChannelInObjectOutputStream(playerTwoToGame.sink());
					out.flush();

					java.io.ObjectInputStream in = org.rnd.util.ChannelRouter.wrapChannelInObjectInputStream(gameToPlayerTwo.source());

					org.rnd.jmagic.comms.StreamPlayer.run(in, out, playerTwoTest);
				}
				catch(Game.InterruptedGameException e)
				{
					// This is expected when this thread is blocked while
					// waiting for a response from the test engine
				}
				catch(ClassNotFoundException e)
				{
					playerTwoException.set(e);
				}
				catch(java.io.IOException e)
				{
					playerTwoException.set(e);
				}
			}
		};
		playerTwo.start();

		// Always create the output stream first and flush it before
		// creating the input stream so a stream header is immediately
		// available
		java.io.ObjectOutputStream out = org.rnd.util.ChannelRouter.wrapChannelInObjectOutputStream(gameToPlayerOne.sink());
		out.flush();

		java.io.ObjectInputStream in = org.rnd.util.ChannelRouter.wrapChannelInObjectInputStream(playerOneToGame.source());

		addInterface(new org.rnd.jmagic.comms.StreamPlayer(in, out));

		// Always create the output stream first and flush it before
		// creating the input stream so a stream header is immediately
		// available
		out = org.rnd.util.ChannelRouter.wrapChannelInObjectOutputStream(gameToPlayerTwo.sink());
		out.flush();

		in = org.rnd.util.ChannelRouter.wrapChannelInObjectInputStream(playerTwoToGame.source());

		addInterface(new org.rnd.jmagic.comms.StreamPlayer(in, out));

		// Change "false" to "true" here to run a stress-test
		this.common(false);

		playerOne.interrupt();
		playerOne.join();
		Exception e = playerOneException.get();
		if(null != e)
		{
			if(e instanceof ClassNotFoundException)
				throw (ClassNotFoundException)e;
			else if(e instanceof java.io.IOException)
				throw (java.io.IOException)e;
			else
				fail("Player one thread threw unexpected exception " + e);
		}

		playerTwo.interrupt();
		playerTwo.join();
		e = playerTwoException.get();
		if(null != e)
		{
			if(e instanceof ClassNotFoundException)
				throw (ClassNotFoundException)e;
			else if(e instanceof java.io.IOException)
				throw (java.io.IOException)e;
			else
				fail("Player one thread threw unexpected exception " + e);
		}
	}
}

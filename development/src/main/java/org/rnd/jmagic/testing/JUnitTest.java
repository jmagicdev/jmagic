package org.rnd.jmagic.testing;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.interfaceAdapters.*;
import org.rnd.jmagic.sanitized.*;

/**
 * The JUnit test base class.
 *
 * Each JUnitTest runs in two threads: a test thread which is the main thread
 * JUnit starts and an engine thread which runs the Game in the background. When
 * the test thread starts the engine thread, it waits on the test object. The
 * engine thread notifies the test object whenever a choice needs to be made,
 * then waits on the test object. The test thread can now make assertions on the
 * current state of the game and respond to the choice that needs to be made.
 * When it does the latter, it notifies the test object when it makes the choice
 * then waits on the test object. This pattern continues back and forth until
 * one of the threads reaches the end and signals the other to complete.
 */
public abstract class JUnitTest
{
	@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
	protected static @interface ReportFailure
	{
		// empty
	}

	public class JMagicTestWatcher extends org.junit.rules.TestWatcher
	{
		@Override
		protected void failed(Throwable e, org.junit.runner.Description d)
		{
			try
			{
				if(d.getTestClass().getMethod(d.getMethodName()).getAnnotation(ReportFailure.class) != null)
					printState();
			}
			catch(SecurityException e1)
			{
				// do nothing
			}
			catch(NoSuchMethodException e1)
			{
				// do nothing
			}
		}

		@Override
		protected void finished(org.junit.runner.Description description)
		{
			cleanup();
		}

		public void printState()
		{
			printState(System.out, JUnitTest.this.game.actualState, 0);
		}

		public void printState(java.io.PrintStream out, GameState state, int indent)
		{
			for(Zone zone: state.getAll(Zone.class))
			{
				out.println(indent(indent) + "[" + zone.getName() + "]");
				for(GameObject o: zone.objects)
					out.println(indent(indent + 1) + "[" + o.getName() + "]");
			}
		}

		private String indent(int i)
		{
			if(i <= 0)
				return "";
			StringBuffer s = new StringBuffer();
			for(; i > 0; --i)
				s.append('\t');
			return s.toString();
		}

		public void cleanup()
		{
			if(null != JUnitTest.this.engine)
				JUnitTest.this.engine.interrupt();

			JUnitTest.this.choices = null;
			JUnitTest.this.choicesReady = false;
			JUnitTest.this.choiceType = null;
			JUnitTest.this.engine = null;
			JUnitTest.this.engineThrowable = null;
			JUnitTest.this.game = null;
			JUnitTest.this.choosingPlayerID = -1;
			JUnitTest.this.numTestInterfaces = 0;
			JUnitTest.this.playerIDs = null;
			JUnitTest.this.playerInterfaces = new java.util.LinkedList<PlayerInterface>();
			JUnitTest.this.responseObject = null;
			JUnitTest.this.winner = null;
		}
	}

	// This extends InterfaceAdapter instead of implementing PlayerInterface so
	// it can be wrapped with a ShortcutInterface
	private class TestInterface implements PlayerInterface
	{
		private Deck deck;
		private int playerNum;

		public TestInterface(Deck deck, int playerNum)
		{
			this.deck = deck;
			this.playerNum = playerNum;
		}

		@Override
		public void alertChoice(int playerID, ChooseParameters<?> choice)
		{
			// We don't care about this alert
		}

		@Override
		public void alertError(ErrorParameters parameters)
		{
			// TODO: Since causing a test failure throws an Error, we can't do
			// so here and expect the test to fail correctly, so find a way to
			// do it in the main testing thread
		}

		@Override
		public void alertEvent(SanitizedEvent event)
		{
			// We don't care about this alert
		}

		@Override
		public void alertState(SanitizedGameState state)
		{
			// We don't care about this alert
		}

		@Override
		public void alertStateReversion(ReversionParameters parameters)
		{
			// We don't care about this alert
		}

		@Override
		public void alertWaiting(SanitizedPlayer who)
		{
			// We don't care about this alert
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T extends java.io.Serializable> java.util.List<Integer> choose(ChooseParameters<T> parameterObject)
		{
			java.util.List<Integer> ret = new java.util.LinkedList<Integer>();

			// we never care about order of simultaneous objects' timestamps
			if(parameterObject.type == PlayerInterface.ChoiceType.TIMESTAMPS)
			{
				for(int i = 0; i < parameterObject.choices.size(); ++i)
					ret.add(i);
				return ret;
			}

			JUnitTest.this.choices = new Set(parameterObject.choices);
			JUnitTest.this.choiceType = parameterObject.type;
			JUnitTest.this.choosingPlayerID = JUnitTest.this.playerIDs[this.playerNum];
			Object response = JUnitTest.this.getResponse();
			JUnitTest.this.choices = null;
			JUnitTest.this.choiceType = null;

			if(response == null)
			{
				return ret;
			}
			else if(parameterObject.choices.contains(response))
			{
				for(int i = 0; i < parameterObject.choices.size(); ++i)
					if(parameterObject.choices.get(i).equals(response))
						ret.add(i);
				return ret;
			}
			else if(response instanceof java.util.List)
			{
				for(T t: (java.util.List<T>)response)
					for(int i = 0; i < parameterObject.choices.size(); ++i)
						if(parameterObject.choices.get(i).equals(t))
							ret.add(i);
				return ret;
			}

			// TODO: Since causing a test failure throws an Error, we can't do
			// so here and expect the test to fail correctly, so find a way to
			// do it in the main testing thread
			fail("Choice provided (" + response.toString() + ") doesn't match available choices (" + parameterObject.choices.toString() + ")");
			return null;
		}

		@Override
		public int chooseNumber(org.rnd.util.NumberRange range, String description)
		{
			// If the range contains only one number, return it
			Integer size = range.size();
			if(size != null && size == 1)
				return range.getLower();

			JUnitTest.this.choosingPlayerID = JUnitTest.this.playerIDs[this.playerNum];
			return JUnitTest.this.<Integer>getResponse();
		}

		@Override
		public void divide(int quantity, int minimum, int whatFrom, String beingDivided, java.util.List<SanitizedTarget> targets)
		{
			if(targets.size() == 1)
			{
				targets.get(0).division = quantity;
				return;
			}

			JUnitTest.this.choices = new Set(targets);
			JUnitTest.this.choosingPlayerID = JUnitTest.this.playerIDs[this.playerNum];
			JUnitTest.this.<Object>getResponse();
			JUnitTest.this.choices = null;
		}

		@Override
		public Deck getDeck()
		{
			return this.deck;
		}

		@Override
		public String getName()
		{
			return ("Player " + this.playerNum);
		}

		@Override
		public void setPlayerID(int playerID)
		{
			JUnitTest.this.playerIDs[this.playerNum] = playerID;
		}
	}

	@Rule
	public JMagicTestWatcher testWatcher;

	protected volatile Set choices;

	private volatile boolean choicesReady;

	protected volatile PlayerInterface.ChoiceType choiceType;

	protected int choosingPlayerID;

	private Thread engine;

	private volatile boolean engineFinished;

	private Throwable engineThrowable;

	protected volatile Game game;

	private int numTestInterfaces;

	private int[] playerIDs;

	private java.util.List<PlayerInterface> playerInterfaces;

	private volatile Object responseObject;

	protected volatile Player winner;

	public JUnitTest()
	{
		this.testWatcher = new JMagicTestWatcher();
		this.testWatcher.cleanup();
	}

	protected final void addDeck(Class<?>... cards)
	{
		this.addInterface(this.createInterface(cards));
	}

	protected final void addInterface(PlayerInterface player)
	{
		this.playerInterfaces.add(player);
	}

	/**
	 * Adds mana to the mana pool of the player who is currently choosing. Note
	 * that in the case of a turn being controlled, this will be the player
	 * controlling the turn, not the player taking the turn.
	 * 
	 * @param mana The mana to add.
	 */
	protected final void addMana(String mana)
	{
		this.game.physicalState.<Player>get(this.choosingPlayerID).pool.addAll(new ManaPool(mana).explode("").iterator().next().manaCost);
	}

	/**
	 * Cover for castAndResolveSpell(spell, spell's mana cost, targets).
	 */
	protected final void castAndResolveSpell(Class<? extends Card> spell, Class<?>... targets)
	{
		castAndResolveSpell(spell, spell.getAnnotation(ManaCost.class).value(), targets);
	}

	/**
	 * Announces the specified spell declaring the specified targets (if any),
	 * adds the specified mana to the acting player's mana pool, and passes once
	 * for each player in the game. Note that, if any triggered abilities
	 * trigger on the casting of this spell and those abilities stack, the spell
	 * won't actually have resolved by the time this function returns.
	 * <p>
	 * This won't work while turns are being controlled, since
	 * {@link #addMana(String)} will add the mana to the wrong player's pool.
	 * 
	 * @param spell The class of spell to cast.
	 * @param mana A string representation of the mana to add; e.g., "4GG".
	 * @param targets The classes of the targets of the spell, in order.
	 */
	protected final void castAndResolveSpell(Class<? extends Card> spell, String mana, Class<?>... targets)
	{
		respondWith(getSpellAction(spell));
		SanitizedTarget[] newTargets = new SanitizedTarget[targets.length];
		for(int i = 0; i < targets.length; ++i)
		{
			newTargets[i] = getTarget(targets[i]);
		}
		finishCasting(mana, newTargets);
	}

	/**
	 * Announces the specified spell declaring the specified targets (if any),
	 * adds the specified mana to the acting player's mana pool, and passes once
	 * for each player in the game. Note that, if any triggered abilities
	 * trigger on the casting of this spell and those abilities stack, the spell
	 * won't actually have resolved by the time this function returns.
	 * 
	 * @param spell The class of spell to cast.
	 * @param mana A string representation of the mana to add; e.g., "4GG".
	 * @param targets The targets of the spell, in order.
	 */
	protected final void castAndResolveSpell(Class<? extends Card> spell, String mana, Identified firstTarget, Identified... targets)
	{
		respondWith(getSpellAction(spell));
		SanitizedTarget[] newTargets = new SanitizedTarget[targets.length + 1];
		newTargets[0] = getTarget(firstTarget);
		for(int i = 0; i < targets.length; ++i)
		{
			newTargets[i + 1] = getTarget(targets[i]);
		}
		finishCasting(mana, newTargets);
	}

	protected final void confirmCantBePlayed(Class<? extends GameObject> action)
	{
		for(SanitizedCastSpellOrActivateAbilityAction choice: this.choices.getAll(SanitizedCastSpellOrActivateAbilityAction.class))
			if(action.isAssignableFrom(this.game.actualState.get(choice.toBePlayed).getClass()))
				fail(action.getSimpleName() + " could be played and should not be able to be played.");
	}

	@SuppressWarnings("unchecked")
	protected final PlayerInterface createInterface(Class<?>... cards)
	{
		if(null != this.game)
			fail("Can't create interfaces after the game has already started");

		TestInterface jUnitInterface = new TestInterface(new Deck((Class<? extends Card>[])cards), this.numTestInterfaces);
		++this.numTestInterfaces;
		return new ShortcutInterface(jUnitInterface);
	}

	protected final void declareNoAttackers()
	{
		if(PlayerInterface.ChoiceType.ATTACK != this.choiceType)
			fail("Must be declaring attackers to declare none");
		respondWith();
	}

	protected final void declareNoBlockers()
	{
		if(PlayerInterface.ChoiceType.BLOCK != this.choiceType)
			fail("Must be declaring blockers to declare none");
		respondWith();
	}

	/**
	 * Divides damage among some number of objects.
	 * 
	 * @param divisions Keys are IDs of objects to divide damage among; values
	 * are amounts of damage.
	 */
	protected final void divide(java.util.Map<Integer, Integer> divisions)
	{
		if(null != this.choiceType)
			fail("Can't divide damage when a choice has to be made");

		for(SanitizedTarget t: this.choices.getAll(SanitizedTarget.class))
		{
			if(!divisions.containsKey(t.targetID))
				org.junit.Assert.fail("Unexpected target found: " + t);

			t.division = divisions.remove(t.targetID);
		}

		if(!divisions.isEmpty())
			fail("Extra divisions specified");
		respondWith();
	}

	protected final void donePlayingManaAbilities()
	{
		if(PlayerInterface.ChoiceType.ACTIVATE_MANA_ABILITIES != this.choiceType)
			fail("Must be able to play mana abilities to finish playing them");
		respondWith();
	}

	private final void finishCasting(String mana, SanitizedTarget... targets)
	{
		for(SanitizedTarget target: targets)
			respondWith(target);

		if(this.choiceType == org.rnd.jmagic.engine.PlayerInterface.ChoiceType.ALTERNATE_COST)
			respondWith();

		if(mana != null && mana.length() > 0 && !mana.equals("0"))
		{
			addMana(mana);
			donePlayingManaAbilities();
		}

		// If asked to order costs, respond arbitrarily.
		if(this.choiceType == PlayerInterface.ChoiceType.COSTS)
			respondArbitrarily();

		for(int i = this.playerIDs.length; i > 0; i--)
			pass();
	}

	private final <T extends Identified> T get(int ID)
	{
		if(this.game.actualState.containsIdentified(ID))
			return this.game.actualState.<T>get(ID);
		return this.game.physicalState.<T>get(ID);
	}

	protected final SanitizedCastSpellOrActivateAbilityAction getAbilityAction(ActivatedAbility ability)
	{
		for(SanitizedCastSpellOrActivateAbilityAction choice: this.choices.getAll(SanitizedCastSpellOrActivateAbilityAction.class))
			if(this.game.actualState.get(choice.toBePlayed).equals(ability))
				return choice;
		fail("Failed to find getAbilityAction(" + ability + ")");
		return null;
	}

	protected final SanitizedCastSpellOrActivateAbilityAction getAbilityAction(Class<? extends ActivatedAbility> ability)
	{
		return getCastSpellOrActivateAbilityAction(ability);
	}

	protected final <T extends Identified> T getActual(T identified)
	{
		return this.game.actualState.<T>get(identified.ID);
	}

	protected final SanitizedCastSpellOrActivateAbilityAction getCastSpellOrActivateAbilityAction(Class<? extends GameObject> action)
	{
		for(SanitizedCastSpellOrActivateAbilityAction choice: this.choices.getAll(SanitizedCastSpellOrActivateAbilityAction.class))
			if(action.isAssignableFrom(this.game.actualState.get(choice.toBePlayed).getClass()))
				return choice;
		fail("Failed to find SanitizedCastSpellOrActivateAbilityAction(" + action.getSimpleName() + ")");
		return null;
	}

	protected final SanitizedIdentified getChoice(Identified choice)
	{
		for(SanitizedIdentified i: this.choices.getAll(SanitizedIdentified.class))
			if(i.ID == choice.ID)
				return i;
		fail("Failed to find choice (" + choice + ")");
		return null;
	}

	/** Gets a choice whose toString contains the given string. */
	protected final java.io.Serializable getChoiceByToString(String what)
	{
		for(java.io.Serializable choice: this.choices.getAll(java.io.Serializable.class))
			if(choice.toString().contains(what))
				return choice;
		fail("Failed to find choice by name '" + what + "'.");
		return null;
	}

	protected final SanitizedCostCollection getCostCollection(String type, String mana)
	{
		SanitizedCostCollection cost = new SanitizedCostCollection(new CostCollection(type, mana));
		for(SanitizedCostCollection choice: this.choices.getAll(SanitizedCostCollection.class))
			if(choice.equals(cost))
				return choice;
		fail("Failed to find CostCollection(\"" + mana + "\"); valid choices: " + this.choices);
		return null;
	}

	protected final SanitizedDamageAssignment.Replacement getDamageAssignmentReplacement(String name)
	{
		for(SanitizedDamageAssignment.Replacement choice: this.choices.getAll(SanitizedDamageAssignment.Replacement.class))
			if(choice.effect.name.equals(name))
				return choice;
		fail("Failed to find DamageAssignment.Replacement(\"" + name + "\")");
		return null;
	}

	protected final SanitizedEvent getEvent(EventType type)
	{
		for(SanitizedEvent choice: this.choices.getAll(SanitizedEvent.class))
			if(this.<Event>get(choice.ID).type == type)
				return choice;
		fail("Failed to find Event(EventType." + type + ")");
		return null;
	}

	protected final Zone getGraveyard(int playerNum)
	{
		return this.game.physicalState.<Player>get(this.playerIDs[playerNum]).getGraveyard(this.game.physicalState);
	}

	protected final Zone getHand(int playerNum)
	{
		return this.game.physicalState.<Player>get(this.playerIDs[playerNum]).getHand(this.game.physicalState);
	}

	protected final SanitizedCastSpellOrActivateAbilityAction getIntrinsicAbilityAction(SubType land)
	{
		for(SanitizedCastSpellOrActivateAbilityAction choice: this.choices.getAll(SanitizedCastSpellOrActivateAbilityAction.class))
		{
			Identified toBePlayed = this.game.actualState.get(choice.toBePlayed);
			if(toBePlayed instanceof Game.IntrinsicManaAbility)
			{
				Game.IntrinsicManaAbility ability = (Game.IntrinsicManaAbility)toBePlayed;
				if(ability.type == land && !((GameObject)ability.getSource(this.game.actualState)).isTapped())
					return choice;
			}
		}
		fail("Failed to find getIntrinsicAbilityAction(" + land + ")");
		return null;
	}

	/** Gets an arbitrary PlayLandAction corresponding to the specified class. */
	protected final SanitizedPlayLandAction getLandAction(Class<? extends GameObject> land)
	{
		for(SanitizedPlayLandAction choice: this.choices.getAll(SanitizedPlayLandAction.class))
			if(land.isAssignableFrom(this.game.actualState.get(choice.land).getClass()))
				return choice;
		fail("Failed to find SanitizedPlayLandAction(" + land.getSimpleName() + ")");
		return null;
	}

	protected final SanitizedPlayLandAction getLandForTheTurnAction(Class<? extends GameObject> land)
	{
		for(SanitizedPlayLandAction choice: this.choices.getAll(SanitizedPlayLandAction.class))
			if(choice.isPerTurnAction && this.game.actualState.get(choice.land).getClass() == land)
				return choice;
		fail("Failed to find SanitizedPlayLandAction(" + land.getSimpleName() + ")");
		return null;
	}

	protected final Zone getLibrary(int playerNum)
	{
		return this.game.physicalState.<Player>get(this.playerIDs[playerNum]).getLibrary(this.game.physicalState);
	}

	protected final java.util.LinkedList<ManaSymbol> getMana(Color... colors)
	{
		java.util.LinkedList<ManaSymbol> ret = new java.util.LinkedList<ManaSymbol>();
		colorLoop: for(Color color: colors)
		{
			for(ManaSymbol choice: this.choices.getAll(ManaSymbol.class))
				if(!ret.contains(choice) && choice.isColor(color))
				{
					ret.add(choice);
					continue colorLoop;
				}
			fail("Failed to find Mana(" + color.toString() + ")");
		}
		return ret;
	}

	protected final java.util.LinkedList<ManaSymbol> getMana(ManaSymbol.ManaType... types)
	{
		java.util.LinkedList<ManaSymbol> ret = new java.util.LinkedList<ManaSymbol>();
		typeLoop: for(ManaSymbol.ManaType type: types)
		{
			if(type != ManaSymbol.ManaType.COLORLESS)
			{
				for(ManaSymbol choice: this.choices.getAll(ManaSymbol.class))
					if(!ret.contains(choice) && choice.isColor(type.getColor()))
					{
						ret.add(choice);
						continue typeLoop;
					}
			}
			else
			{
				for(ManaSymbol choice: this.choices.getAll(ManaSymbol.class))
					if(!ret.contains(choice) && choice.colors.isEmpty())
					{
						ret.add(choice);
						continue typeLoop;
					}
			}
		}
		return ret;
	}

	protected final ManaPool getManaPool(String compare)
	{
		for(ManaPool pool: this.choices.getAll(ManaPool.class))
			if(pool.toString().equals(compare))
				return pool;
		fail("Failed to find ManaPool(" + compare + ")");
		return null;
	}

	protected final SanitizedMode getMode(EventType type)
	{
		for(SanitizedMode mode: this.choices.getAll(SanitizedMode.class))
			for(EventFactory effect: this.game.actualState.<GameObject>get(mode.sourceID).getModes().get(mode.index).effects)
				if(effect.type == type)
					return mode;
		fail("Failed to find Mode(EventType." + type + ")");
		return null;
	}

	protected final SanitizedMode getModeByName(String name)
	{
		for(SanitizedMode mode: this.choices.getAll(SanitizedMode.class))
			if(mode.toString().equals(name))
				return mode;
		fail("Failed to find Mode(\"" + name + "\")");
		return null;
	}

	protected final SanitizedPlayer getPlayer(int playerNumber)
	{
		return (SanitizedPlayer)getChoice(this.player(playerNumber));
	}

	@SuppressWarnings("unchecked")
	private final <T> T getResponse()
	{
		this.responseObject = null;
		this.choicesReady = true;

		try
		{
			synchronized(this)
			{
				this.notify();
				while(this.choicesReady)
					this.wait();
			}
		}
		// This can happen when the test finishes before the engine does throw
		// new JUnitStopTest();
		catch(InterruptedException e)
		{
			throw new Game.InterruptedGameException();
		}

		return (T)this.responseObject;
	}

	protected final SanitizedCastSpellOrActivateAbilityAction getSpellAction(Class<? extends Card> spell)
	{
		return this.getCastSpellOrActivateAbilityAction(spell);
	}

	protected final SanitizedTarget getTarget(Class<?> target)
	{
		for(SanitizedTarget choice: this.choices.getAll(SanitizedTarget.class))
			if(this.game.actualState.get(choice.targetID).getClass() == target)
				return choice;
		fail("Failed to find Target(" + target.getSimpleName() + ")");
		return null;
	}

	protected final SanitizedTarget getTarget(Identified target)
	{
		for(SanitizedTarget choice: this.choices.getAll(SanitizedTarget.class))
			if(choice.targetID == target.ID)
				return choice;
		fail("Failed to find Target(" + target.getName() + ")");
		return null;
	}

	protected final SanitizedNonStaticAbility getTriggeredAbility(EventType type)
	{
		for(SanitizedNonStaticAbility choice: this.choices.getAll(SanitizedNonStaticAbility.class))
		{
			NonStaticAbility ability = this.game.actualState.<NonStaticAbility>get(choice.ID);

			if(ability instanceof TriggeredAbility)
				for(Mode mode: ((TriggeredAbility)ability).getModes())
					for(EventFactory effect: mode.effects)
						if(effect.type == type)
							return choice;
		}
		fail("Unable to find TriggeredAbility with effect " + type.toString() + ".");
		return null;
	}

	/**
	 * Responds with nothing until the given phase.
	 * 
	 * @param phase The phase to wait for.
	 * @return The number of times this responded before the phase was found.
	 */
	protected final int goToPhase(Phase.PhaseType phase)
	{
		int i = 0;
		while(this.game.physicalState.currentPhase().type != phase)
		{
			++i;
			respondWith();
		}
		return i;
	}

	/**
	 * Responds with nothing until the given step.
	 * 
	 * @param step The step to wait for.
	 * @return The number of times this responded before the step was found.
	 */
	protected final int goToStep(Step.StepType step)
	{
		int i = 0;
		while(this.game.physicalState.currentStep().type != step)
		{
			++i;
			respondWith();
		}
		return i;
	}

	protected final void keep()
	{
		if(!PlayerInterface.ChoiceType.EVENT.equals(this.choiceType))
			fail("Must be choosing whether to mulligan in order to keep");
		respondWith();
	}

	/**
	 * Checks that the choice type is EVENT, and chooses the first choice.
	 * 
	 * This is somewhat misnamed since if there is a mulligan option (i.e. Serum
	 * Powder) that may be chosen instead.
	 */
	protected final void mulligan()
	{
		if(!PlayerInterface.ChoiceType.EVENT.equals(this.choiceType))
			fail("Must be choosing whether to mulligan in order to mulligan");
		respondWith((java.io.Serializable)this.choices.iterator().next());
	}

	protected final void pass()
	{
		if(PlayerInterface.ChoiceType.NORMAL_ACTIONS != this.choiceType)
			fail("Must be able to perform normal actions to pass");
		respondWith();
	}

	/**
	 * @param i The player number
	 * @return The player who started the game with the given player number
	 */
	protected final Player player(int i)
	{
		return this.game.physicalState.get(this.playerIDs[i]);
	}

	protected final SanitizedGameObject pullChoice(Class<? extends GameObject> clazz)
	{
		for(SanitizedGameObject clean: this.choices.getAll(SanitizedGameObject.class))
		{
			GameObject dirty = this.game.actualState.<GameObject>get(clean.ID);
			if(clazz.isAssignableFrom(dirty.getClass()))
			{
				this.choices.remove(clean);
				return clean;
			}
		}
		fail("Failed to find choice (" + clazz.getSimpleName() + ")");
		return null;
	}

	protected final java.io.Serializable pullChoice(java.io.Serializable toPull)
	{
		for(java.io.Serializable o: this.choices.getAll(java.io.Serializable.class))
		{
			if(o.equals(toPull))
			{
				this.choices.remove(o);
				return o;
			}
		}
		fail("Failed to find choice (" + toPull + ")");
		return null;
	}

	/**
	 * Convenience method for ordered choices where you don't care about the
	 * order. This takes all available choices, orders them arbitrarily in an
	 * array, and responds with that array.
	 */
	protected final void respondArbitrarily()
	{
		this.respondWith(this.choices.toArray(new java.io.Serializable[0]));
	}

	protected final void respondWith(java.io.Serializable... object)
	{
		if(null == this.game)
			fail("Can't respond without a game started");
		if(this.engineFinished)
			fail("Can't respond after a game is completed");

		Object response;
		if(object == null || object.length == 0)
			response = null;
		else if(object.length == 1)
			response = object[0];
		else
		{
			java.util.List<Object> responseList = new java.util.LinkedList<Object>();
			for(Object o: object)
				responseList.add(o);
			response = responseList;
		}

		this.responseObject = response;
		this.choicesReady = false;
		try
		{
			synchronized(this)
			{
				this.notify();
				while(!this.choicesReady && !this.engineFinished)
					this.wait();
			}
		}
		catch(InterruptedException e)
		{
			if(null == this.engineThrowable)
				fail("engineThrowable not populated before interrupting test thread");
			else if(this.engineThrowable instanceof RuntimeException)
				throw (RuntimeException)this.engineThrowable;
			else if(this.engineThrowable instanceof Error)
				throw (Error)this.engineThrowable;
			else
				fail("engineThrowable is not a RuntimeException or Error");
		}
	}

	/**
	 * Start the engine thread.
	 * 
	 * The current test thread will wait on the engine thread to start before
	 * returning, so any assertions made after this returns will be on the state
	 * of the game as the first choice needs to be made.
	 * 
	 * @param type The type of the game to start
	 */
	protected final void startGame(GameType type)
	{
		if(0 == this.playerInterfaces.size())
			fail("Can't create a 0-player game; must add at least one deck or interface first");

		final Thread testThread = Thread.currentThread();
		this.engine = new Thread("Engine")
		{
			@Override
			public void run()
			{
				try
				{
					synchronized(JUnitTest.this)
					{
						JUnitTest.this.engineFinished = false;
					}
					JUnitTest.this.winner = JUnitTest.this.game.run();
					synchronized(JUnitTest.this)
					{
						JUnitTest.this.engineFinished = true;
						JUnitTest.this.notify();
					}
				}
				catch(Game.InterruptedGameException e)
				{
					// This will happen when the test finishes before the engine
					// does; this thread should exit cleanly
				}
				catch(Throwable t)
				{
					// This is pretty heinous, but it allows testing to continue
					// if anything happens to the game
					JUnitTest.this.engineThrowable = t;
					testThread.interrupt();
				}
			}
		};

		// Tell CardLoader to load all our cards :)
		org.rnd.jmagic.CardLoader.addPackages("org.rnd.jmagic.cards");

		this.game = new Game(type);

		int numInterfaces = this.playerInterfaces.size();
		this.playerIDs = new int[numInterfaces];
		for(int i = 0; i < numInterfaces; ++i)
		{
			Player player = this.game.addInterface(this.playerInterfaces.get(i));
			assertNotNull("Deck for player " + i + " was rejected", player);
		}

		try
		{
			synchronized(this)
			{
				// Wait for the game to present the first choice
				this.engine.start();
				while(!this.choicesReady && !this.engineFinished)
					this.wait();
			}
		}
		catch(InterruptedException e)
		{
			if(null == this.engineThrowable)
				fail("Wait for first choice interrupted");
			else if(this.engineThrowable instanceof RuntimeException)
				throw (RuntimeException)this.engineThrowable;
			else if(this.engineThrowable instanceof Error)
				throw (Error)this.engineThrowable;
			else
				fail("engineThrowable is not a RuntimeException or Error");
		}
	}
}

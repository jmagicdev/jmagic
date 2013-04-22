package org.rnd.jmagic.engine.gameTypes;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

/**
 * 901. Planechase
 * 
 * 901.1. In the Planechase variant, plane cards add additional abilities and
 * randomness to the game. At any given time, one plane card will be face up and
 * its abilities will affect the game. The Planechase variant uses all the
 * normal rules for a _Magic_ game, with the following additions.
 * 
 * 901.2. A Planechase game may be a two-player game or a multiplayer game. The
 * default multiplayer setup is the Free-for-All variant with the attack
 * multiple players option and without the limited range of influence option.
 * See rule 806, "Free-for-All Variant."
 * 
 * 901.3. In addition to the normal game materials, each player needs a planar
 * deck of at least ten plane cards and the game needs one planar die. Each card
 * in a planar deck must have a different English name. (See rule 309,
 * "Planes.")
 * 
 * 901.3a A planar die is a six-sided die. One face has the Planeswalker symbol
 * {PW}. One face has the chaos symbol {C}. The other faces are blank.
 * 
 * 901.4. At the start of the game, each player shuffles his or her planar deck
 * so that the cards are in a random order. Each deck is placed face down next
 * to its owner's library. All plane cards remain in the command zone throughout
 * the game, both while they're part of a planar deck and while they're face up.
 * 
 * 901.5. Once all players have kept their opening hands and used the abilities
 * of cards that allow them to start the game with those cards on the
 * battlefield, the starting player moves the top card of his or her planar deck
 * off that planar deck and turns it face up. (See rule 103.6.) No abilities of
 * that card trigger as a result.
 * 
 * 901.6. The owner of a plane card is the player who started the game with it
 * in his or her planar deck. The controller of a face-up plane card is the
 * player designated as the planar controller. Normally, the planar controller
 * is whoever the active player is. However, if the current planar controller
 * would leave the game, instead the next player in turn order that wouldn't
 * leave the game becomes the planar controller, then the old planar controller
 * leaves the game. The new planar controller retains that designation until he
 * or she leaves the game or a different player becomes the active player,
 * whichever comes first.
 * 
 * 901.7. Any abilities of a face-up plane card in the command zone function
 * from that zone. The card's static abilities affect the game, its triggered
 * abilities may trigger, and its activated abilities may be activated.
 * 
 * 901.7a Each plane card is treated as if its text box included "When you roll
 * {PW}, put this card on the bottom of its owner's planar deck face down, then
 * move the top card of your planar deck off your planar deck and turn it face
 * up." This is called the "planeswalking ability." A face-up plane card that's
 * turned face down becomes a new object.
 * 
 * 901.8. Any time the active player has priority and the stack is empty, but
 * only during a main phase of his or her turn, that player may roll the planar
 * die. Taking this action costs a player an amount of mana equal to the number
 * of times he or she has previously taken this action on that turn. This is a
 * special action and doesn't use the stack. (See rule 115.2f.)
 * 
 * 901.8a If the die roll is a blank face, nothing happens. The active player
 * gets priority.
 * 
 * 901.8b If the die roll is the chaos symbol {C}, any ability of the plane that
 * starts "When you roll {C}" triggers and is put on the stack. The active
 * player gets priority.
 * 
 * 901.8c If the die roll is the Planeswalker symbol {PW}, the plane's
 * "planeswalking ability" triggers and is put on the stack. The active player
 * gets priority.
 * 
 * 901.9. When a player leaves the game, all objects owned by that player leave
 * the game. (See rule 800.4a.) If that includes the face-up plane card, the
 * planar controller turns the top card of his or her planar deck face up. This
 * is not a state-based action. It happens as soon as the player leaves the
 * game.
 * 
 * 901.9a If a plane leaves the game while a "planeswalking ability" for which
 * it was the source is on the stack, that ability ceases to exist.
 * 
 * 901.10. After the game has started, if a player moves the top card of his or
 * her planar deck off that planar deck and turns it face up, that player has
 * "planeswalked." Continuous effects with durations that last until a player
 * planeswalks end. Abilities that trigger when a player planeswalks trigger.
 * See rule 701.21.
 * 
 * 901.10a A player may planeswalk as the result of the "planeswalking ability"
 * (see rule 309.6) or because the owner of the face-up plane card leaves the
 * game (see rule 901.9).
 * 
 * 901.10b The plane card that's turned face up is the plane the player
 * planeswalks to. The plane card that's turned face down, or that leaves the
 * game, is the plane the player planeswalks away from.
 * 
 * 901.11. A Two-Headed Giant Planechase game uses all the rules for the
 * Two-Headed Giant multiplayer variant and all the rules for the Planechase
 * casual variant, with the following additions.
 * 
 * 901.11a Each player has his or her own planar deck.
 * 
 * 901.11b The planar controller is normally the primary player of the active
 * team. However, if the current planar controller's team would leave the game,
 * instead the primary player of the next team in turn order that wouldn't leave
 * the game becomes the planar controller, then the old planar controller's team
 * leaves the game. The new planar controller retains that designation until he
 * or she leaves the game or a different team becomes the active team, whichever
 * comes first.
 * 
 * 901.11c Even though the face-up plane is controlled by just one player, any
 * ability of that plane that refers to "you" applies to both members of the
 * planar controller's team.
 * 
 * 901.11d Since each member of the active team is an active player, each of
 * them may roll the planar die. Each player's cost to roll the planar die is
 * based on the number of times that particular player has already rolled the
 * planar die that turn.
 * 
 * 901.12. In multiplayer formats other than Grand Melee, plane cards are exempt
 * from the limited range of influence option. Their abilities, and the effects
 * of those abilities, affect all applicable objects and players in the game.
 * (See rule 801, "Limited Range of Influence Option.")
 * 
 * 901.13. In Grand Melee Planechase games, multiple plane cards may be face up
 * at the same time.
 * 
 * 901.13a Before the first turn of the game of the game, each player who will
 * start the game with a turn marker moves the top card of his or her planar
 * deck off that planar deck and turns it face up. Each of them is a planar
 * controller.
 * 
 * 901.13b If a player would leave the game and that player leaving the game
 * would reduce the number of turn markers in the game, that player first ceases
 * to be a planar controller (but no other player becomes a planar controller),
 * then that player leaves the game. The face-up plane card that player
 * controlled is put on the bottom of its owner's planar deck. No player is
 * considered to have planeswalked.
 * 
 * 901.14. Single Planar Deck Option
 * 
 * 901.14a As an alternative option, a Planechase game may be played with just a
 * single communal planar deck. In that case, the number of cards in the planar
 * deck must be at least forty or at least ten times the number of players in
 * the game, whichever is smaller. Each card in the planar deck must have a
 * different English name.
 * 
 * 901.14b In a Planechase game using the single planar deck option, the planar
 * controller is considered to be the owner of all the plane cards.
 * 
 * 901.14c If any rule or ability refers to a player's planar deck, the communal
 * planar deck is used.
 * */
@GameType.Ignore
@Name("Planechase (single-deck)")
@Description("The command zone contains the planar deck of the host and planes apply to all players")
public class Planechase extends GameType.SimpleGameTypeRule
{
	private final static java.util.Map<Game, SetGenerator> topPlanarCardGenerators = new java.util.HashMap<Game, SetGenerator>();
	private final static SetGenerator topPlanarCard = new SetGenerator()
	{
		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			SetGenerator topPlane = topPlanarCardGenerators.get(state.game);
			if(null == topPlane)
				return Empty.set;
			return topPlane.evaluate(state, thisObject);
		}
	};

	private final static java.util.Map<Game, SetGenerator> planarDeckGenerators = new java.util.HashMap<Game, SetGenerator>();
	private final static SetGenerator planarDeck = new SetGenerator()
	{
		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			SetGenerator topPlane = planarDeckGenerators.get(state.game);
			if(null == topPlane)
				return Empty.set;
			return topPlane.evaluate(state, thisObject);
		}
	};

	public static final SetGenerator triggeredAbilityCanTrigger = Intersect.instance(AbilitySource.instance(This.instance()), Planechase.topPlaneCard());
	public static final SetGenerator staticAbilityCanApply = Intersect.instance(This.instance(), Planechase.topPlaneCard());

	public static SetGenerator topPlaneCard()
	{
		return topPlanarCard;
	}

	public static SetGenerator planarDeck()
	{
		return planarDeck;
	}

	public static org.rnd.jmagic.engine.EventPattern wheneverYouRollChaos()
	{
		org.rnd.jmagic.engine.patterns.SimpleEventPattern ret = new org.rnd.jmagic.engine.patterns.SimpleEventPattern(ROLL_PLANAR_DIE);
		ret.put(EventType.Parameter.PLAYER, You.instance());
		ret.withResult(Identity.instance(PlanarDie.CHAOS));
		return ret;
	}

	private final SetGenerator topPlane;
	private final SetGenerator planarDeckGenerator;
	private final java.util.Set<Integer> planeIDs;

	public Planechase()
	{
		this.planeIDs = new java.util.HashSet<Integer>();
		this.topPlane = new SetGenerator()
		{
			@Override
			public Set evaluate(GameState state, Identified thisObject)
			{
				Zone commandZone = state.commandZone();
				if(null != commandZone)
					for(GameObject object: commandZone.objects)
						if(Planechase.this.planeIDs.contains(object.ID))
							return new Set(object);
				return Empty.set;
			}
		};
		this.planarDeckGenerator = new SetGenerator()
		{
			@Override
			public Set evaluate(GameState state, Identified thisObject)
			{
				Set ret = null;
				Zone commandZone = state.commandZone();
				if(null != commandZone)
					for(GameObject object: commandZone.objects)
						if(Planechase.this.planeIDs.contains(object.ID))
							// This code purposefully ignores the first plane
							if(ret == null)
								ret = new Set();
							else
								ret.add(object);
				return (ret == null ? Empty.set : ret);
			}
		};
	}

	@Override
	public void modifyGameState(GameState physicalState)
	{
		Zone commandZone = physicalState.commandZone();

		topPlanarCardGenerators.put(physicalState.game, this.topPlane);
		planarDeckGenerators.put(physicalState.game, this.planarDeckGenerator);
		physicalState.ensureTracker(new RollTracker());

		int firstPlayerID = physicalState.players.iterator().next().ID;

		Class<?>[] parameterClasses = new Class<?>[] {GameState.class};
		Object[] parameterObjects = new Object[] {physicalState};
		java.util.List<Class<? extends Card>> planeClasses = new java.util.LinkedList<Class<? extends Card>>(org.rnd.jmagic.CardLoader.getCards(java.util.Collections.singleton(Expansion.PLANECHASE)));
		java.util.Collections.shuffle(planeClasses);

		// ***DEBUGGING***
		planeClasses.clear();
		planeClasses.add(org.rnd.jmagic.cards.Naya.class);
		// ***DEBUGGING***

		for(Class<? extends Card> card: planeClasses)
		{
			Types types = card.getAnnotation(Types.class);
			if(types == null || java.util.Arrays.binarySearch(types.value(), Type.PLANE) < 0)
			{
				continue;
			}
			GameObject plane = org.rnd.util.Constructor.construct(card, parameterClasses, parameterObjects);
			plane.setExpansionSymbol(org.rnd.jmagic.CardLoader.getPrintings(card).firstKey());
			plane.controllerID = firstPlayerID;
			commandZone.addToTop(plane);
			this.planeIDs.add(plane.ID);
		}

		{
			ContinuousEffect.Part planarController = new ContinuousEffect.Part(ROTATING_CONTROL);

			EventFactory applyPlanarRules = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "The controller of a face-up plane card is the player designated as the planar controller. Normally, the planar controller is whoever the active player is.");
			applyPlanarRules.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
			applyPlanarRules.parameters.put(EventType.Parameter.EFFECT, Identity.instance(planarController));
			applyPlanarRules.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));
			applyPlanarRules.createEvent(physicalState.game, null).perform(null, true);
		}

		{
			ContinuousEffect.Part createRollDieAction = new ContinuousEffect.Part(ContinuousEffectType.SPECIAL_ACTION);
			createRollDieAction.parameters.put(ContinuousEffectType.Parameter.ACTION, Identity.instance(new RollPlanarDieActionFactory(this.topPlane)));
			createRollDieAction.parameters.put(ContinuousEffectType.Parameter.PLAYER, PlayerWithPriority.instance());

			EventFactory applyPlanarRules = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "Any time the active player has priority and the stack is empty, but only during a main phase of his or her turn, that player may roll the planar die. Taking this action costs a player an amount of mana equal to the number of times he or she has previously taken this action on that turn. This is a special action and doesn't use the stack.");
			applyPlanarRules.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
			applyPlanarRules.parameters.put(EventType.Parameter.EFFECT, Identity.instance(createRollDieAction));
			applyPlanarRules.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));
			applyPlanarRules.createEvent(physicalState.game, null).perform(null, true);
		}

		{
			// TODO: delay this until the game starts somehow
			EventFactory applyPlanarRules = new EventFactory(EventType.REVEAL, "The top card of the planar deck is face-up.");
			// The only way to get the reveal to have the right duration is to
			// make the plane the cause.
			applyPlanarRules.parameters.put(EventType.Parameter.CAUSE, topPlaneCard());
			applyPlanarRules.parameters.put(EventType.Parameter.OBJECT, topPlaneCard());
			applyPlanarRules.createEvent(physicalState.game, null).perform(null, true);
		}

		{
			ContinuousEffect.Part addPlaneshiftAbility = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_OBJECT);
			addPlaneshiftAbility.parameters.put(ContinuousEffectType.Parameter.OBJECT, HasType.instance(Type.PLANE));
			addPlaneshiftAbility.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(new org.rnd.jmagic.engine.SimpleAbilityFactory(IntrinsicPlaneshift.class)));

			EventFactory applyPlanarRules = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "Each plane card is treated as if its text box included \"When you roll {P}, put this card on the bottom of its owner's planar deck face down, then move the top card of your planar deck off your planar deck and turn it face up.\"");
			applyPlanarRules.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
			applyPlanarRules.parameters.put(EventType.Parameter.EFFECT, Identity.instance(addPlaneshiftAbility));
			applyPlanarRules.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));
			applyPlanarRules.createEvent(physicalState.game, null).perform(null, true);
		}
	}

	public enum PlanarDie
	{
		EMPTY(4), CHAOS(1), PLANESHIFT(1);

		private final int odds;
		private static java.util.Random random = new java.util.Random();

		PlanarDie(int odds)
		{
			this.odds = odds;
		}

		public static PlanarDie rollDie()
		{
			int max = 0;
			for(PlanarDie value: PlanarDie.values())
				max += value.odds;
			double roll = random.nextInt(max);
			for(PlanarDie value: PlanarDie.values())
				if((roll -= value.odds) < 0)
					return value;
			return null;
		}
	}

	public static class RollTracker extends Tracker<Integer>
	{
		private int numRolls = 0;

		@Override
		protected Integer getValueInternal()
		{
			return this.numRolls;
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			return (event.type == ROLL_PLANAR_DIE);
		}

		@Override
		protected void reset()
		{
			this.numRolls = 0;
		}

		@Override
		protected void update(GameState state, Event event)
		{
			++this.numRolls;
		}
	}

	public static final ContinuousEffectType ROTATING_CONTROL = new ContinuousEffectType("ROTATING_CONTROL")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public void apply(GameState state, ContinuousEffect effect, java.util.Map<Parameter, Set> parameters)
		{
			parameters.put(Parameter.OBJECT, Intersect.instance(HasType.instance(Type.PLANE), InZone.instance(CommandZone.instance())).evaluate(state, effect.getSourceObject()));
			parameters.put(Parameter.PLAYER, PlanarController.instance().evaluate(state, effect.getSourceObject()));
			ContinuousEffectType.CHANGE_CONTROL.apply(state, effect, parameters);
		}

		@Override
		public Layer layer()
		{
			return Layer.CONTROL_CHANGE;
		}
	};

	/**
	 * @eparam CAUSE: the ability
	 * @eparam EFFECT: the continuous effect part with the attacking restriction
	 * @eparam RESULT: empty
	 */
	public static final EventType CREATE_FCE_UNTIL_A_PLAYER_PLANESWALKS = new EventType("CREATE_FCE_UNTIL_A_PLAYER_PLANESWALKS")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Map<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>(parameters);

			class LazySetGeneratorWrapper extends SetGenerator
			{
				public SetGenerator wrapped = null;

				@Override
				public Set evaluate(GameState state, Identified thisObject)
				{
					if(this.wrapped == null)
						return Empty.set;
					return this.wrapped.evaluate(state, thisObject);
				}
			}

			LazySetGeneratorWrapper expires = new LazySetGeneratorWrapper();

			newParameters.put(EventType.Parameter.EXPIRES, new Set(expires));
			Event fceEvent = createEvent(game, event.getName(), EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, newParameters);
			if(fceEvent.perform(event, false))
			{
				FloatingContinuousEffect effect = fceEvent.getResult().getOne(FloatingContinuousEffect.class);
				expires.wrapped = Planechase.UntilNextPlaneswalk.instance(effect);
			}

			event.setResult(Empty.set);
			return true;
		}
	};

	/**
	 * @eparam PLAYER: the player rolling the die
	 * @eparam RESULT: the result of the die roll
	 */
	public static final EventType ROLL_PLANAR_DIE = new EventType("ROLL_PLANAR_DIE")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			PlanarDie rollDie = PlanarDie.rollDie();
			event.setResult(Identity.instance(rollDie));
			return true;
		}
	};

	/**
	 * @eparam PLAYER: the player planeswalking
	 * @eparam TO: the new plane
	 * @eparam RESULT: the previous plane
	 */
	public static final EventType PLANESWALK = new EventType("PLANESWALK")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set commandZone = new Set(game.actualState.commandZone());
			Set previousPlane = topPlanarCard.evaluate(game, null);

			java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
			moveParameters.put(Parameter.CAUSE, new Set(game));
			moveParameters.put(Parameter.TO, commandZone);
			moveParameters.put(Parameter.INDEX, NEGATIVE_ONE);
			moveParameters.put(Parameter.OBJECT, previousPlane);
			Event moveEvent = createEvent(game, "Put this card on the bottom of its owner's planar deck face down.", EventType.MOVE_OBJECTS, moveParameters);
			moveEvent.perform(event, true);

			// The only way to get the reveal to have the right duration is to
			// make the plane the cause.
			Set newPlane = topPlanarCard.evaluate(game, null);
			java.util.Map<Parameter, Set> revealParameters = new java.util.HashMap<Parameter, Set>();
			revealParameters.put(Parameter.CAUSE, newPlane);
			revealParameters.put(Parameter.OBJECT, newPlane);
			createEvent(game, "Move the top card of your planar deck off your planar deck and turn it face up.", EventType.REVEAL, revealParameters).perform(event, true);

			event.setResult(Identity.instance(previousPlane));
			return true;
		}
	};

	public static final class UntilNextPlaneswalk extends SetGenerator
	{
		public static UntilNextPlaneswalk instance(FloatingContinuousEffect effect)
		{
			return new UntilNextPlaneswalk(effect);
		}

		private final int effectID;

		private UntilNextPlaneswalk(FloatingContinuousEffect effect)
		{
			this.effectID = effect.ID;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			if(state.getTracker(UntilAPlayerPlaneswalks.class).getValue(state).contains(this.effectID))
				return NonEmpty.set;
			return Empty.set;
		}
	}

	/**
	 * Contains the IDs of all the ContinuousEffects that existed before the
	 * last player planeswalked, so that other effects can see if they are on
	 * the list.
	 */
	public static final class UntilAPlayerPlaneswalks extends Tracker<java.util.Collection<Integer>>
	{
		private java.util.List<Integer> values = new java.util.LinkedList<Integer>();
		private java.util.List<Integer> unmodifiable = java.util.Collections.unmodifiableList(this.values);

		@Override
		public UntilAPlayerPlaneswalks clone()
		{
			UntilAPlayerPlaneswalks ret = (UntilAPlayerPlaneswalks)super.clone();
			ret.values = new java.util.LinkedList<Integer>(this.values);
			ret.unmodifiable = java.util.Collections.unmodifiableList(ret.values);
			return ret;
		}

		@Override
		protected java.util.Collection<Integer> getValueInternal()
		{
			return this.unmodifiable;
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			return event.type == PLANESWALK;
		}

		@Override
		protected void reset()
		{
			this.values.clear();
		}

		@Override
		protected void update(GameState state, Event event)
		{
			for(ContinuousEffect effect: state.getAll(ContinuousEffect.class))
				if(!this.values.contains(effect.ID))
					this.values.add(effect.ID);
		}

		public boolean contains(ContinuousEffect effect)
		{
			return this.values.contains(effect.ID);
		}
	}

	public static class RollPlanarDieActionFactory extends SpecialActionFactory
	{
		private final SetGenerator topPlane;

		public RollPlanarDieActionFactory(SetGenerator topPlane)
		{
			this.topPlane = topPlane;
		}

		@Override
		public java.util.Set<PlayerAction> getActions(GameState state, GameObject source, Player actor)
		{
			java.util.Set<PlayerAction> ret = new java.util.HashSet<PlayerAction>();

			if(state.stack().objects.isEmpty() && state.playerWithPriorityID == state.currentTurn().getOwner(state).ID && (state.currentPhase().type == Phase.PhaseType.PRECOMBAT_MAIN || state.currentPhase().type == Phase.PhaseType.POSTCOMBAT_MAIN))
			{
				int planeID = this.topPlane.evaluate(state, source).getOne(GameObject.class).ID;
				ret.add(new RollPlanarDieAction(state.game, actor, state.getTracker(RollTracker.class).getValue(state), planeID));
			}

			return ret;
		}
	}

	public static class RollPlanarDieAction extends PlayerAction
	{
		private final int cost;
		private final int planeID;

		public RollPlanarDieAction(Game game, Player rolling, int cost, int planeID)
		{
			super(game, "Roll the planar die", rolling, planeID);

			this.cost = cost;
			this.planeID = planeID;
		}

		@Override
		public int getSourceObjectID()
		{
			return this.planeID;
		}

		@Override
		public boolean perform()
		{
			Player acting = this.actor();
			if(this.cost > 0)
			{
				acting.mayActivateManaAbilities();

				EventFactory cost = new EventFactory(EventType.PAY_MANA, "Pay (1) for every time you have rolled the planar die this turn.");
				cost.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
				cost.parameters.put(EventType.Parameter.COST, Identity.instance(new ManaPool("1")));
				cost.parameters.put(EventType.Parameter.PLAYER, Identity.instance(acting));
				cost.parameters.put(EventType.Parameter.NUMBER, org.rnd.jmagic.Convenience.numberGenerator(this.cost));

				if(!cost.createEvent(this.game, null).perform(null, true))
					return false;
			}

			EventFactory factory = new EventFactory(ROLL_PLANAR_DIE, "Roll the planar die.");
			factory.parameters.put(EventType.Parameter.PLAYER, Identity.instance(acting));
			return factory.createEvent(this.game, null).perform(null, true);
		}

		@Override
		public PlayerInterface.ReversionParameters getReversionReason()
		{
			Player player = this.game.physicalState.get(this.actorID);
			return new PlayerInterface.ReversionParameters("RollPlanarDieAction", player.getName() + " failed to roll the planar die.");
		}
	}

	public static final class IntrinsicPlaneshift extends EventTriggeredAbility
	{
		public IntrinsicPlaneshift(GameState state)
		{
			super(state, "When you roll (#), put this card on the bottom of its owner's planar deck face down, then move the top card of your planar deck off your planar deck and turn it face up.");

			org.rnd.jmagic.engine.patterns.SimpleEventPattern pattern = new org.rnd.jmagic.engine.patterns.SimpleEventPattern(ROLL_PLANAR_DIE);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.withResult(Identity.instance(PlanarDie.PLANESHIFT));
			this.addPattern(pattern);

			EventFactory planeswalk = new EventFactory(PLANESWALK, "You planeswalk.");
			planeswalk.parameters.put(EventType.Parameter.PLAYER, You.instance());
			planeswalk.parameters.put(EventType.Parameter.TO, TopMost.instance(CommandZone.instance(), numberGenerator(1), planarDeck()));
			this.addEffect(planeswalk);

			this.canTrigger = Intersect.instance(AbilitySource.instance(This.instance()), topPlaneCard());
		}
	}

	private static class PlanarController extends SetGenerator
	{
		private static final PlanarController _instance = new PlanarController();

		private static PlanarController instance()
		{
			return _instance;
		}

		private PlanarController()
		{
			// Singleton Constructor
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Turn currentTurn = state.currentTurn();
			if(currentTurn == null)
				return Empty.set;

			Player controller = currentTurn.getOwner(state);
			if(null == controller)
			{
				// TODO: Normally, the planar controller is whoever the active
				// player is. However, if the current planar controller would
				// leave the game, instead the next player in turn order that
				// wouldn't leave the game becomes the planar controller, then
				// the old planar controller leaves the game. The new planar
				// controller retains that designation until he or she leaves
				// the game or a different player becomes the active player,
				// whichever comes first.

				// For now, just return an empty set
				return Empty.set;
			}
			return new Set(controller);
		}
	}
}

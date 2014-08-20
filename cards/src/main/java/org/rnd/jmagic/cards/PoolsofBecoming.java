package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pools of Becoming")
@Types({Type.PLANE})
@SubTypes({SubType.BOLASS_MEDITATION_REALM})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class PoolsofBecoming extends Card
{
	public static final class Recycle extends EventTriggeredAbility
	{
		/**
		 * @eparam CAUSE: the ability
		 * @eparam PLAYER: you
		 * @eparam RESULT: empty
		 */
		public static final EventType POOLS_OF_BECOMING_RECYCLE_EVENT = new EventType("POOLS_OF_BECOMING_RECYCLE_EVENT")
		{
			@Override
			public Parameter affects()
			{
				return null;
			}

			@Override
			public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
			{
				Set cause = parameters.get(EventType.Parameter.CAUSE);
				Player player = parameters.get(EventType.Parameter.PLAYER).getOne(Player.class);
				Zone targetsHand = player.getHand(game.actualState);
				Set targetsLibrary = new Set(player.getLibrary(game.actualState));

				java.util.Map<EventType.Parameter, Set> moveParams = new java.util.HashMap<EventType.Parameter, Set>();
				moveParams.put(EventType.Parameter.CAUSE, cause);
				moveParams.put(EventType.Parameter.TO, targetsLibrary);
				moveParams.put(EventType.Parameter.INDEX, NEGATIVE_ONE);
				moveParams.put(EventType.Parameter.OBJECT, Set.fromCollection(targetsHand.objects));
				Event libraryEvent = createEvent(game, "Put the cards from your hand on the bottom of your library in any order.", EventType.MOVE_OBJECTS, moveParams);
				boolean ret = libraryEvent.perform(event, true);

				java.util.Map<EventType.Parameter, Set> drawParams = new java.util.HashMap<EventType.Parameter, Set>();
				drawParams.put(EventType.Parameter.CAUSE, cause);
				drawParams.put(EventType.Parameter.PLAYER, new Set(player));
				drawParams.put(EventType.Parameter.NUMBER, new Set(libraryEvent.getResult().size()));
				Event drawEvent = createEvent(game, "Then draw that many cards.", EventType.DRAW_CARDS, drawParams);
				ret = drawEvent.perform(event, true) && ret;

				event.setResult(Empty.set);
				return ret;
			}
		};

		public Recycle(GameState state)
		{
			super(state, "At the beginning of your end step, put the cards in your hand on the bottom of your library in any order, then draw that many cards.");

			this.addPattern(atTheBeginningOfYourEndStep());

			EventFactory factory = new EventFactory(POOLS_OF_BECOMING_RECYCLE_EVENT, "Put the cards in your hand on the bottom of your library in any order, then draw that many cards.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(factory);

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public static final class SuperChaos extends EventTriggeredAbility
	{
		/**
		 * @eparam CAUSE: the ability
		 * @eparam EVENT: the chaos roll that triggered this
		 * @eparam OBJECT: the cards to trigger chaos on
		 * @eparam RESULT: empty
		 */
		public static final EventType POOLS_OF_BECOMING_CHAOS_EVENT = new EventType("POOLS_OF_BECOMING_CHAOS_EVENT")
		{
			@Override
			public Parameter affects()
			{
				return null;
			}

			@Override
			public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
			{
				Set objects = parameters.get(Parameter.OBJECT);
				Set cause = parameters.get(Parameter.CAUSE);

				java.util.Map<Parameter, Set> revealParameters = new java.util.HashMap<Parameter, Set>();
				revealParameters.put(Parameter.CAUSE, cause);
				revealParameters.put(Parameter.OBJECT, objects);
				Event revealEvent = createEvent(game, "Reveal the top three cards of your planar deck.", EventType.REVEAL, revealParameters);
				revealEvent.perform(event, true);

				Event triggerEvent = parameters.get(Parameter.EVENT).getOne(Event.class);

				for(GameObject object: objects.getAll(GameObject.class))
				{
					object = object.getActual();
					for(EventTriggeredAbility ability: Set.fromCollection(object.getNonStaticAbilities()).getAll(EventTriggeredAbility.class))
					{
						SetGenerator canTrigger = ability.canTrigger;
						ability.canTrigger = NonEmpty.instance();
						ability.triggerOn(triggerEvent, game.actualState, false);
						ability.canTrigger = canTrigger;
					}
				}

				Set commandZone = new Set(game.actualState.commandZone());

				java.util.Map<Parameter, Set> shuffleParameters = new java.util.HashMap<Parameter, Set>();
				shuffleParameters.put(Parameter.CAUSE, cause);
				shuffleParameters.put(Parameter.TO, commandZone);
				shuffleParameters.put(Parameter.INDEX, NEGATIVE_ONE);
				shuffleParameters.put(Parameter.OBJECT, objects);
				Event shuffleEvent = createEvent(game, "Put the revealed cards on the bottom of your planar deck in any order.", EventType.MOVE_OBJECTS, shuffleParameters);
				shuffleEvent.perform(event, true);

				event.setResult(Empty.set);
				return true;
			}
		};

		public SuperChaos(GameState state)
		{
			super(state, "Whenever you roll (C), reveal the top three cards of your planar deck. Each of the revealed cards' (C) abilities triggers. Then put the revealed cards on the bottom of your planar deck in any order.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			SetGenerator planarDeck = Planechase.planarDeck();
			SetGenerator toReveal = TopMost.instance(CommandZone.instance(), numberGenerator(3), planarDeck);

			EventFactory factory = new EventFactory(POOLS_OF_BECOMING_CHAOS_EVENT, "Reveal the top three cards of your planar deck. Each of the revealed cards' (C) abilities triggers. Then put the revealed cards on the bottom of your planar deck in any order.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.EVENT, TriggerEvent.instance(This.instance()));
			factory.parameters.put(EventType.Parameter.OBJECT, toReveal);
			this.addEffect(factory);

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public PoolsofBecoming(GameState state)
	{
		super(state);

		this.addAbility(new Recycle(state));

		this.addAbility(new SuperChaos(state));
	}
}

package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public final class Unearth extends Keyword
{
	public static final class UnearthAbility extends ActivatedAbility
	{
		/**
		 * @eparam CAUSE: the cause of this unearth
		 * @eparam CONTROLLER: the controller of the creature
		 * @eparam OBJECT: the object to return to play
		 * @eparam RESULT: empty
		 */
		public static final EventType UNEARTH_EVENT = new EventType("UNEARTH_EVENT")
		{
			@Override
			public Parameter affects()
			{
				return null;
			}

			@Override
			public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
			{
				Event moveEvent;
				boolean ret = true;

				Set cause = parameters.get(EventType.Parameter.CAUSE);
				Set controller = parameters.get(EventType.Parameter.CONTROLLER);
				Set thisCard = parameters.get(EventType.Parameter.OBJECT);

				// Return this card from your graveyard to play.
				{
					java.util.Map<EventType.Parameter, Set> moveParameters = new java.util.HashMap<EventType.Parameter, Set>();
					moveParameters.put(EventType.Parameter.CAUSE, cause);
					moveParameters.put(EventType.Parameter.CONTROLLER, controller);
					moveParameters.put(EventType.Parameter.OBJECT, thisCard);
					moveEvent = createEvent(game, "Return this card from your graveyard to play", EventType.PUT_ONTO_BATTLEFIELD, moveParameters);
					ret = moveEvent.perform(event, true) && ret;
				}

				GameObject it = game.actualState.get(moveEvent.getResult().getOne(ZoneChange.class).newObjectID);

				// It gains haste.
				{
					ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_OBJECT);
					part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(it));
					part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(new org.rnd.jmagic.engine.SimpleAbilityFactory(Haste.class)));

					java.util.Map<EventType.Parameter, Set> hasteParameters = new java.util.HashMap<EventType.Parameter, Set>();
					hasteParameters.put(EventType.Parameter.CAUSE, cause);
					hasteParameters.put(EventType.Parameter.EFFECT, new Set(part));
					hasteParameters.put(EventType.Parameter.EXPIRES, new Set(Empty.instance()));
					Event hasteEvent = createEvent(game, "It gains haste", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, hasteParameters);
					ret = hasteEvent.perform(event, true) && ret;
				}

				{
					// Trigger event
					SimpleEventPattern eot = new SimpleEventPattern(EventType.BEGIN_STEP);
					eot.put(EventType.Parameter.STEP, EndStepOf.instance(Players.instance()));

					// Trigger effect
					EventFactory exileFactory = new EventFactory(EventType.MOVE_OBJECTS, "Exile it.");
					exileFactory.parameters.put(EventType.Parameter.CAUSE, Identity.fromCollection(cause));
					exileFactory.parameters.put(EventType.Parameter.TO, ExileZone.instance());
					exileFactory.parameters.put(EventType.Parameter.OBJECT, Identity.instance(it));

					// Delayed trigger parameter map
					java.util.Map<EventType.Parameter, Set> trigParameters = new java.util.HashMap<EventType.Parameter, Set>();
					trigParameters.put(EventType.Parameter.CAUSE, cause);
					trigParameters.put(EventType.Parameter.EVENT, new Set(eot));
					trigParameters.put(EventType.Parameter.EFFECT, new Set(exileFactory));
					Event triggerEvent = createEvent(game, "Exile it at the beginning of the next end step.", EventType.CREATE_DELAYED_TRIGGER, trigParameters);
					ret = triggerEvent.perform(event, true) && ret;
				}

				{
					ZoneChangeReplacementEffect rfgEffect = new ZoneChangeReplacementEffect(game, "If it would leave the battlefield, exile it instead of putting it anywhere else.");
					rfgEffect.addPattern(new SimpleZoneChangePattern(new SimpleSetPattern(Battlefield.instance()), new Flashback.FlashbackExileReplacement.NotTheExileZonePattern(), new SimpleSetPattern(Identity.instance(it)), true));
					rfgEffect.changeDestination(ExileZone.instance());

					ContinuousEffect.Part part = replacementEffectPart(rfgEffect);

					java.util.Map<EventType.Parameter, Set> replacementParameters = new java.util.HashMap<EventType.Parameter, Set>();
					replacementParameters.put(EventType.Parameter.CAUSE, cause);
					replacementParameters.put(EventType.Parameter.EFFECT, new Set(part));
					replacementParameters.put(EventType.Parameter.EXPIRES, new Set(Empty.instance()));
					Event replacementEvent = createEvent(game, "If it would leave the battlefield, exile it instead of putting it anywhere else", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, replacementParameters);
					ret = replacementEvent.perform(event, true) && ret;
				}

				event.setResult(Empty.set);

				return ret;
			}
		};

		private final String cost;

		public UnearthAbility(GameState state, String cost)
		{
			super(state, cost + ": Return this card from your graveyard to play. It gains haste. Exile it at the beginning of the next end step. If it would leave the battlefield, exile it instead of putting it anywhere else. Play this ability only any time you could play a sorcery.");

			this.cost = cost;

			this.setManaCost(new ManaPool(cost));

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			parameters.put(EventType.Parameter.OBJECT, thisCard);
			this.addEffect(new EventFactory(UNEARTH_EVENT, parameters, "Return this card from your graveyard to the battlefield. It gains haste. Exile it at the beginning of the next end step. If it would leave the battlefield, exile it instead of putting it anywhere else."));

			this.activateOnlyFromGraveyard();
			this.activateOnlyAtSorcerySpeed();
		}

		@Override
		public UnearthAbility create(Game game)
		{
			return new UnearthAbility(game.physicalState, this.cost);
		}
	}

	private final String cost;

	public Unearth(GameState state, String cost)
	{
		super(state, "Unearth " + cost);
		this.cost = cost;
	}

	@Override
	public Unearth create(Game game)
	{
		return new Unearth(game.physicalState, this.cost);
	}

	@Override
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();
		ret.add(new UnearthAbility(this.state, this.cost));
		return ret;
	}
}

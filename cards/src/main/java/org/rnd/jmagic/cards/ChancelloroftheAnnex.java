package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Chancellor of the Annex")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("4WWW")
@ColorIdentity({Color.WHITE})
public final class ChancelloroftheAnnex extends Card
{
	public static final class ChancelloroftheAnnexAbility0 extends StaticAbility
	{
		/**
		 * A tracker that maps each player to the number of spells they have
		 * cast this game. If a player is not in the map, they have not cast any
		 * spells.
		 */
		public static final class CastTracker extends Tracker<java.util.Map<Integer, Integer>>
		{
			private java.util.Map<Integer, Integer> map = new java.util.HashMap<Integer, Integer>();
			private java.util.Map<Integer, Integer> unmodifiable = java.util.Collections.unmodifiableMap(this.map);

			@Override
			public CastTracker clone()
			{
				CastTracker ret = (CastTracker)super.clone();
				ret.map = new java.util.HashMap<Integer, Integer>(this.map);
				ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.map);
				return ret;
			}

			@Override
			protected java.util.Map<Integer, Integer> getValueInternal()
			{
				return this.unmodifiable;
			}

			@Override
			protected void reset()
			{
				// Never resets
			}

			@Override
			protected boolean match(GameState state, Event event)
			{
				return event.type == EventType.BECOMES_PLAYED && event.parameters.get(EventType.Parameter.OBJECT).evaluate(state, null).getOne(GameObject.class).isSpell();
			}

			@Override
			protected void update(GameState state, Event event)
			{
				int player = event.parameters.get(EventType.Parameter.PLAYER).evaluate(state, null).getOne(Player.class).ID;
				if(!this.map.containsKey(player))
					this.map.put(player, 1);
				else
					this.map.put(player, this.map.get(player) + 1);
			}
		}

		public static final class HasCastOneSpell extends SetGenerator
		{
			private static final HasCastOneSpell _instance = new HasCastOneSpell();

			public static HasCastOneSpell instance()
			{
				return _instance;
			}

			private HasCastOneSpell()
			{
				// Singleton SetGenerator
			}

			@Override
			public Set evaluate(GameState state, Identified thisObject)
			{
				Set ret = new Set();
				java.util.Map<Integer, Integer> values = state.getTracker(CastTracker.class).getValue(state);
				for(Player player: state.players)
					if(values.containsKey(player.ID) && values.get(player.ID) == 1)
						ret.add(player);
				return ret;
			}
		}

		public ChancelloroftheAnnexAbility0(GameState state)
		{
			super(state, "You may reveal this card from your opening hand. If you do, when each opponent casts his or her first spell of the game, counter that spell unless that player pays (1).");
			this.canApply = NonEmpty.instance();

			SetGenerator triggerEvent = TriggerEvent.instance(This.instance());
			SetGenerator thatSpell = EventResult.instance(triggerEvent);
			SetGenerator thatPlayer = EventParameter.instance(triggerEvent, EventType.Parameter.PLAYER);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BEGIN_THE_GAME_EFFECT);

			{
				EventFactory reveal = new EventFactory(EventType.REVEAL, "Reveal this card from your opening hand.");
				reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
				reveal.parameters.put(EventType.Parameter.OBJECT, This.instance());
				reveal.parameters.put(EventType.Parameter.EFFECT, Identity.instance(CurrentTurn.instance()));
				part.parameters.put(ContinuousEffectType.Parameter.EVENT, Identity.instance(reveal));
			}

			{
				EventFactory action = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "When each opponent casts his or her first spell of the game, counter that spell unless that player pays (1).");
				action.parameters.put(EventType.Parameter.CAUSE, This.instance());
				action.parameters.put(EventType.Parameter.EXPIRES, Empty.instance());

				{
					state.ensureTracker(new CastTracker());

					SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
					pattern.put(EventType.Parameter.PLAYER, HasCastOneSpell.instance());
					pattern.put(EventType.Parameter.OBJECT, SetPattern.CASTABLE);
					action.parameters.put(EventType.Parameter.EVENT, Identity.instance(pattern));
				}

				{
					EventFactory counter = counter(thatSpell, "Counter that spell.");

					EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay (1).");
					pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
					pay.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("(1)")));
					pay.parameters.put(EventType.Parameter.PLAYER, thatPlayer);

					EventFactory effect = unless(thatPlayer, counter, pay, "Counter that spell unless that player pays (1).");
					action.parameters.put(EventType.Parameter.EFFECT, Identity.instance(effect));
				}

				part.parameters.put(ContinuousEffectType.Parameter.ACTION, Identity.instance(action));
			}

			this.addEffectPart(part);
		}
	}

	public static final class ChancelloroftheAnnexAbility2 extends EventTriggeredAbility
	{
		public ChancelloroftheAnnexAbility2(GameState state)
		{
			super(state, "Whenever an opponent casts a spell, counter it unless that player pays (1).");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			pattern.put(EventType.Parameter.OBJECT, Spells.instance());
			this.addPattern(pattern);

			SetGenerator triggerEvent = TriggerEvent.instance(This.instance());
			SetGenerator thatSpell = EventResult.instance(triggerEvent);
			SetGenerator thatPlayer = EventParameter.instance(triggerEvent, EventType.Parameter.PLAYER);

			EventFactory counter = counter(thatSpell, "Counter it.");

			EventFactory pay = new EventFactory(EventType.PAY_MANA, "Pay (1).");
			pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			pay.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("(1)")));
			pay.parameters.put(EventType.Parameter.PLAYER, thatPlayer);

			EventFactory effect = unless(thatPlayer, counter, pay, "Counter it unless that player pays (1).");
			this.addEffect(effect);
		}
	}

	public ChancelloroftheAnnex(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(6);

		// You may reveal this card from your opening hand. If you do, when each
		// opponent casts his or her first spell of the game, counter that spell
		// unless that player pays (1).
		this.addAbility(new ChancelloroftheAnnexAbility0(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever an opponent casts a spell, counter it unless that player
		// pays (1).
		this.addAbility(new ChancelloroftheAnnexAbility2(state));
	}
}

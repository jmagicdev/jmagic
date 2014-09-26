package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Howl of the Horde")
@Types({Type.SORCERY})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class HowloftheHorde extends Card
{
	/**
	 * Keys are playerIDs, values are the number of sorceries and instants
	 * they've cast this turn
	 */
	public static final class CastTracker extends Tracker<java.util.Map<Integer, Integer>>
	{
		public static final class Generator extends SetGenerator
		{
			private SetGenerator players;

			public static Generator instance(SetGenerator players)
			{
				return new Generator(players);
			}

			private Generator(SetGenerator players)
			{
				this.players = players;
			}

			@Override
			public Set evaluate(GameState state, Identified thisObject)
			{
				CastTracker tracker = state.getTracker(CastTracker.class);
				java.util.Map<Integer, Integer> trackerValue = tracker.getValue(state);
				Set ret = new Set();
				for(Player p: this.players.evaluate(state, thisObject).getAll(Player.class))
					if(trackerValue.containsKey(p.ID))
						ret.add(trackerValue.get(p.ID));
					else
						ret.add(0);
				return ret;
			}
		}

		private java.util.Map<Integer, Integer> values = new java.util.HashMap<>();
		private java.util.Map<Integer, Integer> unmodifiable = java.util.Collections.unmodifiableMap(this.values);

		@Override
		protected CastTracker clone()
		{
			CastTracker ret = (CastTracker)super.clone();
			ret.values = new java.util.HashMap<>(this.values);
			ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.values);
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
			this.values.clear();
		}

		@Override
		protected boolean match(GameState state, Event event)
		{
			if(event.type != EventType.BECOMES_PLAYED)
				return false;

			java.util.Set<Type> types = event.parametersNow.get(EventType.Parameter.OBJECT).evaluate(state, null).getOne(GameObject.class).getTypes();
			return types.contains(Type.SORCERY) || types.contains(Type.INSTANT);
		}

		@Override
		protected void update(GameState state, Event event)
		{
			Player player = event.parametersNow.get(EventType.Parameter.PLAYER).evaluate(state, null).getOne(Player.class);
			if(this.values.containsKey(player.ID))
				this.values.put(player.ID, this.values.get(player.ID) + 1);
			else
				this.values.put(player.ID, 1);
		}
	}

	public HowloftheHorde(GameState state)
	{
		super(state);

		// When you cast your next instant or sorcery spell this turn, copy that
		// spell. You may choose new targets for the copy.

		// Raid \u2014 If you attacked with a creature this turn, when you cast
		// your next instant or sorcery spell this turn, copy that spell an
		// additional time. You may choose new targets for the copy.

		state.ensureTracker(new CastTracker());
		SetGenerator spellsNow = CastTracker.Generator.instance(You.instance());
		EventFactory noteNumberOfSpells = new EventFactory(NOTE, "When you cast your next");
		noteNumberOfSpells.parameters.put(EventType.Parameter.OBJECT, spellsNow);
		this.addEffect(noteNumberOfSpells);

		SetGenerator spellsBefore = EffectResult.instance(noteNumberOfSpells);
		SetGenerator haveCastSpell = Not.instance(Intersect.instance(spellsBefore, spellsNow));

		SimpleEventPattern castSpell = new SimpleEventPattern(EventType.BECOMES_PLAYED);
		castSpell.put(EventType.Parameter.PLAYER, You.instance());
		castSpell.put(EventType.Parameter.OBJECT, new TypePattern(Type.INSTANT, Type.SORCERY));

		// everything in the following event factory is evaluate with respect to
		// the delayed trigger.
		SetGenerator thatSpell = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT);
		EventFactory copy = new EventFactory(EventType.COPY_SPELL_OR_ABILITY, "Copy that spell. You may choose new targets for the copy.");
		copy.parameters.put(EventType.Parameter.CAUSE, This.instance());
		copy.parameters.put(EventType.Parameter.PLAYER, You.instance());
		copy.parameters.put(EventType.Parameter.OBJECT, thatSpell);

		EventFactory copyLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.");
		copyLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
		copyLater.parameters.put(EventType.Parameter.EXPIRES, Union.instance(EndMostFloatingEffects.instance(), haveCastSpell));
		copyLater.parameters.put(EventType.Parameter.EVENT, Identity.instance(castSpell));
		copyLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(copy));

		this.addEffect(copyLater);
		state.ensureTracker(new org.rnd.jmagic.engine.trackers.AttackTracker());
		this.addEffect(ifThen(Raid.instance(), copyLater, "\n\nIf you attacked with a creature this turn, when you cast your next instant or sorcery spell this turn, copy that spell an additional time. You may choose new targets for the copy."));

		// If you attacked with a creature this turn, when you cast your next
		// instant or sorcery spell this turn, copy that spell an additional
		// time. You may choose new targets for the copy.
	}
}

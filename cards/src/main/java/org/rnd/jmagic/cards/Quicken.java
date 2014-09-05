package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Quicken")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2014, r = Rarity.RARE), @Printings.Printed(ex = Expansion.GUILDPACT, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Quicken extends Card
{
	/**
	 * Keys are playerIDs, values are the number of sorceries they've cast this
	 * turn
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

			return event.parametersNow.get(EventType.Parameter.OBJECT).evaluate(state, null).getOne(GameObject.class).getTypes().contains(Type.SORCERY);
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

	public Quicken(GameState state)
	{
		super(state);


		// The next sorcery card you cast this turn can be cast as though it had
		// flash.

		// This is somewhat of a tricky effect. What we'd like to do is make a
		// floating effect that makes sorceries castable as though they had
		// flash that ends when you cast your first one. However you can't just
		// end it when a sorcery you control is on the stack, since you might
		// respond to your own sorcery with Quicken (and the effect would end
		// due to the sorcery you cast before it). We can't just track the
		// number of sorceries you've played and end the effect when that number
		// isn't zero (since then you wouldn't even need to be responding to
		// your own sorcery for the effect to end prematurely).

		// We need to end the effect when you cast your *next* sorcery *after*
		// this card resolves. So really what we want is to count the sorceries
		// you've cast, and end the effect when that count is different from
		// what it was as Quicken resolved. NOTE to the rescue.

		// Get the tracker we need
		state.ensureTracker(new CastTracker());

		// Count the sorceries we've cast so far
		EventFactory sorceriesSoFar = new EventFactory(NOTE, "The next sorcery card");
		sorceriesSoFar.parameters.put(EventType.Parameter.OBJECT, CastTracker.Generator.instance(You.instance()));
		this.addEffect(sorceriesSoFar);

		// Use the same generator to continuously count the sorceries you've
		// cast
		SetGenerator sorceriesNow = CastTracker.Generator.instance(You.instance());
		SetGenerator sorceriesThen = EffectResult.instance(sorceriesSoFar);

		// We've cast a sorcery since the card resolves when the numbers are
		// different
		SetGenerator castASorcerySinceThen = Not.instance(Intersect.instance(sorceriesThen, sorceriesNow));
		SetGenerator endEffect = Union.instance(castASorcerySinceThen, EndMostFloatingEffects.instance());

		SetGenerator youHavePriority = Intersect.instance(You.instance(), PlayerWithPriority.instance());

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MAY_CAST_TIMING);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, HasType.instance(Type.SORCERY));
		part.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(youHavePriority)));
		this.addEffect(createFloatingEffect(endEffect, "you cast this turn can be cast as though it had flash.\n\n", part));

		// Draw a card.
		this.addEffect(drawACard());
	}
}

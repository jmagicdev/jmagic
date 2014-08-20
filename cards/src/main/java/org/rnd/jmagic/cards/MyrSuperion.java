package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Myr Superion")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.MYR})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.RARE)})
@ColorIdentity({})
public final class MyrSuperion extends Card
{
	public static final class ManaFromCreatures extends SetGenerator
	{
		public static final class Tracker extends org.rnd.jmagic.engine.Tracker<java.util.Collection<ManaSymbol>>
		{
			private java.util.HashSet<ManaSymbol> mana = new java.util.HashSet<ManaSymbol>();
			private java.util.Set<ManaSymbol> unmodifiable = java.util.Collections.unmodifiableSet(this.mana);

			@SuppressWarnings("unchecked")
			@Override
			public Tracker clone()
			{
				Tracker ret = (Tracker)super.clone();
				ret.mana = (java.util.HashSet<ManaSymbol>)this.mana.clone();
				ret.unmodifiable = java.util.Collections.unmodifiableSet(ret.mana);
				return ret;
			}

			@Override
			protected java.util.Collection<ManaSymbol> getValueInternal()
			{
				return this.unmodifiable;
			}

			@Override
			protected void reset()
			{
				// No way to retain mana if it's still around, since we have no
				// way of checking a game state for players' mana pools here
			}

			@Override
			protected boolean match(GameState state, Event event)
			{
				if(event.type != EventType.ADD_MANA)
					return false;
				GameObject source = event.parameters.get(EventType.Parameter.SOURCE).evaluate(state, event.getSource()).getOne(GameObject.class);
				if(source == null)
					return false;
				return source.getTypes().contains(Type.CREATURE);
			}

			@Override
			protected void update(GameState state, Event event)
			{
				this.mana.addAll(event.getResult().getAll(ManaSymbol.class));
			}
		}

		private static SetGenerator _instance = null;

		private ManaFromCreatures()
		{
			// singleton generator
		}

		public static SetGenerator instance()
		{
			if(_instance == null)
				_instance = new ManaFromCreatures();
			return _instance;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			return Set.fromCollection(state.getTracker(Tracker.class).getValue(state));
		}
	}

	public static final class MyrSuperionAbility0 extends StaticAbility
	{
		public MyrSuperionAbility0(GameState state)
		{
			super(state, "Spend only mana produced by creatures to cast Myr Superion.");

			state.ensureTracker(new ManaFromCreatures.Tracker());
			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, ManaFromCreatures.instance());
			this.addEffectPart(part);

			this.canApply = THIS_IS_ON_THE_STACK;
		}
	}

	public MyrSuperion(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(6);

		// Spend only mana produced by creatures to cast Myr Superion.
		this.addAbility(new MyrSuperionAbility0(state));
	}
}

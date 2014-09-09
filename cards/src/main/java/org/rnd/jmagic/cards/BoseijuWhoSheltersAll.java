package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Boseiju, Who Shelters All")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.LAND})
@ColorIdentity({})
public final class BoseijuWhoSheltersAll extends Card
{
	public static final class BoseijuMana extends ManaSymbol
	{
		private static final long serialVersionUID = 1L;

		public BoseijuMana()
		{
			super(ManaType.COLORLESS);
			this.name = "If this mana is spent on an instant or sorcery spell, that spell can't be countered by spells or abilities.";
		}

		@Override
		public ManaSymbol create()
		{
			return new BoseijuMana();
		}
	}

	/** IDs of spells and abilities on which a Boseiju mana was spent */
	public static final class BoseijuTracker extends Tracker<java.util.Collection<Integer>>
	{
		private java.util.HashSet<Integer> value = new java.util.HashSet<Integer>();
		private java.util.Collection<Integer> unmodifiable = java.util.Collections.unmodifiableSet(this.value);

		@Override
		@SuppressWarnings("unchecked")
		public BoseijuTracker clone()
		{
			BoseijuTracker ret = (BoseijuTracker)super.clone();
			ret.value = (java.util.HashSet<Integer>)this.value.clone();
			ret.unmodifiable = java.util.Collections.unmodifiableSet(ret.value);
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
			if(event.type != EventType.PAY_MANA)
				return false;

			return event.getResult(state).getOne(BoseijuMana.class) != null;
		}

		@Override
		protected void reset()
		{
			this.value.clear();
		}

		@Override
		protected void update(GameState state, Event event)
		{
			GameObject spentOn = event.parameters.get(EventType.Parameter.OBJECT).evaluate(state, null).getOne(GameObject.class);
			this.value.add(spentOn.ID);
		}
	}

	public static final class BoseijuManaSpentOn extends SetGenerator
	{
		private static SetGenerator _instance = null;

		public static SetGenerator instance()
		{
			if(_instance == null)
				_instance = new BoseijuManaSpentOn();
			return _instance;
		}

		private BoseijuManaSpentOn()
		{
			// singleton
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Set ret = new Set();

			java.util.Collection<Integer> trackerValue = state.getTracker(BoseijuTracker.class).getValue(state);
			for(int id: trackerValue)
				ret.add(state.<GameObject>get(id));

			return ret;
		}
	}

	public static final class BoseijuWhoSheltersAllAbility1 extends ActivatedAbility
	{
		public BoseijuWhoSheltersAllAbility1(GameState state)
		{
			super(state, "(T), Pay 2 life: Add (1) to your mana pool. If that mana is spent on an instant or sorcery spell, that spell can't be countered by spells or abilities.");
			this.costsTap = true;
			this.addCost(payLife(You.instance(), 2, "Pay 2 life"));

			EventFactory mana = new EventFactory(EventType.ADD_MANA, "Add (1) to your mana pool.");
			mana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			mana.parameters.put(EventType.Parameter.MANA, Identity.instance(new BoseijuMana()));
			mana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(mana);

			state.ensureTracker(new BoseijuTracker());
			SetGenerator instantsAndSorceries = Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), Spells.instance());
			SetGenerator thatSpell = Intersect.instance(BoseijuManaSpentOn.instance(), instantsAndSorceries);

			SimpleEventPattern counterThatSpell = new SimpleEventPattern(EventType.COUNTER);
			counterThatSpell.put(EventType.Parameter.OBJECT, thatSpell);
			counterThatSpell.put(EventType.Parameter.CAUSE, spellsAndAbilities());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(counterThatSpell));

			SetGenerator youHaveMana = ManaInPool.instance(You.instance());
			SetGenerator keepEffect = Union.instance(youHaveMana, thatSpell);

			EventFactory shelter = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "If that mana is spent on an instant or sorcery spell, that spell can't be countered by spells or abilities.");
			shelter.parameters.put(EventType.Parameter.CAUSE, This.instance());
			shelter.parameters.put(EventType.Parameter.EFFECT, Identity.instance(part));
			shelter.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Not.instance(keepEffect)));
			this.addEffect(shelter);
		}
	}

	public BoseijuWhoSheltersAll(GameState state)
	{
		super(state);

		// Boseiju, Who Shelters All enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T), Pay 2 life: Add (1) to your mana pool. If that mana is spent on
		// an instant or sorcery spell, that spell can't be countered by spells
		// or abilities.
		this.addAbility(new BoseijuWhoSheltersAllAbility1(state));
	}
}

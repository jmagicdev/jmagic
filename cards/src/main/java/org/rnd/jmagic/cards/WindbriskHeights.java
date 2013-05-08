package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Windbrisk Heights")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class WindbriskHeights extends Card
{
	public static final class WindbriskHideaway extends org.rnd.jmagic.abilities.keywords.Hideaway
	{
		public static final class WindbriskExile extends org.rnd.jmagic.abilities.keywords.Hideaway.Exile
		{
			public WindbriskExile(GameState state)
			{
				super(state, WindbriskHeightsAbility2.class);
			}
		}

		public WindbriskHideaway(GameState state)
		{
			super(state);
		}

		@Override
		protected java.util.List<NonStaticAbility> createNonStaticAbilities()
		{
			return java.util.Collections.<NonStaticAbility>singletonList(new WindbriskExile(this.state));
		}
	}

	public static final class AttackTracker extends Tracker<java.util.Map<Integer, java.util.Collection<Integer>>>
	{
		public static final class Generator extends SetGenerator
		{
			private static final Generator _instance = new Generator();

			public static Generator instance()
			{
				return _instance;
			}

			private Generator()
			{
				// Singleton constructor
			}

			@Override
			public Set evaluate(GameState state, Identified thisObject)
			{
				AttackTracker tracker = state.getTracker(AttackTracker.class);
				java.util.Map<Integer, java.util.Collection<Integer>> trackerValue = tracker.getValue(state);
				Player you = ((GameObject)thisObject).getController(state);
				if(!trackerValue.containsKey(you.ID))
					return new Set();

				Set ret = new Set();
				for(int i: trackerValue.get(you.ID))
					ret.add(state.get(i));
				return ret;
			}
		}

		// keys are playerIDs, values are IDs of creatures that player attacked
		// with this turn
		private java.util.Map<Integer, java.util.Collection<Integer>> values = new java.util.HashMap<Integer, java.util.Collection<Integer>>();
		private java.util.Map<Integer, java.util.Collection<Integer>> unmodifiable = java.util.Collections.unmodifiableMap(this.values);

		@Override
		protected AttackTracker clone()
		{
			AttackTracker ret = (AttackTracker)super.clone();
			ret.values = new java.util.HashMap<Integer, java.util.Collection<Integer>>(this.values);
			ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.values);
			return ret;
		}

		@Override
		protected java.util.Map<Integer, java.util.Collection<Integer>> getValueInternal()
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
			return event.type == EventType.DECLARE_ONE_ATTACKER;
		}

		@Override
		protected void update(GameState state, Event event)
		{
			GameObject attacker = event.parametersNow.get(EventType.Parameter.OBJECT).evaluate(state, null).getOne(GameObject.class);
			java.util.Collection<Integer> value = this.values.get(attacker.controllerID);
			if(value == null)
			{
				value = new java.util.LinkedList<Integer>();
				this.values.put(attacker.controllerID, value);
			}
			value.add(attacker.ID);
		}
	}

	public static final class WindbriskHeightsAbility2 extends ActivatedAbility
	{
		public WindbriskHeightsAbility2(GameState state)
		{
			super(state, "(W), (T): You may play the exiled card without paying its mana cost if you attacked with three or more creatures this turn.");
			this.setManaCost(new ManaPool("(W)"));
			this.costsTap = true;

			state.ensureTracker(new AttackTracker());
			SetGenerator creaturesYouAttackedWith = Count.instance(AttackTracker.Generator.instance());
			SetGenerator condition = Intersect.instance(creaturesYouAttackedWith, Between.instance(3, null));

			EventFactory play = new EventFactory(PLAY_WITHOUT_PAYING_MANA_COSTS, "You may play the exiled card without paying its mana cost");
			play.parameters.put(EventType.Parameter.CAUSE, This.instance());
			play.parameters.put(EventType.Parameter.PLAYER, You.instance());
			play.parameters.put(EventType.Parameter.OBJECT, ChosenFor.instance(LinkedTo.instance(This.instance())));

			EventFactory effect = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "You may play the exiled card without paying its mana cost if you attacked with three or more creatures this turn.");
			effect.parameters.put(EventType.Parameter.IF, condition);
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(play));
			this.addEffect(effect);

			this.getLinkManager().addLinkClass(WindbriskHideaway.WindbriskExile.class);
		}
	}

	public WindbriskHeights(GameState state)
	{
		super(state);

		// Hideaway (This land enters the battlefield tapped. When it does, look
		// at the top four cards of your library, exile one face down, then put
		// the rest on the bottom of your library.)
		this.addAbility(new WindbriskHideaway(state));

		// (T): Add (W) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForW(state));

		// (W), (T): You may play the exiled card without paying its mana cost
		// if you attacked with three or more creatures this turn.
		this.addAbility(new WindbriskHeightsAbility2(state));
	}
}

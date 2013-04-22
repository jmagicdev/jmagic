package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Incursion Specialist")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class IncursionSpecialist extends Card
{
	/**
	 * A tracker that maps each player to the spells they have cast this turn.
	 * If a player is not in the map, they have not cast any spells.
	 */
	public static final class CastTracker extends Tracker<java.util.Map<Integer, java.util.List<Integer>>>
	{
		private java.util.Map<Integer, java.util.List<Integer>> map = new java.util.HashMap<Integer, java.util.List<Integer>>();
		private java.util.Map<Integer, java.util.List<Integer>> unmodifiable = java.util.Collections.unmodifiableMap(this.map);

		@Override
		public CastTracker clone()
		{
			CastTracker ret = (CastTracker)super.clone();
			ret.map = new java.util.HashMap<Integer, java.util.List<Integer>>(this.map);
			ret.unmodifiable = java.util.Collections.unmodifiableMap(ret.map);
			return ret;
		}

		@Override
		protected java.util.Map<Integer, java.util.List<Integer>> getValueInternal()
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
				this.map.put(player, new java.util.LinkedList<Integer>());

			GameObject spell = event.parameters.get(EventType.Parameter.OBJECT).evaluate(state, null).getOne(GameObject.class);
			this.map.get(player).add(spell.ID);
		}
	}

	public static final class CastYourSecondSpell implements EventPattern
	{
		@Override
		public boolean match(Event event, Identified object, GameState state)
		{
			if(event.type != EventType.BECOMES_PLAYED)
				return false;

			int you = ((GameObject)((TriggeredAbility)object).getSource(state)).controllerID;
			java.util.Map<Integer, java.util.List<Integer>> trackerValue = state.getTracker(CastTracker.class).getValue(state);
			if(!trackerValue.containsKey(you))
				return false;

			java.util.List<Integer> yourSpells = trackerValue.get(you);
			if(yourSpells.size() < 2)
				return false;

			GameObject cast = event.parameters.get(EventType.Parameter.OBJECT).evaluate(state, object).getOne(GameObject.class);
			return yourSpells.get(1) == cast.ID;
		}

		@Override
		public boolean looksBackInTime()
		{
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean matchesManaAbilities()
		{
			// TODO Auto-generated method stub
			return false;
		}
	}

	public static final class IncursionSpecialistAbility0 extends EventTriggeredAbility
	{
		public IncursionSpecialistAbility0(GameState state)
		{
			super(state, "Whenever you cast your second spell each turn, Incursion Specialist gets +2/+0 until end of turn and is unblockable this turn.");

			state.ensureTracker(new CastTracker());
			this.addPattern(new CastYourSecondSpell());
			this.addEffect(createFloatingEffect("Incursion Specialist gets +2/+0 until end of turn and is unblockable this turn.", modifyPowerAndToughness(ABILITY_SOURCE_OF_THIS, +2, +0), unblockable(ABILITY_SOURCE_OF_THIS)));
		}
	}

	public IncursionSpecialist(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Whenever you cast your second spell each turn, Incursion Specialist
		// gets +2/+0 until end of turn and is unblockable this turn.
		this.addAbility(new IncursionSpecialistAbility0(state));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.trackers.CastTracker;

@Name("Incursion Specialist")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class IncursionSpecialist extends Card
{
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
			super(state, "Whenever you cast your second spell each turn, Incursion Specialist gets +2/+0 until end of turn and can't be blocked this turn.");

			state.ensureTracker(new CastTracker());
			this.addPattern(new CastYourSecondSpell());
			this.addEffect(createFloatingEffect("Incursion Specialist gets +2/+0 until end of turn and can't be blocked this turn.", modifyPowerAndToughness(ABILITY_SOURCE_OF_THIS, +2, +0), unblockable(ABILITY_SOURCE_OF_THIS)));
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

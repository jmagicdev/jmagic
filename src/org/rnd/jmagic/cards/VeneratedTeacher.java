package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Venerated Teacher")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class VeneratedTeacher extends Card
{
	public static final class ETBTwoLevels extends EventTriggeredAbility
	{
		public ETBTwoLevels(GameState state)
		{
			super(state, "When Venerated Teacher enters the battlefield, put two level counters on each creature you control with level up.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator levelerCreatures = Intersect.instance(CREATURES_YOU_CONTROL, HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.LevelUp.class));
			this.addEffect(putCounters(2, Counter.CounterType.LEVEL, levelerCreatures, "Put two level counters on each creature you control with level up."));
		}
	}

	public VeneratedTeacher(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Venerated Teacher enters the battlefield, put two level counters
		// on each creature you control with level up.
		this.addAbility(new ETBTwoLevels(state));
	}
}

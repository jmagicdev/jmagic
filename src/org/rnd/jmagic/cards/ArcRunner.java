package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Arc Runner")
@Types({Type.CREATURE})
@SubTypes({SubType.OX, SubType.ELEMENTAL})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class ArcRunner extends Card
{
	public static final class ArcRunnerAbility1 extends EventTriggeredAbility
	{
		public ArcRunnerAbility1(GameState state)
		{
			super(state, "At the beginning of the end step, sacrifice Arc Runner.");
			this.addPattern(atTheBeginningOfTheEndStep());
			this.addEffect(sacrificeThis("Arc Runner"));
		}
	}

	public ArcRunner(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(1);

		// Haste (This creature can attack and (T) as soon as it comes under
		// your control.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// At the beginning of the end step, sacrifice Arc Runner.
		this.addAbility(new ArcRunnerAbility1(state));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Phantasmal Bear")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAR, SubType.ILLUSION})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class PhantasmalBear extends Card
{
	public static final class PhantasmalBearAbility0 extends EventTriggeredAbility
	{
		public PhantasmalBearAbility0(GameState state)
		{
			super(state, "When Phantasmal Bear becomes the target of a spell or ability, sacrifice it.");
			this.addPattern(new BecomesTheTargetPattern(ABILITY_SOURCE_OF_THIS));
			this.addEffect(sacrificeThis("Phantasmal Bear"));
		}
	}

	public PhantasmalBear(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Phantasmal Bear becomes the target of a spell or ability,
		// sacrifice it.
		this.addAbility(new PhantasmalBearAbility0(state));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Phantom Beast")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST, SubType.ILLUSION})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class PhantomBeast extends Card
{
	public static final class PhantomBeastAbility0 extends EventTriggeredAbility
	{
		public PhantomBeastAbility0(GameState state)
		{
			super(state, "When Phantom Beast becomes the target of a spell or ability, sacrifice it.");

			this.addPattern(new BecomesTheTargetPattern(ABILITY_SOURCE_OF_THIS));

			this.addEffect(sacrificeThis("Phantom Beast"));
		}
	}

	public PhantomBeast(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// When Phantom Beast becomes the target of a spell or ability,
		// sacrifice it.
		this.addAbility(new PhantomBeastAbility0(state));
	}
}

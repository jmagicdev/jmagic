package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Highland Game")
@Types({Type.CREATURE})
@SubTypes({SubType.ELK})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class HighlandGame extends Card
{
	public static final class HighlandGameAbility0 extends EventTriggeredAbility
	{
		public HighlandGameAbility0(GameState state)
		{
			super(state, "When Highland Game dies, you gain 2 life.");
			this.addPattern(whenThisDies());
			this.addEffect(gainLife(You.instance(), 2, "You gain 2 life."));
		}
	}

	public HighlandGame(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// When Highland Game dies, you gain 2 life.
		this.addAbility(new HighlandGameAbility0(state));
	}
}

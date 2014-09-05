package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Pride Guardian")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.MONK})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class PrideGuardian extends Card
{
	public static final class PrideGuardianAbility1 extends EventTriggeredAbility
	{
		public PrideGuardianAbility1(GameState state)
		{
			super(state, "Whenever Pride Guardian blocks, you gain 3 life.");
			this.addPattern(whenThisBlocks());
			this.addEffect(gainLife(You.instance(), 3, "You gain 3 life."));
		}
	}

	public PrideGuardian(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);

		// Defender (This creature can't attack.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// Whenever Pride Guardian blocks, you gain 3 life.
		this.addAbility(new PrideGuardianAbility1(state));
	}
}

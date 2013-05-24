package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Kiln Walker")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class KilnWalker extends Card
{
	public static final class KilnWalkerAbility0 extends EventTriggeredAbility
	{
		public KilnWalkerAbility0(GameState state)
		{
			super(state, "Whenever Kiln Walker attacks, it gets +3/+0 until end of turn.");
			this.addPattern(whenThisAttacks());
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +3, +0, "It gets +3/+0 until end of turn."));
		}
	}

	public KilnWalker(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);

		// Whenever Kiln Walker attacks, it gets +3/+0 until end of turn.
		this.addAbility(new KilnWalkerAbility0(state));
	}
}

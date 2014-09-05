package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Fortress Cyclops")
@Types({Type.CREATURE})
@SubTypes({SubType.CYCLOPS, SubType.SOLDIER})
@ManaCost("3RW")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class FortressCyclops extends Card
{
	public static final class FortressCyclopsAbility0 extends EventTriggeredAbility
	{
		public FortressCyclopsAbility0(GameState state)
		{
			super(state, "Whenever Fortress Cyclops attacks, it gets +3/+0 until end of turn.");
			this.addPattern(whenThisAttacks());
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +3, +0, "It gets +3/+0 until end of turn."));
		}
	}

	public static final class FortressCyclopsAbility1 extends EventTriggeredAbility
	{
		public FortressCyclopsAbility1(GameState state)
		{
			super(state, "Whenever Fortress Cyclops blocks, it gets +0/+3 until end of turn.");
			this.addPattern(whenThisBlocks());
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +0, +3, "It gets +0/+3 until end of turn."));
		}
	}

	public FortressCyclops(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Whenever Fortress Cyclops attacks, it gets +3/+0 until end of turn.
		this.addAbility(new FortressCyclopsAbility0(state));

		// Whenever Fortress Cyclops blocks, it gets +0/+3 until end of turn.
		this.addAbility(new FortressCyclopsAbility1(state));
	}
}

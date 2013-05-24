package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hamlet Captain")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class HamletCaptain extends Card
{
	public static final class HamletCaptainAbility0 extends EventTriggeredAbility
	{
		public HamletCaptainAbility0(GameState state)
		{
			super(state, "Whenever Hamlet Captain attacks or blocks, other Human creatures you control get +1/+1 until end of turn.");
			this.addPattern(whenThisAttacks());
			this.addPattern(whenThisBlocks());

			this.addEffect(ptChangeUntilEndOfTurn(RelativeComplement.instance(Intersect.instance(CREATURES_YOU_CONTROL, HasSubType.instance(SubType.HUMAN)), ABILITY_SOURCE_OF_THIS), +1, +1, "Other Human creatures you control get +1/+1 until end of turn."));
		}
	}

	public HamletCaptain(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever Hamlet Captain attacks or blocks, other Human creatures you
		// control get +1/+1 until end of turn.
		this.addAbility(new HamletCaptainAbility0(state));
	}
}

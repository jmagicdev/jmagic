package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nefashu")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.MUTANT})
@ManaCost("4BB")
@Printings({@Printings.Printed(ex = Expansion.SCOURGE, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class Nefashu extends Card
{
	public static final class WTFIsANefashu extends EventTriggeredAbility
	{
		public WTFIsANefashu(GameState state)
		{
			super(state, "Whenever Nefashu attacks, up to five target creatures each get -1/-1 until end of turn.");
			this.addPattern(whenThisAttacks());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			target.setNumber(0, 5);

			this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), -1, -1, "Up to five target creatures each get -1/-1 until end of turn."));
		}
	}

	public Nefashu(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(3);

		// Whenever Nefashu attacks, up to five target creatures each get -1/-1
		// until end of turn.
		this.addAbility(new WTFIsANefashu(state));
	}
}

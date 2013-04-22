package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bazaar Krovod")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class BazaarKrovod extends Card
{
	public static final class BazaarKrovodAbility0 extends EventTriggeredAbility
	{
		public BazaarKrovodAbility0(GameState state)
		{
			super(state, "Whenever Bazaar Krovod attacks, another target attacking creature gets +0/+2 until end of turn. Untap that creature.");
			this.addPattern(whenThisAttacks());

			SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(Attacking.instance(), ABILITY_SOURCE_OF_THIS), "another target attacking creature"));
			this.addEffect(ptChangeUntilEndOfTurn(target, +0, +2, "Another target attacking creature gets +0/+2 until end of turn."));
			this.addEffect(untap(target, "Untap that creature."));
		}
	}

	public BazaarKrovod(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(5);

		// Whenever Bazaar Krovod attacks, another target attacking creature
		// gets +0/+2 until end of turn. Untap that creature.
		this.addAbility(new BazaarKrovodAbility0(state));
	}
}

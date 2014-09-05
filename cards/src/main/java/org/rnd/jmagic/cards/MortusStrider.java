package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Mortus Strider")
@Types({Type.CREATURE})
@SubTypes({SubType.SKELETON})
@ManaCost("1UB")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class MortusStrider extends Card
{
	public static final class MortusStriderAbility0 extends EventTriggeredAbility
	{
		public MortusStriderAbility0(GameState state)
		{
			super(state, "When Mortus Strider dies, return it to its owner's hand.");
			this.addPattern(whenThisDies());
			this.addEffect(bounce(FutureSelf.instance(ABILITY_SOURCE_OF_THIS), "Return it to its owner's hand."));
		}
	}

	public MortusStrider(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Mortus Strider dies, return it to its owner's hand.
		this.addAbility(new MortusStriderAbility0(state));
	}
}

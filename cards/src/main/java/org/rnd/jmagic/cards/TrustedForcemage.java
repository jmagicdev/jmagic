package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Trusted Forcemage")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.HUMAN})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class TrustedForcemage extends Card
{
	public static final class TrustedForcemageAbility1 extends StaticAbility
	{
		public TrustedForcemageAbility1(GameState state)
		{
			super(state, "As long as Trusted Forcemage is paired with another creature, each of those creatures gets +1/+1.");
			SetGenerator pairedWithThis = PairedWith.instance(This.instance());
			this.canApply = Both.instance(this.canApply, pairedWithThis);

			SetGenerator both = Union.instance(This.instance(), pairedWithThis);
			this.addEffectPart(modifyPowerAndToughness(both, +1, +1));
		}
	}

	public TrustedForcemage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Soulbond (You may pair this creature with another unpaired creature
		// when either enters the battlefield. They remain paired for as long as
		// you control both of them.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Soulbond(state));

		// As long as Trusted Forcemage is paired with another creature, each of
		// those creatures gets +1/+1.
		this.addAbility(new TrustedForcemageAbility1(state));
	}
}

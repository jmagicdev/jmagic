package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wolfir Silverheart")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.WOLF})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class WolfirSilverheart extends Card
{
	public static final class WolfirSilverheartAbility1 extends StaticAbility
	{
		public WolfirSilverheartAbility1(GameState state)
		{
			super(state, "As long as Wolfir Silverheart is paired with another creature, each of those creatures gets +4/+4.");
			SetGenerator pairedWithThis = PairedWith.instance(This.instance());
			this.canApply = Both.instance(this.canApply, pairedWithThis);

			SetGenerator both = Union.instance(This.instance(), pairedWithThis);
			this.addEffectPart(modifyPowerAndToughness(both, +4, +4));

		}
	}

	public WolfirSilverheart(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Soulbond (You may pair this creature with another unpaired creature
		// when either enters the battlefield. They remain paired for as long as
		// you control both of them.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Soulbond(state));

		// As long as Wolfir Silverheart is paired with another creature, each
		// of those creatures gets +4/+4.
		this.addAbility(new WolfirSilverheartAbility1(state));
	}
}

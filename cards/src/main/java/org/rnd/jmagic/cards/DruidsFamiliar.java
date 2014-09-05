package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Druid's Familiar")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAR})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class DruidsFamiliar extends Card
{
	public static final class DruidsFamiliarAbility1 extends StaticAbility
	{
		public DruidsFamiliarAbility1(GameState state)
		{
			super(state, "As long as Druid's Familiar is paired with another creature, each of those creatures gets +2/+2.");
			SetGenerator pairedWithThis = PairedWith.instance(This.instance());
			this.canApply = Both.instance(this.canApply, pairedWithThis);

			SetGenerator both = Union.instance(This.instance(), pairedWithThis);
			this.addEffectPart(modifyPowerAndToughness(both, +2, +2));
		}
	}

	public DruidsFamiliar(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Soulbond (You may pair this creature with another unpaired creature
		// when either enters the battlefield. They remain paired for as long as
		// you control both of them.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Soulbond(state));

		// As long as Druid's Familiar is paired with another creature, each of
		// those creatures gets +2/+2.
		this.addAbility(new DruidsFamiliarAbility1(state));
	}
}

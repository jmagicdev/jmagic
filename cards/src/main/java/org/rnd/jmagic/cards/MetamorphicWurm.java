package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Metamorphic Wurm")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEPHANT, SubType.WURM})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Expansion.ODYSSEY, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class MetamorphicWurm extends Card
{
	public static final class Emergence extends StaticAbility
	{
		public Emergence(GameState state)
		{
			super(state, "Metamorphic Wurm gets +4/+4 as long as seven or more cards are in your graveyard.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +4, +4));

			this.canApply = Both.instance(this.canApply, Threshold.instance());
		}
	}

	public MetamorphicWurm(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new Emergence(state));
	}
}

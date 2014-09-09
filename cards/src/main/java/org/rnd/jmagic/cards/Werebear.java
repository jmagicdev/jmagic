package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Werebear")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.DRUID, SubType.BEAR})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class Werebear extends Card
{
	public static final class Lycanthropy extends StaticAbility
	{
		public Lycanthropy(GameState state)
		{
			super(state, "Werebear gets +3/+3 as long as seven or more cards are in your graveyard.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +3, +3));

			this.canApply = Both.instance(this.canApply, Threshold.instance());
		}
	}

	public Werebear(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(G)"));

		this.addAbility(new Lycanthropy(state));
	}
}

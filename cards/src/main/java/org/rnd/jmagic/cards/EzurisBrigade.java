package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ezuri's Brigade")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.WARRIOR})
@ManaCost("2GG")
@ColorIdentity({Color.GREEN})
public final class EzurisBrigade extends Card
{
	public static final class EzurisBrigadeAbility0 extends StaticAbility
	{
		public EzurisBrigadeAbility0(GameState state)
		{
			super(state, "As long as you control three or more artifacts, Ezuri's Brigade gets +4/+4 and has trample.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +4, +4));

			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Trample.class));

			this.canApply = Both.instance(this.canApply, Metalcraft.instance());
		}
	}

	public EzurisBrigade(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Metalcraft \u2014 As long as you control three or more artifacts,
		// Ezuri's Brigade gets +4/+4 and has trample.
		this.addAbility(new EzurisBrigadeAbility0(state));
	}
}

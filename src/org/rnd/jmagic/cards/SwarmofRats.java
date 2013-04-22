package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Swarm of Rats")
@Types({Type.CREATURE})
@SubTypes({SubType.RAT})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.PORTAL_SECOND_AGE, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class SwarmofRats extends Card
{
	public static final class SwarmofRatsAbility0 extends StaticAbility
	{
		public SwarmofRatsAbility0(GameState state)
		{
			super(state, "Swarm of Rats's power is equal to the number of Rats you control.");

			SetGenerator count = Count.instance(Intersect.instance(HasSubType.instance(SubType.RAT), ControlledBy.instance(You.instance())));
			this.addEffectPart(setPowerAndToughness(This.instance(), count, null));
		}
	}

	public SwarmofRats(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// Swarm of Rats's power is equal to the number of Rats you control.
		this.addAbility(new SwarmofRatsAbility0(state));
	}
}

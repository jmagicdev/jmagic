package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Timberpack Wolf")
@Types({Type.CREATURE})
@SubTypes({SubType.WOLF})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class TimberpackWolf extends Card
{
	public static final class TimberpackWolfAbility0 extends StaticAbility
	{
		public TimberpackWolfAbility0(GameState state)
		{
			super(state, "Timberpack Wolf gets +1/+1 for each other creature you control named Timberpack Wolf.");

			SetGenerator num = Count.instance(RelativeComplement.instance(Intersect.instance(CREATURES_YOU_CONTROL, HasName.instance("Timberpack Wolf")), This.instance()));
			this.addEffectPart(modifyPowerAndToughness(This.instance(), num, num));
		}
	}

	public TimberpackWolf(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Timberpack Wolf gets +1/+1 for each other creature you control named
		// Timberpack Wolf.
		this.addAbility(new TimberpackWolfAbility0(state));
	}
}

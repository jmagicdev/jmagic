package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Earth Servant")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("5R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class EarthServant extends Card
{
	public static final class MountainDefense extends StaticAbility
	{
		public MountainDefense(GameState state)
		{
			super(state, "Earth Servant gets +0/+1 for each Mountain you control.");

			SetGenerator count = Count.instance(Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.MOUNTAIN)));
			this.addEffectPart(modifyPowerAndToughness(This.instance(), numberGenerator(0), count));
		}
	}

	public EarthServant(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Earth Servant gets +0/+1 for each Mountain you control.
		this.addAbility(new MountainDefense(state));
	}
}

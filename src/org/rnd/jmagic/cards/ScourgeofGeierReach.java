package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scourge of Geier Reach")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class ScourgeofGeierReach extends Card
{
	public static final class ScourgeofGeierReachAbility0 extends StaticAbility
	{
		public ScourgeofGeierReachAbility0(GameState state)
		{
			super(state, "Scourge of Geier Reach gets +1/+1 for each creature your opponents control.");

			SetGenerator number = Count.instance(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))));

			this.addEffectPart(modifyPowerAndToughness(This.instance(), number, number));
		}
	}

	public ScourgeofGeierReach(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Scourge of Geier Reach gets +1/+1 for each creature your opponents
		// control.
		this.addAbility(new ScourgeofGeierReachAbility0(state));
	}
}

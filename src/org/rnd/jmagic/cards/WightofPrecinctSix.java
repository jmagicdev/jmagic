package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wight of Precinct Six")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class WightofPrecinctSix extends Card
{
	public static final class WightofPrecinctSixAbility0 extends StaticAbility
	{
		public WightofPrecinctSixAbility0(GameState state)
		{
			super(state, "Wight of Precinct Six gets +1/+1 for each creature card in your opponents' graveyards.");
			SetGenerator inOpponentsGraveyards = InZone.instance(GraveyardOf.instance(OpponentsOf.instance(You.instance())));
			SetGenerator amount = Count.instance(Intersect.instance(inOpponentsGraveyards, HasType.instance(Type.CREATURE)));
			this.addEffectPart(modifyPowerAndToughness(This.instance(), amount, amount));
		}
	}

	public WightofPrecinctSix(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Wight of Precinct Six gets +1/+1 for each creature card in your
		// opponents' graveyards.
		this.addAbility(new WightofPrecinctSixAbility0(state));
	}
}

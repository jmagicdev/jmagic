package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Court Homunculus")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.HOMUNCULUS})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Conflux.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class CourtHomunculus extends Card
{
	public static final class CourtHomunculusAbility0 extends StaticAbility
	{
		public CourtHomunculusAbility0(GameState state)
		{
			super(state, "Court Homunculus gets +1/+1 as long as you control another artifact.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), 1, 1));

			SetGenerator yourArtifacts = Intersect.instance(ArtifactPermanents.instance(), ControlledBy.instance(You.instance()));
			this.canApply = Both.instance(this.canApply, RelativeComplement.instance(yourArtifacts, This.instance()));
		}
	}

	public CourtHomunculus(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Court Homunculus gets +1/+1 as long as you control another artifact.
		this.addAbility(new CourtHomunculusAbility0(state));
	}
}

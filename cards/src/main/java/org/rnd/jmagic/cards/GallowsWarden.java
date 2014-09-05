package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Gallows Warden")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class GallowsWarden extends Card
{
	public static final class GallowsWardenAbility1 extends StaticAbility
	{
		public GallowsWardenAbility1(GameState state)
		{
			super(state, "Other Spirit creatures you control get +0/+1.");

			SetGenerator otherSpirits = RelativeComplement.instance(HasSubType.instance(SubType.SPIRIT), This.instance());
			SetGenerator otherSpiritCreaturesYouControl = Intersect.instance(otherSpirits, CREATURES_YOU_CONTROL);
			this.addEffectPart(modifyPowerAndToughness(otherSpiritCreaturesYouControl, +0, +1));
		}
	}

	public GallowsWarden(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Other Spirit creatures you control get +0/+1.
		this.addAbility(new GallowsWardenAbility1(state));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stitcher's Apprentice")
@Types({Type.CREATURE})
@SubTypes({SubType.HOMUNCULUS})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class StitchersApprentice extends Card
{
	public static final class StitchersApprenticeAbility0 extends ActivatedAbility
	{
		public StitchersApprenticeAbility0(GameState state)
		{
			super(state, "(1)(U), (T): Put a 2/2 blue Homunculus creature token onto the battlefield, then sacrifice a creature.");
			this.setManaCost(new ManaPool("(1)(U)"));
			this.costsTap = true;

			CreateTokensFactory factory = new CreateTokensFactory(1, 2, 2, "Put a 2/2 blue Homunculus creature token onto the battlefield.");
			factory.setColors(Color.BLUE);
			factory.setSubTypes(SubType.HOMUNCULUS);
			this.addEffect(factory.getEventFactory());

			this.addEffect(sacrifice(You.instance(), 1, CreaturePermanents.instance(), "Sacrifice a creature."));
		}
	}

	public StitchersApprentice(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// (1)(U), (T): Put a 2/2 blue Homunculus creature token onto the
		// battlefield, then sacrifice a creature.
		this.addAbility(new StitchersApprenticeAbility0(state));
	}
}

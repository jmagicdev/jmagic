package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Invasive Species")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class InvasiveSpecies extends Card
{
	public static final class InvasiveSpeciesAbility0 extends EventTriggeredAbility
	{
		public InvasiveSpeciesAbility0(GameState state)
		{
			super(state, "When Invasive Species enters the battlefield, return another permanent you control to its owner's hand.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(bounceChoice(You.instance(), 1, ControlledBy.instance(You.instance()), "Return another permanent you control to its owner's hand."));
		}
	}

	public InvasiveSpecies(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// When Invasive Species enters the battlefield, return another
		// permanent you control to its owner's hand.
		this.addAbility(new InvasiveSpeciesAbility0(state));
	}
}

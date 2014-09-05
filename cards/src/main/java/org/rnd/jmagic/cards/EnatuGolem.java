package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Enatu Golem")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.GOLEM})
@ManaCost("6")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class EnatuGolem extends Card
{
	public static final class CircleOfLife extends EventTriggeredAbility
	{
		public CircleOfLife(GameState state)
		{
			super(state, "When Enatu Golem dies, you gain 4 life.");

			this.addPattern(whenThisDies());

			this.addEffect(gainLife(You.instance(), 4, "You gain 4 life."));
		}
	}

	public EnatuGolem(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(5);

		// When Enatu Golem is put into a graveyard from the battlefield, you
		// gain 4 life.
		this.addAbility(new CircleOfLife(state));
	}
}

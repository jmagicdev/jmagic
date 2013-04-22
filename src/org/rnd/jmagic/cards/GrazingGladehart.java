package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Grazing Gladehart")
@Types({Type.CREATURE})
@SubTypes({SubType.ANTELOPE})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class GrazingGladehart extends Card
{
	public static final class LandLife extends EventTriggeredAbility
	{
		public LandLife(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under your control, you may gain 2 life.");
			this.addPattern(landfall());
			this.addEffect(youMay(gainLife(You.instance(), 2, "Gain 2 life"), "You may gain 2 life."));
		}
	}

	public GrazingGladehart(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, you may gain 2 life.
		this.addAbility(new LandLife(state));
	}
}

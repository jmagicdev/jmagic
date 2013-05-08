package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Peace Strider")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class PeaceStrider extends Card
{
	public static final class PeaceStriderAbility0 extends EventTriggeredAbility
	{
		public PeaceStriderAbility0(GameState state)
		{
			super(state, "When Peace Strider enters the battlefield, you gain 3 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 3, "You gain 3 life."));
		}
	}

	public PeaceStrider(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// When Peace Strider enters the battlefield, you gain 3 life.
		this.addAbility(new PeaceStriderAbility0(state));
	}
}

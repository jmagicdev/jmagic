package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Tireless Missionaries")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class TirelessMissionaries extends Card
{
	public static final class TirelessMissionariesAbility0 extends EventTriggeredAbility
	{
		public TirelessMissionariesAbility0(GameState state)
		{
			super(state, "When Tireless Missionaries enters the battlefield, you gain 3 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 3, "You gain 3 life."));
		}
	}

	public TirelessMissionaries(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// When Tireless Missionaries enters the battlefield, you gain 3 life.
		this.addAbility(new TirelessMissionariesAbility0(state));
	}
}

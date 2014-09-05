package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ondu Cleric")
@Types({Type.CREATURE})
@SubTypes({SubType.KOR, SubType.CLERIC, SubType.ALLY})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class OnduCleric extends Card
{
	public static final class AllyGainLife extends EventTriggeredAbility
	{
		public AllyGainLife(GameState state)
		{
			super(state, "Whenever Ondu Cleric or another Ally enters the battlefield under your control, you may gain life equal to the number of Allies you control.");

			this.addPattern(allyTrigger());

			EventFactory gainLife = gainLife(You.instance(), Count.instance(ALLIES_YOU_CONTROL), "Gain life equal to the number of Allies you control");
			this.addEffect(youMay(gainLife, "You may gain life equal to the number of Allies you control."));
		}
	}

	public OnduCleric(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever Ondu Cleric or another Ally enters the battlefield under
		// your control, you may gain life equal to the number of Allies you
		// control.
		this.addAbility(new AllyGainLife(state));
	}
}

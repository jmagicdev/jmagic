package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Forsaken Drifters")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class ForsakenDrifters extends Card
{
	public static final class ForsakenDriftersAbility0 extends EventTriggeredAbility
	{
		public ForsakenDriftersAbility0(GameState state)
		{
			super(state, "When Forsaken Drifters dies, put the top four cards of your library into your graveyard.");
			this.addPattern(whenThisDies());
			this.addEffect(millCards(You.instance(), 4, "Put the top four cards of your library into your graveyard."));
		}
	}

	public ForsakenDrifters(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);

		// When Forsaken Drifters dies, put the top four cards of your library
		// into your graveyard.
		this.addAbility(new ForsakenDriftersAbility0(state));
	}
}

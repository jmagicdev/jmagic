package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Returned Reveler")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.SATYR})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class ReturnedReveler extends Card
{
	public static final class ReturnedRevelerAbility0 extends EventTriggeredAbility
	{
		public ReturnedRevelerAbility0(GameState state)
		{
			super(state, "When Returned Reveler dies, each player puts the top three cards of his or her library into his or her graveyard.");
			this.addPattern(whenThisDies());
			this.addEffect(millCards(Players.instance(), 3, "Each player puts the top three cards of his or her library into his or her graveyard."));
		}
	}

	public ReturnedReveler(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// When Returned Reveler dies, each player puts the top three cards of
		// his or her library into his or her graveyard.
		this.addAbility(new ReturnedRevelerAbility0(state));
	}
}

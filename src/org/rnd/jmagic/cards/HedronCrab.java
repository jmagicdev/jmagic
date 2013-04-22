package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hedron Crab")
@Types({Type.CREATURE})
@SubTypes({SubType.CRAB})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class HedronCrab extends Card
{
	public static final class Landmill extends EventTriggeredAbility
	{
		public Landmill(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under your control, target player puts the top three cards of his or her library into his or her graveyard.");
			this.addPattern(landfall());

			Target target = this.addTarget(Players.instance(), "target player");
			this.addEffect(millCards(targetedBy(target), 3, "Target player puts the top three cards of his or her library into his or her graveyard."));
		}
	}

	public HedronCrab(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(2);

		// Landfall - Whenever a land enters the battlefield under your control,
		// target player puts the top three cards of his or her library into his
		// or her graveyard.
		this.addAbility(new Landmill(state));
	}
}

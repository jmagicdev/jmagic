package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ravenous Rats")
@Types({Type.CREATURE})
@SubTypes({SubType.RAT})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class RavenousRats extends Card
{
	public static final class CIPDiscard extends EventTriggeredAbility
	{
		public CIPDiscard(GameState state)
		{
			super(state, "When Ravenous Rats enters the battlefield, target opponent discards a card.");
			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(OpponentsOf.instance(You.instance()), "target opponent");
			this.addEffect(discardCards(targetedBy(target), 1, "Target opponent discards a card."));
		}
	}

	public RavenousRats(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new CIPDiscard(state));
	}
}

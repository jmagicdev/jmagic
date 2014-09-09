package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Geralf's Mindcrusher")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.HORROR})
@ManaCost("4UU")
@ColorIdentity({Color.BLUE})
public final class GeralfsMindcrusher extends Card
{
	public static final class GeralfsMindcrusherAbility0 extends EventTriggeredAbility
	{
		public GeralfsMindcrusherAbility0(GameState state)
		{
			super(state, "When Geralf's Mindcrusher enters the battlefield, target player puts the top five cards of his or her library into his or her graveyard.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(millCards(target, 5, "Target player puts the top five cards of his or her library into his or her graveyard."));
		}
	}

	public GeralfsMindcrusher(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// When Geralf's Mindcrusher enters the battlefield, target player puts
		// the top five cards of his or her library into his or her graveyard.
		this.addAbility(new GeralfsMindcrusherAbility0(state));

		// Undying (When this creature dies, if it had no +1/+1 counters on it,
		// return it to the battlefield under its owner's control with a +1/+1
		// counter on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Undying(state));
	}
}

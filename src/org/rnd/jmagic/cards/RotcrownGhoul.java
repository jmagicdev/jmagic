package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rotcrown Ghoul")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class RotcrownGhoul extends Card
{
	public static final class RotcrownGhoulAbility0 extends EventTriggeredAbility
	{
		public RotcrownGhoulAbility0(GameState state)
		{
			super(state, "When Rotcrown Ghoul dies, target player puts the top five cards of his or her library into his or her graveyard.");
			this.addPattern(whenThisDies());
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(millCards(target, 5, "Target player puts the top five cards of his or her library into his or her graveyard."));
		}
	}

	public RotcrownGhoul(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// When Rotcrown Ghoul dies, target player puts the top five cards of
		// his or her library into his or her graveyard.
		this.addAbility(new RotcrownGhoulAbility0(state));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Brain Freeze")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.SCOURGE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class BrainFreeze extends Card
{
	public BrainFreeze(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		this.addEffect(millCards(targetedBy(target), 3, "Target player puts the top three cards of his or her library into his or her graveyard."));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Storm(state));
	}
}

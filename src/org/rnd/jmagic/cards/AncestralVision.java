package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ancestral Vision")
@Types({Type.SORCERY})
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class AncestralVision extends Card
{
	public AncestralVision(GameState state)
	{
		super(state);

		this.setColorIndicator(Color.BLUE);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Suspend(state, 4, "(U)"));

		Target target = this.addTarget(Players.instance(), "target player");
		this.addEffect(drawCards(targetedBy(target), 3, "Target player draws three cards."));
	}
}

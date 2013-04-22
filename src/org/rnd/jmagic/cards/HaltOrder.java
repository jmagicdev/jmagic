package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Halt Order")
@Types({Type.INSTANT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class HaltOrder extends Card
{
	public HaltOrder(GameState state)
	{
		super(state);

		// Counter target artifact spell.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(Spells.instance(), HasType.instance(Type.ARTIFACT)), "target artifact spell"));
		this.addEffect(counter(target, "Counter target artifact spell."));

		// Draw a card.
		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}

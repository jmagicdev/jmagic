package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Disrupt")
@Types({Type.INSTANT})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class Disrupt extends Card
{
	public Disrupt(GameState state)
	{
		super(state);

		Target target = this.addTarget(Intersect.instance(Spells.instance(), HasType.instance(Type.INSTANT, Type.SORCERY)), "target instant or sorcery spell");

		// Counter target instant or sorcery spell unless its controller pays
		// (1).
		this.addEffect(counterTargetUnlessControllerPays("(1)", target));

		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}

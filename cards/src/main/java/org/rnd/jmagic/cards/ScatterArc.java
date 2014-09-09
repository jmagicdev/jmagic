package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scatter Arc")
@Types({Type.INSTANT})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class ScatterArc extends Card
{
	public ScatterArc(GameState state)
	{
		super(state);

		// Counter target noncreature spell.
		SetGenerator noncreatureSpells = RelativeComplement.instance(Spells.instance(), HasType.instance(Type.CREATURE));
		SetGenerator target = targetedBy(this.addTarget(noncreatureSpells, "target noncreature spell"));
		this.addEffect(counter(target, "Counter target noncreature spell"));

		// Draw a card.
		this.addEffect(drawCards(You.instance(), 1, " Draw a card."));
	}
}

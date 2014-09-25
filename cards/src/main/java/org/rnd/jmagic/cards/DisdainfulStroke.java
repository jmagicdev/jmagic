package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Disdainful Stroke")
@Types({Type.INSTANT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class DisdainfulStroke extends Card
{
	public DisdainfulStroke(GameState state)
	{
		super(state);

		// Counter target spell with converted mana cost 4 or greater.
		SetGenerator legal = Intersect.instance(Spells.instance(), HasConvertedManaCost.instance(Between.instance(4, null)));
		SetGenerator target = targetedBy(this.addTarget(legal, "target spell with converted mana cost 4 or greater"));
		this.addEffect(counter(target, "Counter target spell with converted mana cost 4 or greater."));
	}
}

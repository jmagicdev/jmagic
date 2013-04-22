package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.Convenience;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spell Snare")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.DISSENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class SpellSnare extends Card
{
	public SpellSnare(GameState state)
	{
		super(state);

		Target target = this.addTarget(Intersect.instance(Spells.instance(), HasConvertedManaCost.instance(numberGenerator(2))), "target spell with converted mana cost 2");

		this.addEffect(Convenience.counter(targetedBy(target), "Counter target spell with converted mana cost 2."));
	}
}

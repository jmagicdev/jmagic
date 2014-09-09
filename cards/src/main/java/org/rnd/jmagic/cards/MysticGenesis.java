package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mystic Genesis")
@Types({Type.INSTANT})
@ManaCost("2GUU")
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class MysticGenesis extends Card
{
	public MysticGenesis(GameState state)
	{
		super(state);

		// Counter target spell. Put an X/X green Ooze creature token onto the
		// battlefield, where X is that spell's converted mana cost.
		SetGenerator target = targetedBy(this.addTarget(Spells.instance(), "target spell"));
		SetGenerator X = ConvertedManaCostOf.instance(target);

		this.addEffect(counter(target, "Counter target spell."));

		CreateTokensFactory factory = new CreateTokensFactory(numberGenerator(1), X, X, "Put an X/X green Ooze creature token onto the battlefield, where X is that spell's converted mana cost.");
		factory.setColors(Color.GREEN);
		factory.setSubTypes(SubType.OOZE);
		this.addEffect(factory.getEventFactory());
	}
}

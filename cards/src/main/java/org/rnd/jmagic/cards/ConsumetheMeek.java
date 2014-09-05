package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Consume the Meek")
@Types({Type.INSTANT})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class ConsumetheMeek extends Card
{
	public ConsumetheMeek(GameState state)
	{
		super(state);

		// Destroy each creature with converted mana cost 3 or less. They can't
		// be regenerated.
		SetGenerator meekCreatures = Intersect.instance(CreaturePermanents.instance(), HasConvertedManaCost.instance(Between.instance(null, 3)));
		this.addEffects(bury(this, meekCreatures, "Destroy each creature with converted mana cost 3 or less. They can't be regenerated."));
	}
}

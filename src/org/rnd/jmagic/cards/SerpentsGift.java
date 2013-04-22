package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Serpent's Gift")
@Types({Type.INSTANT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class SerpentsGift extends Card
{
	public SerpentsGift(GameState state)
	{
		super(state);

		// Target creature gains deathtouch until end of turn. (Any amount of
		// damage it deals to a creature is enough to destroy it.)
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Deathtouch.class, "Target creature gains deathtouch until end of turn."));
	}
}

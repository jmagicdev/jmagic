package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Smother")
@Types({Type.INSTANT})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Smother extends Card
{
	public Smother(GameState state)
	{
		super(state);

		// Destroy target creature with converted mana cost 3 or less. It can't
		// be regenerated.
		Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasConvertedManaCost.instance(Between.instance(null, 3))), "target creature with converted mana cost 3 or less");
		this.addEffects(bury(this, targetedBy(target), "Destroy target creature with converted mana cost 3 or less. It can't be regenerated."));
	}
}

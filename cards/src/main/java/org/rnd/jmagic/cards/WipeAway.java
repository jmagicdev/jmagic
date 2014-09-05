package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Wipe Away")
@Types({Type.INSTANT})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = TimeSpiral.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class WipeAway extends Card
{
	public WipeAway(GameState state)
	{
		super(state);

		// Split second (As long as this spell is on the stack, players can't
		// cast spells or activate abilities that aren't mana abilities.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.SplitSecond(state));

		// Return target permanent to its owner's hand.
		Target target = this.addTarget(Permanents.instance(), "target permanent");
		this.addEffect(bounce(targetedBy(target), "Return target permanent to its owner's hand."));
	}
}

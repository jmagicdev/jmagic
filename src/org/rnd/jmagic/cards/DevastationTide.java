package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Devastation Tide")
@Types({Type.SORCERY})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class DevastationTide extends Card
{
	public DevastationTide(GameState state)
	{
		super(state);

		// Return all nonland permanents to their owners' hands.
		this.addEffect(bounce(RelativeComplement.instance(Permanents.instance(), LandPermanents.instance()), "Return all nonland permanents to their owners' hands."));

		// Miracle (1)(U) (You may cast this card for its miracle cost when you
		// draw it if it's the first card you drew this turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Miracle(state, "(1)(U)"));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Street Spasm")
@Types({Type.INSTANT})
@ManaCost("XR")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class StreetSpasm extends Card
{
	public StreetSpasm(GameState state)
	{
		super(state);

		// Street Spasm deals X damage to target creature without flying you
		// don't control.
		SetGenerator creature = CreaturePermanents.instance();
		SetGenerator creatureWithoutFlying = RelativeComplement.instance(creature, HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
		SetGenerator creatureWithoutFlyingYouDontControl = RelativeComplement.instance(creatureWithoutFlying, ControlledBy.instance(You.instance()));
		SetGenerator target = targetedBy(this.addTarget(creatureWithoutFlyingYouDontControl, "target creature without flying you don't control"));
		this.addEffect(spellDealDamage(ValueOfX.instance(This.instance()), target, "Street Spasm deals X damage to target creature without flying you don't control."));

		// Overload (X)(X)(R)(R) (You may cast this spell for its overload cost.
		// If you do, change its text by replacing all instances of "target"
		// with "each.")
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Overload(state, "(X)(X)(R)(R)"));
	}
}

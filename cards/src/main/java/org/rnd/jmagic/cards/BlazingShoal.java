package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Blazing Shoal")
@Types({Type.INSTANT})
@SubTypes({SubType.ARCANE})
@ManaCost("XRR")
@Printings({@Printings.Printed(ex = BetrayersOfKamigawa.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class BlazingShoal extends Card
{
	public BlazingShoal(GameState state)
	{
		super(state);

		// You may exile a red card with converted mana cost X from your hand
		// rather than pay Blazing Shoal's mana cost.
		this.addAbility(new ShoalAbility(state, Color.RED, this.getName()));

		// Target creature gets +X/+0 until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeUntilEndOfTurn(target, ValueOfX.instance(This.instance()), numberGenerator(+0), "Target creature gets +X/+0 until end of turn."));
	}
}

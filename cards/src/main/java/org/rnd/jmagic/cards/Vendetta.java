package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Vendetta")
@Types({Type.INSTANT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON), @Printings.Printed(ex = MercadianMasques.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class Vendetta extends Card
{
	public Vendetta(GameState state)
	{
		super(state);

		// Destroy target nonblack creature. It can't be regenerated.
		SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), HasColor.instance(Color.BLACK)), "target nonblack creature"));
		this.addEffects(bury(this, target, "Destroy target nonblack creature. It can't be regenerated."));

		// You lose life equal to that creature's toughness.
		this.addEffect(loseLife(You.instance(), ToughnessOf.instance(target), "You lose life equal to that creature's toughness."));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Hero's Downfall")
@Types({Type.INSTANT})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Theros.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class HerosDownfall extends Card
{
	public HerosDownfall(GameState state)
	{
		super(state);

		// Destroy target creature or planeswalker.
		SetGenerator legal = Intersect.instance(Permanents.instance(), HasType.instance(Type.CREATURE, Type.PLANESWALKER));
		SetGenerator target = targetedBy(this.addTarget(legal, "target creature or planeswalker"));
		this.addEffect(destroy(target, "Destroy target creature or planeswalker."));
	}
}

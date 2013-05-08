package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dreadbore")
@Types({Type.SORCERY})
@ManaCost("BR")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class Dreadbore extends Card
{
	public Dreadbore(GameState state)
	{
		super(state);

		// Destroy target creature or planeswalker.
		SetGenerator legal = Intersect.instance(Permanents.instance(), HasType.instance(Type.CREATURE, Type.PLANESWALKER));
		SetGenerator target = targetedBy(this.addTarget(legal, "target creature or planeswalker"));
		this.addEffect(destroy(target, "Destroy target creature or planeswalker."));
	}
}

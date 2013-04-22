package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Guttural Response")
@Types({Type.INSTANT})
@ManaCost("(R/G)")
@Printings({@Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class GutturalResponse extends Card
{
	public GutturalResponse(GameState state)
	{
		super(state);

		// Counter target blue instant spell.
		SetGenerator targetable = Intersect.instance(HasColor.instance(Color.BLUE), HasType.instance(Type.INSTANT), Spells.instance());
		SetGenerator target = targetedBy(this.addTarget(targetable, "target blue instant spell"));
		this.addEffect(counter(target, "Counter target blue instant spell."));
	}
}

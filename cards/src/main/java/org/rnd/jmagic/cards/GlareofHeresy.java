package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Glare of Heresy")
@Types({Type.SORCERY})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Theros.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class GlareofHeresy extends Card
{
	public GlareofHeresy(GameState state)
	{
		super(state);

		// Exile target white permanent.
		SetGenerator whitePermanents = Intersect.instance(HasColor.instance(Color.WHITE), Permanents.instance());
		SetGenerator target = targetedBy(this.addTarget(whitePermanents, "target white permanent"));
		this.addEffect(exile(target, "Exile target white permanent."));
	}
}

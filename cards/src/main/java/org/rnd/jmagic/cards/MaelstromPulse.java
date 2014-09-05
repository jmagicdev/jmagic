package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Maelstrom Pulse")
@Types({Type.SORCERY})
@ManaCost("1BG")
@Printings({@Printings.Printed(ex = AlaraReborn.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class MaelstromPulse extends Card
{
	public MaelstromPulse(GameState state)
	{
		super(state);

		// Destroy target nonland permanent and all other permanents with the
		// same name as that permanent.
		SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND)), "target nonland permanent"));
		SetGenerator stuffToDestroy = Union.instance(target, Intersect.instance(Permanents.instance(), HasName.instance(NameOf.instance(target))));
		this.addEffect(destroy(stuffToDestroy, "Destroy target nonland permanent and all other permanents with the same name as that permanent."));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shred Memory")
@Types({Type.INSTANT})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class ShredMemory extends Card
{
	public ShredMemory(GameState state)
	{
		super(state);

		// Exile up to four target cards from a single graveyard.
		Target target = new Target.SingleZone(InZone.instance(GraveyardOf.instance(Players.instance())), "up to four target cards from a single graveyard");
		target.setNumber(0, 4);
		this.addTarget(target);

		this.addEffect(exile(targetedBy(target), "Exile up to four target cards from a single graveyard."));

		// Transmute (1)(B)(B)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Transmute(state, "(1)(B)(B)"));
	}
}

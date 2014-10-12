package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spite of Mogis")
@Types({Type.SORCERY})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class SpiteofMogis extends Card
{
	public SpiteofMogis(GameState state)
	{
		super(state);

		// Spite of Mogis deals damage to target creature equal to the number of
		// instant and sorcery cards in your graveyard.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		SetGenerator deadThings = Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), InZone.instance(GraveyardOf.instance(You.instance())));
		SetGenerator dmg = Count.instance(deadThings);
		this.addEffect(spellDealDamage(dmg, target, "Spite of Mogis deals damage to target creature equal to the number of instant and sorcery cards in your graveyard."));

		// Scry 1. (Look at the top card of your library. You may put that card
		// on the bottom of your library.)
		this.addEffect(scry(1, "Scry 1."));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Unmake the Graves")
@Types({Type.INSTANT})
@ManaCost("4B")
@ColorIdentity({Color.BLACK})
public final class UnmaketheGraves extends Card
{
	public UnmaketheGraves(GameState state)
	{
		super(state);

		// Convoke (Your creatures can help cast this spell. Each creature you
		// tap while casting this spell pays for (1) or one mana of that
		// creature's color.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Convoke(state));

		// Return up to two target creature cards from your graveyard to your
		// hand.
		SetGenerator deadThings = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance())));
		SetGenerator target = targetedBy(this.addTarget(deadThings, "two target creature cards from your graveyard").setNumber(0, 2));
		this.addEffect(putIntoHand(target, You.instance(), "Return up to two target creature cards from your graveyard to your hand."));
	}
}

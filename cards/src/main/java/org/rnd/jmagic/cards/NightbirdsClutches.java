package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nightbird's Clutches")
@Types({Type.SORCERY})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class NightbirdsClutches extends Card
{
	public NightbirdsClutches(GameState state)
	{
		super(state);

		// Up to two target creatures can't block this turn.
		Target target = this.addTarget(CreaturePermanents.instance(), "up to two target creatures");
		target.setNumber(0, 2);

		this.addEffect(cantBlockThisTurn(targetedBy(target), "Up to two target creatures can't block this turn."));

		// Flashback (3)(R) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(3)(R)"));
	}
}

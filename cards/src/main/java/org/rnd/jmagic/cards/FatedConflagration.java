package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fated Conflagration")
@Types({Type.INSTANT})
@ManaCost("1RRR")
@ColorIdentity({Color.RED})
public final class FatedConflagration extends Card
{
	public FatedConflagration(GameState state)
	{
		super(state);

		// Fated Conflagration deals 5 damage to target creature or
		// planeswalker.
		SetGenerator legal = Intersect.instance(HasType.instance(Type.CREATURE, Type.PLANESWALKER), Permanents.instance());
		SetGenerator target = targetedBy(this.addTarget(legal, "target creature or planeswalker"));
		this.addEffect(spellDealDamage(5, target, "Fated Conflagration deals 5 damage to target creature or planeswalker."));

		// If it's your turn, scry 2.
		SetGenerator itsYourTurn = Intersect.instance(OwnerOf.instance(CurrentTurn.instance()), You.instance());
		this.addEffect(ifThen(itsYourTurn, scry(2, "Scry 2."), "If it's your turn, scry 2."));
	}
}

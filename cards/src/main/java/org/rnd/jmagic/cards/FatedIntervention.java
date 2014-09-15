package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fated Intervention")
@Types({Type.INSTANT})
@ManaCost("2GGG")
@ColorIdentity({Color.GREEN})
public final class FatedIntervention extends Card
{
	public FatedIntervention(GameState state)
	{
		super(state);

		// Put two 3/3 green Centaur enchantment creature tokens onto the
		// battlefield.
		CreateTokensFactory centaurs = new CreateTokensFactory(2, 3, 3, "Put two 3/3 green Centaur enchantment creature tokens onto the battlefield.");
		centaurs.setColors(Color.GREEN);
		centaurs.setSubTypes(SubType.CENTAUR);
		centaurs.setEnchantment();
		this.addEffect(centaurs.getEventFactory());

		// If it's your turn, scry 2.
		SetGenerator itsYourTurn = Intersect.instance(OwnerOf.instance(CurrentTurn.instance()), You.instance());
		this.addEffect(ifThen(itsYourTurn, scry(2, "Scry 2."), "If it's your turn, scry 2."));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Master the Way")
@Types({Type.SORCERY})
@ManaCost("3UR")
@ColorIdentity({Color.RED, Color.BLUE})
public final class MastertheWay extends Card
{
	public MastertheWay(GameState state)
	{
		super(state);

		// Draw a card.
		this.addEffect(drawACard());

		// Master the Way deals damage to target creature or player equal to the
		// number of cards in your hand.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
		SetGenerator dmg = Count.instance(InZone.instance(HandOf.instance(You.instance())));
		this.addEffect(spellDealDamage(dmg, target, "Master the Way deals damage to target creature or player equal to the number of cards in your hand."));
	}
}

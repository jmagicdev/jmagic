package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sorin's Vengeance")
@Types({Type.SORCERY})
@ManaCost("4BBB")
@ColorIdentity({Color.BLACK})
public final class SorinsVengeance extends Card
{
	public SorinsVengeance(GameState state)
	{
		super(state);

		// Sorin's Vengeance deals 10 damage to target player and you gain 10
		// life.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		this.addEffect(spellDealDamage(10, target, "Sorin's Vengeance deals 10 damage to target player"));
		this.addEffect(gainLife(You.instance(), 10, "and you gain 10 life."));
	}
}

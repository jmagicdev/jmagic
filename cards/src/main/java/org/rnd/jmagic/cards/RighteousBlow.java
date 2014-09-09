package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Righteous Blow")
@Types({Type.INSTANT})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class RighteousBlow extends Card
{
	public RighteousBlow(GameState state)
	{
		super(state);

		// Righteous Blow deals 2 damage to target attacking or blocking
		// creature.
		SetGenerator target = targetedBy(this.addTarget(Union.instance(Attacking.instance(), Blocking.instance()), "target attacking or blocking creature"));
		this.addEffect(spellDealDamage(3, target, "Righteous Blow deals 2 damage to target attacking or blocking creature."));
	}
}

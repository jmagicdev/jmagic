package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Jeskai Charm")
@Types({Type.INSTANT})
@ManaCost("URW")
@ColorIdentity({Color.RED, Color.WHITE, Color.BLUE})
public final class JeskaiCharm extends Card
{
	public JeskaiCharm(GameState state)
	{
		super(state);

		// Choose one \u2014

		// • Put target creature on top of its owner's library.
		{
			SetGenerator target = targetedBy(this.addTarget(1, CreaturePermanents.instance(), "target creature"));
			this.addEffect(1, putOnTopOfLibrary(target, "Put target creature on top of its owner's library."));
		}

		// • Jeskai Charm deals 4 damage to target opponent.
		{
			SetGenerator target = targetedBy(this.addTarget(2, OpponentsOf.instance(You.instance()), "target opponent"));
			this.addEffect(2, spellDealDamage(4, target, "Jeskai Charm deals 4 damage to target opponent."));
		}

		// • Creatures you control get +1/+1 and gain lifelink until end of
		// turn.
		{
			this.addEffect(3, ptChangeAndAbilityUntilEndOfTurn(CREATURES_YOU_CONTROL, +1, +1, "Creatures you control get +1/+1 and gain lifelink until end of turn.", org.rnd.jmagic.abilities.keywords.Lifelink.class));
		}
	}
}

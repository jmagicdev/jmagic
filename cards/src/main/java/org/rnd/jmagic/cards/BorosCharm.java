package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Boros Charm")
@Types({Type.INSTANT})
@ManaCost("RW")
@ColorIdentity({Color.WHITE, Color.RED})
public final class BorosCharm extends Card
{
	public BorosCharm(GameState state)
	{
		super(state);

		// Choose one \u2014

		// Boros Charm deals 4 damage to target player
		{
			SetGenerator target = targetedBy(this.addTarget(1, Players.instance(), "target player"));
			this.addEffect(1, spellDealDamage(4, target, "Boros Charm deals 4 damage to target player."));
		}

		// permanents you control gain indestructible until end of turn
		{
			this.addEffect(2, createFloatingEffect("Creatures you control gain indestructible until end of turn.", addAbilityToObject(ControlledBy.instance(You.instance()), org.rnd.jmagic.abilities.keywords.Indestructible.class)));
		}

		// target creature gains double strike until end of turn.
		{
			SetGenerator target = targetedBy(this.addTarget(3, CreaturePermanents.instance(), "target creature"));
			this.addEffect(3, addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.DoubleStrike.class, "Target creature gains double strike until end of turn."));
		}
	}
}

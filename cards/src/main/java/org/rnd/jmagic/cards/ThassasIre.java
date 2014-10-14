package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thassa's Ire")
@Types({Type.ENCHANTMENT})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class ThassasIre extends Card
{
	public static final class ThassasIreAbility0 extends ActivatedAbility
	{
		public ThassasIreAbility0(GameState state)
		{
			super(state, "(3)(U): You may tap or untap target creature.");
			this.setManaCost(new ManaPool("(3)(U)"));
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(youMay(tapOrUntap(target, "target creature"), "You may tap or untap target creature."));
		}
	}

	public ThassasIre(GameState state)
	{
		super(state);

		// (3)(U): You may tap or untap target creature.
		this.addAbility(new ThassasIreAbility0(state));
	}
}

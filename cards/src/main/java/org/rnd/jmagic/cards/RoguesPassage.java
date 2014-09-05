package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Rogue's Passage")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class RoguesPassage extends Card
{
	public static final class RoguesPassageAbility1 extends ActivatedAbility
	{
		public RoguesPassageAbility1(GameState state)
		{
			super(state, "(4), (T): Target creature can't be blocked this turn.");
			this.setManaCost(new ManaPool("(4)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(createFloatingEffect("Target creature can't be blocked this turn.", unblockable(target)));
		}
	}

	public RoguesPassage(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (4), (T): Target creature is unblockable this turn.
		this.addAbility(new RoguesPassageAbility1(state));
	}
}

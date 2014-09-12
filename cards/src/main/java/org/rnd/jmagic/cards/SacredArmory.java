package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sacred Armory")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class SacredArmory extends Card
{
	public static final class SacredArmoryAbility0 extends ActivatedAbility
	{
		public SacredArmoryAbility0(GameState state)
		{
			super(state, "(2): Target creature gets +1/+0 until end of turn.");
			this.setManaCost(new ManaPool("(2)"));
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(ptChangeUntilEndOfTurn(target, +1, +0, "Target creature gets +1/+0 until end of turn."));
		}
	}

	public SacredArmory(GameState state)
	{
		super(state);

		// (2): Target creature gets +1/+0 until end of turn.
		this.addAbility(new SacredArmoryAbility0(state));
	}
}

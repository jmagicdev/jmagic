package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sunhome, Fortress of the Legion")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.RED})
public final class SunhomeFortressoftheLegion extends Card
{
	public static final class SunhomeFortressoftheLegionAbility1 extends ActivatedAbility
	{
		public SunhomeFortressoftheLegionAbility1(GameState state)
		{
			super(state, "(2)(R)(W), (T): Target creature gains double strike until end of turn.");
			this.setManaCost(new ManaPool("(2)(R)(W)"));
			this.costsTap = true;

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			this.addEffect(addAbilityUntilEndOfTurn(targetedBy(target), org.rnd.jmagic.abilities.keywords.DoubleStrike.class, "Target creature gains double strike until end of turn."));
		}
	}

	public SunhomeFortressoftheLegion(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (2)(R)(W), (T): Target creature gains double strike until end of
		// turn.
		this.addAbility(new SunhomeFortressoftheLegionAbility1(state));
	}
}

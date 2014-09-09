package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Silver-Inlaid Dagger")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@ColorIdentity({})
public final class SilverInlaidDagger extends Card
{
	public static final class SilverInlaidDaggerAbility0 extends StaticAbility
	{
		public SilverInlaidDaggerAbility0(GameState state)
		{
			super(state, "Equipped creature gets +2/+0.");

			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +2, +0));
		}
	}

	public static final class SilverInlaidDaggerAbility1 extends StaticAbility
	{
		public SilverInlaidDaggerAbility1(GameState state)
		{
			super(state, "As long as equipped creature is a Human, it gets an additional +1/+0.");

			SetGenerator equipped = EquippedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(equipped, +1, +0));

			this.canApply = Both.instance(this.canApply, Intersect.instance(equipped, HasSubType.instance(SubType.HUMAN)));
		}
	}

	public SilverInlaidDagger(GameState state)
	{
		super(state);

		// Equipped creature gets +2/+0.
		this.addAbility(new SilverInlaidDaggerAbility0(state));

		// As long as equipped creature is a Human, it gets an additional +1/+0.
		this.addAbility(new SilverInlaidDaggerAbility1(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}

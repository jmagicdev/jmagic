package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Heavy Mattock")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@ColorIdentity({})
public final class HeavyMattock extends Card
{
	public static final class HeavyMattockAbility0 extends StaticAbility
	{
		public HeavyMattockAbility0(GameState state)
		{
			super(state, "Equipped creature gets +1/+1.");
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +1, +1));
		}
	}

	public static final class HeavyMattockAbility1 extends StaticAbility
	{
		public HeavyMattockAbility1(GameState state)
		{
			super(state, "As long as equipped creature is a Human, it gets an additional +1/+1.");

			SetGenerator equipped = EquippedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(equipped, +1, +1));

			this.canApply = Both.instance(this.canApply, Intersect.instance(equipped, HasSubType.instance(SubType.HUMAN)));
		}
	}

	public HeavyMattock(GameState state)
	{
		super(state);

		// Equipped creature gets +1/+1.
		this.addAbility(new HeavyMattockAbility0(state));

		// As long as equipped creature is a Human, it gets an additional +1/+1.
		this.addAbility(new HeavyMattockAbility1(state));

		// Equip (2) ((2): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}

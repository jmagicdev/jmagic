package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sharpened Pitchfork")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@ColorIdentity({})
public final class SharpenedPitchfork extends Card
{
	public static final class SharpenedPitchforkAbility0 extends StaticAbility
	{
		public SharpenedPitchforkAbility0(GameState state)
		{
			super(state, "Equipped creature has first strike.");

			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.FirstStrike.class));
		}
	}

	public static final class SharpenedPitchforkAbility1 extends StaticAbility
	{
		public SharpenedPitchforkAbility1(GameState state)
		{
			super(state, "As long as equipped creature is a Human, it gets +1/+1.");

			SetGenerator equipped = EquippedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(equipped, +1, +1));

			this.canApply = Both.instance(this.canApply, Intersect.instance(equipped, HasSubType.instance(SubType.HUMAN)));
		}
	}

	public SharpenedPitchfork(GameState state)
	{
		super(state);

		// Equipped creature has first strike.
		this.addAbility(new SharpenedPitchforkAbility0(state));

		// As long as equipped creature is a Human, it gets +1/+1.
		this.addAbility(new SharpenedPitchforkAbility1(state));

		// Equip (1)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}

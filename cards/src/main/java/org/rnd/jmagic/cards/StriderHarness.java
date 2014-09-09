package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Strider Harness")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@ColorIdentity({})
public final class StriderHarness extends Card
{
	public static final class StriderHarnessAbility0 extends StaticAbility
	{
		public StriderHarnessAbility0(GameState state)
		{
			super(state, "Equipped creature gets +1/+1 and has haste.");

			SetGenerator equipped = EquippedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(equipped, +1, +1));
			this.addEffectPart(addAbilityToObject(equipped, org.rnd.jmagic.abilities.keywords.Haste.class));
		}
	}

	public StriderHarness(GameState state)
	{
		super(state);

		// Equipped creature gets +1/+1 and has haste.
		this.addAbility(new StriderHarnessAbility0(state));

		// Equip (1)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}

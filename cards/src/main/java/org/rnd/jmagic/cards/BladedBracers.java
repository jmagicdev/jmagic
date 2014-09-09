package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bladed Bracers")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@ColorIdentity({})
public final class BladedBracers extends Card
{
	public static final class BladedBracersAbility0 extends StaticAbility
	{
		public BladedBracersAbility0(GameState state)
		{
			super(state, "Equipped creature gets +1/+1.");
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +1, +1));
		}
	}

	public static final class BladedBracersAbility1 extends StaticAbility
	{
		public BladedBracersAbility1(GameState state)
		{
			super(state, "As long as equipped creature is a Human or an Angel, it has vigilance.");
			SetGenerator equipped = EquippedBy.instance(This.instance());
			this.canApply = Intersect.instance(equipped, HasSubType.instance(SubType.HUMAN, SubType.ANGEL));
			this.addEffectPart(addAbilityToObject(equipped, org.rnd.jmagic.abilities.keywords.Vigilance.class));
		}
	}

	public BladedBracers(GameState state)
	{
		super(state);

		// Equipped creature gets +1/+1.
		this.addAbility(new BladedBracersAbility0(state));

		// As long as equipped creature is a Human or an Angel, it has
		// vigilance.
		this.addAbility(new BladedBracersAbility1(state));

		// Equip (2) ((2): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Butcher's Cleaver")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class ButchersCleaver extends Card
{
	public static final class ButchersCleaverAbility0 extends StaticAbility
	{
		public ButchersCleaverAbility0(GameState state)
		{
			super(state, "Equipped creature gets +3/+0.");
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +3, 0));
		}
	}

	public static final class ButchersCleaverAbility1 extends StaticAbility
	{
		public ButchersCleaverAbility1(GameState state)
		{
			super(state, "As long as equipped creature is a Human, it has lifelink.");
			SetGenerator equipped = EquippedBy.instance(This.instance());
			this.canApply = Intersect.instance(equipped, HasSubType.instance(SubType.HUMAN));
			this.addEffectPart(addAbilityToObject(equipped, org.rnd.jmagic.abilities.keywords.Lifelink.class));
		}
	}

	public ButchersCleaver(GameState state)
	{
		super(state);

		// Equipped creature gets +3/+0.
		this.addAbility(new ButchersCleaverAbility0(state));

		// As long as equipped creature is a Human, it has lifelink.
		this.addAbility(new ButchersCleaverAbility1(state));

		// Equip (3)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(3)"));
	}
}

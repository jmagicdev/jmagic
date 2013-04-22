package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Field Marshal")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("1WW")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.COLDSNAP, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class FieldMarshal extends Card
{
	public static final class SoldierLord extends StaticAbility
	{
		public SoldierLord(GameState state)
		{
			super(state, "Other Soldier creatures get +1/+1 and have first strike.");

			SetGenerator otherSoldierCreatures = RelativeComplement.instance(Intersect.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.SOLDIER)), This.instance());

			this.addEffectPart(modifyPowerAndToughness(otherSoldierCreatures, 1, 1));

			this.addEffectPart(addAbilityToObject(otherSoldierCreatures, org.rnd.jmagic.abilities.keywords.FirstStrike.class));
		}
	}

	public FieldMarshal(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		SetGenerator soldiers = Intersect.instance(HasSubType.instance(SubType.SOLDIER), CreaturePermanents.instance());
		SetGenerator others = RelativeComplement.instance(soldiers, This.instance());
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, others, "Other Soldier creatures", +1, +1, org.rnd.jmagic.abilities.keywords.FirstStrike.class, true));
	}
}

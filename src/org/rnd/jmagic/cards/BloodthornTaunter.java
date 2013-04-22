package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bloodthorn Taunter")
@Types({Type.CREATURE})
@SubTypes({SubType.SCOUT, SubType.HUMAN})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BloodthornTaunter extends Card
{
	public static final class BloodthornTaunterAbility1 extends ActivatedAbility
	{
		public BloodthornTaunterAbility1(GameState state)
		{
			super(state, "(T): Target creature with power 5 or greater gains haste until end of turn.");
			this.costsTap = true;

			SetGenerator powerFiveOrGreater = HasPower.instance(Between.instance(5, null));
			SetGenerator creaturesWithPowerFiveOrGreater = Intersect.instance(CreaturePermanents.instance(), powerFiveOrGreater);
			SetGenerator target = targetedBy(this.addTarget(creaturesWithPowerFiveOrGreater, "target creature with power 5 or greater"));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Haste.class, "Target creature with power 5 or greater gains haste until end of turn."));
		}
	}

	public BloodthornTaunter(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// (T): Target creature with power 5 or greater gains haste until end of
		// turn.
		this.addAbility(new BloodthornTaunterAbility1(state));
	}
}

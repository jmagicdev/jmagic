package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Crimson Mage")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class CrimsonMage extends Card
{
	public static final class CrimsonMageAbility0 extends ActivatedAbility
	{
		public CrimsonMageAbility0(GameState state)
		{
			super(state, "(R): Target creature you control gains haste until end of turn.");
			this.setManaCost(new ManaPool("(R)"));
			SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Haste.class, "Target creature you control gains haste until end of turn."));
		}
	}

	public CrimsonMage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// (R): Target creature you control gains haste until end of turn. (It
		// can attack and (T) this turn.)
		this.addAbility(new CrimsonMageAbility0(state));
	}
}

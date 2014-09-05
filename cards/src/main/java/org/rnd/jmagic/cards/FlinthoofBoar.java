package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Flinthoof Boar")
@Types({Type.CREATURE})
@SubTypes({SubType.BOAR})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class FlinthoofBoar extends Card
{
	public static final class FlinthoofBoarAbility0 extends StaticAbility
	{
		public FlinthoofBoarAbility0(GameState state)
		{
			super(state, "Flinthoof Boar gets +1/+1 as long as you control a Mountain.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), 1, 1));

			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator mountain = HasSubType.instance(SubType.MOUNTAIN);
			this.canApply = Both.instance(this.canApply, Intersect.instance(youControl, mountain));
		}
	}

	public static final class FlinthoofBoarAbility1 extends ActivatedAbility
	{
		public FlinthoofBoarAbility1(GameState state)
		{
			super(state, "(R): Flinthoof Boar gains haste until end of turn.");
			this.setManaCost(new ManaPool("(R)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Haste.class, "Flinthoof Boar gains haste until end of turn."));
		}
	}

	public FlinthoofBoar(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flinthoof Boar gets +1/+1 as long as you control a Mountain.
		this.addAbility(new FlinthoofBoarAbility0(state));

		// (R): Flinthoof Boar gains haste until end of turn. (It can attack and
		// (T) this turn.)
		this.addAbility(new FlinthoofBoarAbility1(state));
	}
}

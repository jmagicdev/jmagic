package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Thraben Doomsayer")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.CLERIC})
@ManaCost("1WW")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class ThrabenDoomsayer extends Card
{
	public static final class ThrabenDoomsayerAbility0 extends ActivatedAbility
	{
		public ThrabenDoomsayerAbility0(GameState state)
		{
			super(state, "(T): Put a 1/1 white Human creature token onto the battlefield.");
			this.costsTap = true;

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Human creature token onto the battlefield.");
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.HUMAN);
			this.addEffect(token.getEventFactory());
		}
	}

	public static final class ThrabenDoomsayerAbility1 extends StaticAbility
	{
		public ThrabenDoomsayerAbility1(GameState state)
		{
			super(state, "As long as you have 5 or less life, other creatures you control get +2/+2.");

			this.addEffectPart(modifyPowerAndToughness(RelativeComplement.instance(CREATURES_YOU_CONTROL, This.instance()), +2, +2));

			this.canApply = Both.instance(this.canApply, FatefulHour.instance());
		}
	}

	public ThrabenDoomsayer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (T): Put a 1/1 white Human creature token onto the battlefield.
		this.addAbility(new ThrabenDoomsayerAbility0(state));

		// Fateful hour \u2014 As long as you have 5 or less life, other
		// creatures you control get +2/+2.
		this.addAbility(new ThrabenDoomsayerAbility1(state));
	}
}

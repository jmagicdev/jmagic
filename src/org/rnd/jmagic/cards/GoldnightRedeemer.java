package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goldnight Redeemer")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("4WW")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class GoldnightRedeemer extends Card
{
	public static final class GoldnightRedeemerAbility1 extends EventTriggeredAbility
	{
		public GoldnightRedeemerAbility1(GameState state)
		{
			super(state, "When Goldnight Redeemer enters the battlefield, you gain 2 life for each other creature you control.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator amount = Multiply.instance(numberGenerator(2), Count.instance(RelativeComplement.instance(CREATURES_YOU_CONTROL, ABILITY_SOURCE_OF_THIS)));
			this.addEffect(gainLife(You.instance(), amount, "You gain 2 life for each other creature you control."));
		}
	}

	public GoldnightRedeemer(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Goldnight Redeemer enters the battlefield, you gain 2 life for
		// each other creature you control.
		this.addAbility(new GoldnightRedeemerAbility1(state));
	}
}

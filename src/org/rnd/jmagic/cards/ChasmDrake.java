package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Chasm Drake")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class ChasmDrake extends Card
{
	public static final class ChasmDrakeAbility1 extends EventTriggeredAbility
	{
		public ChasmDrakeAbility1(GameState state)
		{
			super(state, "Whenever Chasm Drake attacks, target creature you control gains flying until end of turn.");
			this.addPattern(whenThisAttacks());

			SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Flying.class, "Target creature you control gains flying until end of turn."));
		}
	}

	public ChasmDrake(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Chasm Drake attacks, target creature you control gains
		// flying until end of turn.
		this.addAbility(new ChasmDrakeAbility1(state));
	}
}

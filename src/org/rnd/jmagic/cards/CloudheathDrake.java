package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Cloudheath Drake")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.DRAKE})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class CloudheathDrake extends Card
{
	public static final class CloudheathDrakeAbility1 extends ActivatedAbility
	{
		public CloudheathDrakeAbility1(GameState state)
		{
			super(state, "(1)(W): Cloudheath Drake gains vigilance until end of turn.");
			this.setManaCost(new ManaPool("(1)(W)"));

			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Flying.class, "Cloudheath Drake gains vigilance until end of turn."));
		}
	}

	public CloudheathDrake(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (1)(W): Cloudheath Drake gains vigilance until end of turn.
		this.addAbility(new CloudheathDrakeAbility1(state));
	}
}

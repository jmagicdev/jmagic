package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kemba's Skyguard")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.KNIGHT})
@ManaCost("1WW")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class KembasSkyguard extends Card
{
	public static final class KembasSkyguardAbility1 extends EventTriggeredAbility
	{
		public KembasSkyguardAbility1(GameState state)
		{
			super(state, "When Kemba's Skyguard enters the battlefield, you gain 2 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 2, "You gain 2 life."));
		}
	}

	public KembasSkyguard(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Kemba's Skyguard enters the battlefield, you gain 2 life.
		this.addAbility(new KembasSkyguardAbility1(state));
	}
}

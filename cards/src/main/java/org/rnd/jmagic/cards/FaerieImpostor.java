package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Faerie Impostor")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.FAERIE})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class FaerieImpostor extends Card
{
	public static final class FaerieImpostorAbility1 extends EventTriggeredAbility
	{
		public FaerieImpostorAbility1(GameState state)
		{
			super(state, "When Faerie Impostor enters the battlefield, sacrifice it unless you return another creature you control to its owner's hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator anotherCreatureYouControl = RelativeComplement.instance(CREATURES_YOU_CONTROL, ABILITY_SOURCE_OF_THIS);
			EventFactory bounce = bounceChoice(You.instance(), 1, anotherCreatureYouControl, "Return another creature you control to its owner's hand.");

			this.addEffect(unless(You.instance(), sacrificeThis("it"), bounce, "Sacrifice it unless you return another creature you control to its owner's hand."));
		}
	}

	public FaerieImpostor(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Faerie Impostor enters the battlefield, sacrifice it unless you
		// return another creature you control to its owner's hand.
		this.addAbility(new FaerieImpostorAbility1(state));
	}
}

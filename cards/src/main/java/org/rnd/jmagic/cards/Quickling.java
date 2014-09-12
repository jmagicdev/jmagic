package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Quickling")
@Types({Type.CREATURE})
@SubTypes({SubType.FAERIE, SubType.ROGUE})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class Quickling extends Card
{
	public static final class QuicklingAbility2 extends EventTriggeredAbility
	{
		public QuicklingAbility2(GameState state)
		{
			super(state, "When Quickling enters the battlefield, sacrifice it unless you return another creature you control to its owner's hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator anotherCreature = RelativeComplement.instance(CREATURES_YOU_CONTROL, ABILITY_SOURCE_OF_THIS);
			EventFactory bounce = bounceChoice(You.instance(), 1, anotherCreature, "Return another creature you control to its owner's hand.");
			this.addEffect(unless(You.instance(), sacrificeThis("Quickling"), bounce, "Sacrifice Quickling unless you return another creature you control to its owner's hand."));
		}
	}

	public Quickling(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Quickling enters the battlefield, sacrifice it unless you return
		// another creature you control to its owner's hand.
		this.addAbility(new QuicklingAbility2(state));
	}
}

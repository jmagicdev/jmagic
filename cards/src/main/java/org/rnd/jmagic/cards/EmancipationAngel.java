package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Emancipation Angel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("1WW")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class EmancipationAngel extends Card
{
	public static final class EmancipationAngelAbility1 extends EventTriggeredAbility
	{
		public EmancipationAngelAbility1(GameState state)
		{
			super(state, "When Emancipation Angel enters the battlefield, return a permanent you control to its owner's hand.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(bounceChoice(You.instance(), 1, ControlledBy.instance(You.instance()), "Return a permanent you control to its owner's hand."));
		}
	}

	public EmancipationAngel(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Emancipation Angel enters the battlefield, return a permanent
		// you control to its owner's hand.
		this.addAbility(new EmancipationAngelAbility1(state));
	}
}

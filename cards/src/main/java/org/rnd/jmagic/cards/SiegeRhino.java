package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Siege Rhino")
@Types({Type.CREATURE})
@SubTypes({SubType.RHINO})
@ManaCost("1WBG")
@ColorIdentity({Color.WHITE, Color.BLACK, Color.GREEN})
public final class SiegeRhino extends Card
{
	public static final class SiegeRhinoAbility1 extends EventTriggeredAbility
	{
		public SiegeRhinoAbility1(GameState state)
		{
			super(state, "When Siege Rhino enters the battlefield, each opponent loses 3 life and you gain 3 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(loseLife(OpponentsOf.instance(You.instance()), 3, "Each opponent loses 3 life."));
			this.addEffect(gainLife(You.instance(), 3, "You gain 3 life."));
		}
	}

	public SiegeRhino(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// When Siege Rhino enters the battlefield, each opponent loses 3 life
		// and you gain 3 life.
		this.addAbility(new SiegeRhinoAbility1(state));
	}
}

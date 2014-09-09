package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thraben Sentry")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
@BackFace(ThrabenMilitia.class)
public final class ThrabenSentry extends Card
{
	public static final class ThrabenSentryAbility1 extends EventTriggeredAbility
	{
		public ThrabenSentryAbility1(GameState state)
		{
			super(state, "Whenever another creature you control dies, you may transform Thraben Sentry.");
			this.addPattern(whenXDies(RelativeComplement.instance(CREATURES_YOU_CONTROL, ABILITY_SOURCE_OF_THIS)));
			this.addEffect(youMay(transformThis("Thraben Sentry"), "You may transform Thraben Sentry."));
		}
	}

	public ThrabenSentry(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// Whenever another creature you control dies, you may transform Thraben
		// Sentry.
		this.addAbility(new ThrabenSentryAbility1(state));
	}
}

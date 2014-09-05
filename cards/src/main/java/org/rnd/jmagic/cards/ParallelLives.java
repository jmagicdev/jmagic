package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Parallel Lives")
@Types({Type.ENCHANTMENT})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class ParallelLives extends Card
{
	public static final class ParallelLivesAbility0 extends StaticAbility
	{
		public ParallelLivesAbility0(GameState state)
		{
			super(state, "If an effect would put one or more tokens onto the battlefield under your control, it puts twice that many of those tokens onto the battlefield instead.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.CREATE_TOKEN);
			pattern.put(EventType.Parameter.CONTROLLER, You.instance());
			EventReplacementEffect replacement = new EventReplacementEffect(state.game, this.getName(), pattern);

			EventFactory factory = new EventFactory(EventType.CREATE_TOKEN, "Create twice that many tokens instead.");
			SetGenerator originalNumber = EventParameter.instance(replacement.replacedByThis(), EventType.Parameter.NUMBER);
			factory.parameters.put(EventType.Parameter.NUMBER, Multiply.instance(originalNumber, numberGenerator(2)));
			replacement.addEffect(factory);
			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public ParallelLives(GameState state)
	{
		super(state);

		// If an effect would put one or more tokens onto the battlefield under
		// your control, it puts twice that many of those tokens onto the
		// battlefield instead.
		this.addAbility(new ParallelLivesAbility0(state));
	}
}

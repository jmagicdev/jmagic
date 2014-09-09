package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Conjurer's Closet")
@Types({Type.ARTIFACT})
@ManaCost("5")
@ColorIdentity({})
public final class ConjurersCloset extends Card
{
	public static final class ConjurersClosetAbility0 extends EventTriggeredAbility
	{
		public ConjurersClosetAbility0(GameState state)
		{
			super(state, "At the beginning of your end step, you may exile target creature you control, then return that card to the battlefield under your control.");
			this.addPattern(atTheBeginningOfYourEndStep());

			SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));

			EventFactory factory = new EventFactory(BLINK, "Exile target creature you control, then return that card to the battlefield under your control.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.TARGET, target);
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(youMay(factory, "You may exile target creature you control, then return that card to the battlefield under your control."));
		}
	}

	public ConjurersCloset(GameState state)
	{
		super(state);

		// At the beginning of your end step, you may exile target creature you
		// control, then return that card to the battlefield under your control.
		this.addAbility(new ConjurersClosetAbility0(state));
	}
}

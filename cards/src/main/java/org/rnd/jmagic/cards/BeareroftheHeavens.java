package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bearer of the Heavens")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("7R")
@ColorIdentity({Color.RED})
public final class BeareroftheHeavens extends Card
{
	public static final class BeareroftheHeavensAbility0 extends EventTriggeredAbility
	{
		public BeareroftheHeavensAbility0(GameState state)
		{
			super(state, "When Bearer of the Heavens dies, destroy all permanents at the beginning of the next end step.");
			this.addPattern(whenThisDies());

			EventFactory destroy = destroy(Permanents.instance(), "Destroy all permanents.");

			EventFactory destroyLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Destroy all permanents at the beginning of the next end step.");
			destroyLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
			destroyLater.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfEachEndStep()));
			destroyLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(destroy));
			this.addEffect(destroyLater);
		}
	}

	public BeareroftheHeavens(GameState state)
	{
		super(state);

		this.setPower(10);
		this.setToughness(10);

		// When Bearer of the Heavens dies, destroy all permanents at the
		// beginning of the next end step.
		this.addAbility(new BeareroftheHeavensAbility0(state));
	}
}

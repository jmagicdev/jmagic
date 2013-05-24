package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Resounding Wave")
@Types({Type.INSTANT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.BLUE, Color.BLACK})
public final class ResoundingWave extends Card
{
	public static final class ResoundingTrigger extends EventTriggeredAbility
	{
		public ResoundingTrigger(GameState state)
		{
			super(state, "When you cycle Resounding Wave, return two target permanents to their owners' hands.");

			this.canTrigger = NonEmpty.instance();
			this.addPattern(whenYouCycleThis());

			Target target = this.addTarget(Permanents.instance(), "two target permanents");
			target.setNumber(2, 2);
			this.addEffect(bounce(targetedBy(target), "Return two target permanents to their owners' hands."));
		}
	}

	public ResoundingWave(GameState state)
	{
		super(state);

		// Return target permanent to its owner's hand.
		Target target = this.addTarget(Permanents.instance(), "target permanent");
		this.addEffect(bounce(targetedBy(target), "Return target permanent to its owner's hand."));

		// Cycling (5)(W)(U)(B)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cycling(state, "(5)(W)(U)(B)"));

		// When you cycle Resounding Wave, return two target permanents to their
		// owners' hands.
		this.addAbility(new ResoundingTrigger(state));
	}
}

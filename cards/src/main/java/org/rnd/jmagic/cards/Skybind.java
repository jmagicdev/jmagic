package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Skybind")
@Types({Type.ENCHANTMENT})
@ManaCost("3WW")
@ColorIdentity({Color.WHITE})
public final class Skybind extends Card
{
	public static final class SkybindAbility0 extends EventTriggeredAbility
	{
		public SkybindAbility0(GameState state)
		{
			super(state, "Constellation \u2014 Whenever Skybind or another enchantment enters the battlefield under your control, exile target nonenchantment permanent. Return that card to the battlefield under its owner's control at the beginning of the next end step.");
			this.addPattern(constellation());

			SetGenerator nonenchantments = RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.ENCHANTMENT));
			SetGenerator target = targetedBy(this.addTarget(nonenchantments, "target nonenchantment permanent"));

			EventFactory slide = new EventFactory(SLIDE, "Exile target nonenchantment permanent. Return that card to the battlefield under its owner's control at the beginning of the next end step.");
			slide.parameters.put(EventType.Parameter.CAUSE, This.instance());
			slide.parameters.put(EventType.Parameter.TARGET, target);
			this.addEffect(slide);
		}
	}

	public Skybind(GameState state)
	{
		super(state);

		// Constellation \u2014 Whenever Skybind or another enchantment enters
		// the battlefield under your control, exile target nonenchantment
		// permanent. Return that card to the battlefield under its owner's
		// control at the beginning of the next end step.
		this.addAbility(new SkybindAbility0(state));
	}
}

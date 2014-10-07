package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Harvestguard Alseids")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.NYMPH})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class HarvestguardAlseids extends Card
{
	public static final class HarvestguardAlseidsAbility0 extends EventTriggeredAbility
	{
		public HarvestguardAlseidsAbility0(GameState state)
		{
			super(state, "Constellation \u2014 Whenever Harvestguard Alseids or another enchantment enters the battlefield under your control, prevent all damage that would be dealt to target creature this turn.");
			this.addPattern(constellation());

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REPLACEMENT_EFFECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(new org.rnd.jmagic.cards.ShieldedPassage.Prevent(state.game, "Prevent all damage that would be dealt to target creature", target)));
			this.addEffect(createFloatingEffect("Prevent all damage that would be dealt to target creature this turn.", part));
		}
	}

	public HarvestguardAlseids(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Constellation \u2014 Whenever Harvestguard Alseids or another
		// enchantment enters the battlefield under your control, prevent all
		// damage that would be dealt to target creature this turn.
		this.addAbility(new HarvestguardAlseidsAbility0(state));
	}
}

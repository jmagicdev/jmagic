package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Triton Cavalry")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.MERFOLK})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class TritonCavalry extends Card
{
	public static final class TritonCavalryAbility0 extends EventTriggeredAbility
	{
		public TritonCavalryAbility0(GameState state)
		{
			super(state, "Heroic \u2014 Whenever you cast a spell that targets Triton Cavalry, you may return target enchantment to its owner's hand.");
			this.addPattern(heroic());
			SetGenerator target = targetedBy(this.addTarget(EnchantmentPermanents.instance(), "target enchantment"));
			this.addEffect(youMay(bounce(target, "Target enchantment to its owner's hand.")));
		}
	}

	public TritonCavalry(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Heroic \u2014 Whenever you cast a spell that targets Triton Cavalry,
		// you may return target enchantment to its owner's hand.
		this.addAbility(new TritonCavalryAbility0(state));
	}
}

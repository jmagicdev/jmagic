package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Lotus Bloom")
@Types({Type.ARTIFACT})
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.RARE)})
@ColorIdentity({})
public final class LotusBloom extends Card
{
	public static final class LotusMana extends ActivatedAbility
	{
		public LotusMana(GameState state)
		{
			super(state, "(T), Sacrifice Lotus Bloom: Add three mana of any one color to your mana pool.");
			this.costsTap = true;
			this.addCost(sacrificeThis("Lotus Bloom"));

			EventFactory effect = addManaToYourManaPoolFromAbility("(WUBRG)", "Add three mana of any one color to your mana pool");
			effect.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
			this.addEffect(effect);
		}
	}

	public LotusBloom(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Suspend(state, 3, "(0)"));

		this.addAbility(new LotusMana(state));
	}
}

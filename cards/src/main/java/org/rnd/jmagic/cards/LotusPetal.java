package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Lotus Petal")
@Types({Type.ARTIFACT})
@ManaCost("0")
@ColorIdentity({})
public final class LotusPetal extends Card
{
	public static final class LotusPetalAbility0 extends ActivatedAbility
	{
		public LotusPetalAbility0(GameState state)
		{
			super(state, "(T), Sacrifice Lotus Petal: Add one mana of any color to your mana pool.");
			this.costsTap = true;
			this.addCost(sacrificeThis("Lotus Petal"));
			this.addEffect(addManaToYourManaPoolFromAbility("(WUBRG)"));
		}
	}

	public LotusPetal(GameState state)
	{
		super(state);

		// (T), Sacrifice Lotus Petal: Add one mana of any color to your mana
		// pool.
		this.addAbility(new LotusPetalAbility0(state));
	}
}

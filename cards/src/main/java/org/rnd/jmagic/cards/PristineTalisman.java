package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pristine Talisman")
@Types({Type.ARTIFACT})
@ManaCost("3")
@ColorIdentity({})
public final class PristineTalisman extends Card
{
	public static final class PristineTalismanAbility0 extends ActivatedAbility
	{
		public PristineTalismanAbility0(GameState state)
		{
			super(state, "(T): Add (1) to your mana pool. You gain 1 life.");
			this.costsTap = true;
			this.addEffect(addManaToYourManaPoolFromAbility("(1)"));
			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public PristineTalisman(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool. You gain 1 life.
		this.addAbility(new PristineTalismanAbility0(state));
	}
}

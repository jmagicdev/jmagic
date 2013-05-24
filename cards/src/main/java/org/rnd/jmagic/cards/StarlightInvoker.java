package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Starlight Invoker")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.MUTANT, SubType.CLERIC})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.LEGIONS, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class StarlightInvoker extends Card
{
	public static final class Invoke extends ActivatedAbility
	{
		public Invoke(GameState state)
		{
			super(state, "(7)(W): You gain 5 life.");

			this.setManaCost(new ManaPool("7W"));

			this.addEffect(gainLife(You.instance(), 5, "You gain 5 life."));
		}
	}

	public StarlightInvoker(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		this.addAbility(new Invoke(state));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Burning-Tree Emissary")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.HUMAN})
@ManaCost("(R/G)(R/G)")
@ColorIdentity({Color.GREEN, Color.RED})
public final class BurningTreeEmissary extends Card
{
	public static final class BurningTreeEmissaryAbility0 extends EventTriggeredAbility
	{
		public BurningTreeEmissaryAbility0(GameState state)
		{
			super(state, "When Burning-Tree Emissary enters the battlefield, add (R)(G) to your mana pool.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(addManaToYourManaPoolFromAbility("(R)(G)"));
		}
	}

	public BurningTreeEmissary(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Burning-Tree Emissary enters the battlefield, add (R)(G) to your
		// mana pool.
		this.addAbility(new BurningTreeEmissaryAbility0(state));
	}
}

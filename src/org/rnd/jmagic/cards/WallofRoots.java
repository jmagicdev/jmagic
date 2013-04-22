package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Wall of Roots")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL, SubType.PLANT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.SPECIAL), @Printings.Printed(ex = Expansion.MIRAGE, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class WallofRoots extends Card
{
	public static final class WallofRootsAbility1 extends ActivatedAbility
	{
		public WallofRootsAbility1(GameState state)
		{
			super(state, "Put a -0/-1 counter on Wall of Roots: Add (G) to your mana pool. Activate this ability only once each turn.");
			this.addCost(putCounters(1, Counter.CounterType.MINUS_ZERO_MINUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a -0/-1 counter on Wall of Roots"));
			this.addEffect(addManaToYourManaPoolFromAbility("(G)"));
			this.perTurnLimit(1);
		}
	}

	public WallofRoots(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(5);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// Put a -0/-1 counter on Wall of Roots: Add (G) to your mana pool.
		// Activate this ability only once each turn.
		this.addAbility(new WallofRootsAbility1(state));
	}
}

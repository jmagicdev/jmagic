package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Blight Mamba")
@Types({Type.CREATURE})
@SubTypes({SubType.SNAKE})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class BlightMamba extends Card
{
	public static final class BlightMambaAbility1 extends ActivatedAbility
	{
		public BlightMambaAbility1(GameState state)
		{
			super(state, "(1)(G): Regenerate Blight Mamba.");
			this.setManaCost(new ManaPool("(1)(G)"));
			this.addEffect(regenerate(ABILITY_SOURCE_OF_THIS, "Regenerate Blight Mamba."));
		}
	}

	public BlightMamba(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// (1)(G): Regenerate Blight Mamba.
		this.addAbility(new BlightMambaAbility1(state));
	}
}

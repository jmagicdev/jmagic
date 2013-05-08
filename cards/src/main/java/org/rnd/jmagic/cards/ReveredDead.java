package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Revered Dead")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT, SubType.SOLDIER})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.PLANAR_CHAOS, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class ReveredDead extends Card
{
	public static final class ReveredDeadAbility0 extends ActivatedAbility
	{
		public ReveredDeadAbility0(GameState state)
		{
			super(state, "(W): Regenerate Revered Dead.");

			this.setManaCost(new ManaPool("(W)"));

			this.addEffect(regenerate(ABILITY_SOURCE_OF_THIS, "Regenerate Revered Dead."));
		}
	}

	public ReveredDead(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (W): Regenerate Revered Dead.
		this.addAbility(new ReveredDeadAbility0(state));
	}
}

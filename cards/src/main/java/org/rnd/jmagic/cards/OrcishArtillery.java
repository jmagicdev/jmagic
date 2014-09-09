package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Orcish Artillery")
@Types({Type.CREATURE})
@SubTypes({SubType.ORC, SubType.WARRIOR})
@ManaCost("1RR")
@ColorIdentity({Color.RED})
public final class OrcishArtillery extends Card
{
	public static final class Strike extends ActivatedAbility
	{
		public Strike(GameState state)
		{
			super(state, "(T): Orcish Artillery deals 2 damage to target creature or player and 3 damage to you.");

			this.costsTap = true;

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

			// TODO : Simultaneous etc etc
			this.addEffect(permanentDealDamage(2, targetedBy(target), "Orcish Artillery deals 2 damage to target creature or player"));
			this.addEffect(permanentDealDamage(3, You.instance(), "and 3 damage to you."));
		}
	}

	public OrcishArtillery(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		this.addAbility(new Strike(state));
	}
}

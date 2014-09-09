package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scalding Devil")
@Types({Type.CREATURE})
@SubTypes({SubType.DEVIL})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class ScaldingDevil extends Card
{
	public static final class ScaldingDevilAbility0 extends ActivatedAbility
	{
		public ScaldingDevilAbility0(GameState state)
		{
			super(state, "(2)(R): Scalding Devil deals 1 damage to target player.");
			this.setManaCost(new ManaPool("(2)(R)"));

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(permanentDealDamage(1, target, "Scalding Devil deals 1 damage to target player."));
		}
	}

	public ScaldingDevil(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (2)(R): Scalding Devil deals 1 damage to target player.
		this.addAbility(new ScaldingDevilAbility0(state));
	}
}

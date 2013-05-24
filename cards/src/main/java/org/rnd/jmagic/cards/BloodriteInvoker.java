package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bloodrite Invoker")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE, SubType.SHAMAN})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class BloodriteInvoker extends Card
{
	public static final class BloodriteInvokerAbility0 extends ActivatedAbility
	{
		public BloodriteInvokerAbility0(GameState state)
		{
			super(state, "(8): Target player loses 3 life and you gain 3 life.");

			this.setManaCost(new ManaPool("(8)"));

			Target target = this.addTarget(Players.instance(), "target player");

			this.addEffect(loseLife(targetedBy(target), 3, "Target player loses 3 life"));

			this.addEffect(gainLife(You.instance(), 3, "and you gain 3 life."));
		}
	}

	public BloodriteInvoker(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// (8): Target player loses 3 life and you gain 3 life.
		this.addAbility(new BloodriteInvokerAbility0(state));
	}
}

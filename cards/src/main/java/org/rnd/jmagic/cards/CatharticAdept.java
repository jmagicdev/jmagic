package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Cathartic Adept")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("U")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class CatharticAdept extends Card
{
	public static final class ShortTermMemoryLoss extends ActivatedAbility
	{
		public ShortTermMemoryLoss(GameState state)
		{
			super(state, "(T): Target player puts the top card of his or her library into his or her graveyard.");

			this.costsTap = true;

			Target target = this.addTarget(Players.instance(), "target player");

			this.addEffect(millCards(targetedBy(target), 1, "Target player puts the top card of his or her library into his or her graveyard."));
		}
	}

	public CatharticAdept(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new ShortTermMemoryLoss(state));
	}
}

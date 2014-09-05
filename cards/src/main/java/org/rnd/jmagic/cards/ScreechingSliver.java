package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Screeching Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("U")
@Printings({@Printings.Printed(ex = TimeSpiral.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class ScreechingSliver extends Card
{
	@Name("\"(T): Target player puts the top card of his or her library into his or her graveyard.\"")
	public static final class SliverMill extends ActivatedAbility
	{
		public SliverMill(GameState state)
		{
			super(state, "(T): Target player puts the top card of his or her library into his or her graveyard.");

			this.costsTap = true;

			Target target = this.addTarget(Players.instance(), "target player");

			this.addEffect(millCards(targetedBy(target), 1, "Target player puts the top card of his or her library into his or her graveyard."));
		}
	}

	public ScreechingSliver(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// All Slivers have
		// "(T): Target player puts the top card of his or her library into his or her graveyard."
		this.addAbility(new org.rnd.jmagic.abilities.AllSliversHave(state, SliverMill.class, "All Slivers have \"(T): Target player puts the top card of his or her library into his or her graveyard.\""));
	}
}

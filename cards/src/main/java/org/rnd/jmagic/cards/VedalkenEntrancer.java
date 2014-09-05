package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Vedalken Entrancer")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.VEDALKEN})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class VedalkenEntrancer extends Card
{
	public static final class VedalkenEntrancerAbility0 extends ActivatedAbility
	{
		public VedalkenEntrancerAbility0(GameState state)
		{
			super(state, "(U), (T): Target player puts the top two cards of his or her library into his or her graveyard.");
			this.setManaCost(new ManaPool("(U)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(millCards(target, 2, "Target player puts the top two cards of his or her library into his or her graveyard."));
		}
	}

	public VedalkenEntrancer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		// (U), (T): Target player puts the top two cards of his or her library
		// into his or her graveyard.
		this.addAbility(new VedalkenEntrancerAbility0(state));
	}
}

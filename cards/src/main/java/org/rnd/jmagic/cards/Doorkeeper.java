package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Doorkeeper")
@Types({Type.CREATURE})
@SubTypes({SubType.HOMUNCULUS})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Doorkeeper extends Card
{
	public static final class DoorkeeperAbility1 extends ActivatedAbility
	{
		public DoorkeeperAbility1(GameState state)
		{
			super(state, "(2)(U), (T): Target player puts the top X cards of his or her library into his or her graveyard, where X is the number of creatures with defender you control.");
			this.setManaCost(new ManaPool("(2)(U)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

			SetGenerator X = Count.instance(Intersect.instance(HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Defender.class), CREATURES_YOU_CONTROL));
			this.addEffect(millCards(target, X, "Target player puts the top X cards of his or her library into his or her graveyard, where X is the number of creatures with defender you control."));
		}
	}

	public Doorkeeper(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(4);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// (2)(U), (T): Target player puts the top X cards of his or her library
		// into his or her graveyard, where X is the number of creatures with
		// defender you control.
		this.addAbility(new DoorkeeperAbility1(state));
	}
}

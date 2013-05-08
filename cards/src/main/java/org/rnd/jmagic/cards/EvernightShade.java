package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Evernight Shade")
@Types({Type.CREATURE})
@SubTypes({SubType.SHADE})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class EvernightShade extends Card
{
	public EvernightShade(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (B): Evernight Shade gets +1/+1 until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.ShadePump(state, this.getName()));

		// Undying (When this creature dies, if it had no +1/+1 counters on it,
		// return it to the battlefield under its owner's control with a +1/+1
		// counter on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Undying(state));
	}
}

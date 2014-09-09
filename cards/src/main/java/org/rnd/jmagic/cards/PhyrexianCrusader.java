package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Phyrexian Crusader")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.KNIGHT})
@ManaCost("1BB")
@ColorIdentity({Color.BLACK})
public final class PhyrexianCrusader extends Card
{
	public PhyrexianCrusader(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// First strike, protection from red and from white
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.From(state, HasColor.instance(Color.RED, Color.WHITE), "red and from white"));

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));
	}
}

package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Zhur-Taa Swine")
@Types({Type.CREATURE})
@SubTypes({SubType.BOAR})
@ManaCost("3RG")
@ColorIdentity({Color.RED, Color.GREEN})
public final class ZhurTaaSwine extends Card
{
	public ZhurTaaSwine(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Bloodrush \u2014 (1)(R)(G), Discard Zhur-Taa Swine: Target attacking
		// creature gets +5/+4 until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.Bloodrush(state, "(1)(R)(G)", "Zhur-Taa Swine", +5, +4, "Target attacking creature gets +5/+4 until end of turn."));
	}
}

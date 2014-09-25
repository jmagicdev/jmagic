package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Disowned Ancestor")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT, SubType.WARRIOR})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class DisownedAncestor extends Card
{
	public DisownedAncestor(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(4);

		// Outlast (1)(B) ((1)(B), (T): Put a +1/+1 counter on this creature.
		// Outlast only as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Outlast(state, "(1)(B)"));
	}
}

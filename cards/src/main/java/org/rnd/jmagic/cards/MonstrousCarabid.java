package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Monstrous Carabid")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("3BR")
@ColorIdentity({Color.BLACK, Color.RED})
public final class MonstrousCarabid extends Card
{
	public MonstrousCarabid(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Monstrous Carabid attacks each turn if able.
		this.addAbility(new org.rnd.jmagic.abilities.AttacksEachTurnIfAble(state, this.getName()));

		// Cycling ((b/r)) (((b/r)), Discard this card: Draw a card.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cycling(state, "(BR)"));
	}
}

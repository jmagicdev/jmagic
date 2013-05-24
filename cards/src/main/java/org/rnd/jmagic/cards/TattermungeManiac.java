package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Tattermunge Maniac")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.WARRIOR})
@ManaCost("(R/G)")
@Printings({@Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class TattermungeManiac extends Card
{
	public TattermungeManiac(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Tattermunge Maniac attacks each turn if able.
		this.addAbility(new org.rnd.jmagic.abilities.AttacksEachTurnIfAble(state, this.getName()));
	}
}

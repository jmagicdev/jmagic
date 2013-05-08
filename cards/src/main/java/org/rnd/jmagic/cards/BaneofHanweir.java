package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Bane of Hanweir")
@Types({Type.CREATURE})
@SubTypes({SubType.WEREWOLF})
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class BaneofHanweir extends AlternateCard
{
	public BaneofHanweir(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		this.setColorIndicator(Color.RED);

		// Bane of Hanweir attacks each turn if able.
		this.addAbility(new org.rnd.jmagic.abilities.AttacksEachTurnIfAble(state, "Bane of Hanweir"));

		// At the beginning of each upkeep, if a player cast two or more spells
		// last turn, transform Bane of Hanweir.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeHuman(state, "Bane of Hanweir"));
	}
}

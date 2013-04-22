package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Thran Dynamo")
@Types({Type.ARTIFACT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.URZAS_DESTINY, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class ThranDynamo extends Card
{
	public ThranDynamo(GameState state)
	{
		super(state);

		// (T): Add (3) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(3)"));
	}
}

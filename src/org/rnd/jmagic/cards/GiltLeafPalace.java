package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Gilt-Leaf Palace")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class GiltLeafPalace extends Card
{
	public GiltLeafPalace(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.RevealOrThisEntersTapped(state, this.getName(), SubType.ELF));
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(BG)"));
	}
}

package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Mist Leopard")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class MistLeopard extends Card
{
	public MistLeopard(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Shroud(state));
	}
}

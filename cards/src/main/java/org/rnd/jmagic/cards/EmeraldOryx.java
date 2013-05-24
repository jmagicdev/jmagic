package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Emerald Oryx")
@Types({Type.CREATURE})
@SubTypes({SubType.ANTELOPE})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class EmeraldOryx extends Card
{
	public EmeraldOryx(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Forestwalk(state));
	}
}

package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Child of Night")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class ChildofNight extends Card
{
	public ChildofNight(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));
	}
}

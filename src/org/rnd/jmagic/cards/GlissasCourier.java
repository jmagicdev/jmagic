package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Glissa's Courier")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("1GG")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class GlissasCourier extends Card
{
	public static final class GlissasCourierAbility0 extends StaticAbility
	{
		public GlissasCourierAbility0(GameState state)
		{
			super(state, "Mountainwalk");
		}
	}

	public GlissasCourier(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Mountainwalk
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Mountainwalk(state));
	}
}

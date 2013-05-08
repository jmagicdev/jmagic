package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Nightwing Shade")
@Types({Type.CREATURE})
@SubTypes({SubType.SHADE})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class NightwingShade extends Card
{
	public static final class NightwingShadeAbility1 extends ActivatedAbility
	{
		public NightwingShadeAbility1(GameState state)
		{
			super(state, "(1)(B): Nightwing Shade gets +1/+1 until end of turn.");
			this.setManaCost(new ManaPool("(1)(B)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, (+1), (+1), "Crypt Ripper gets +1/+1 until end of turn."));
		}
	}

	public NightwingShade(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (1)(B): Nightwing Shade gets +1/+1 until end of turn.
		this.addAbility(new NightwingShadeAbility1(state));
	}
}

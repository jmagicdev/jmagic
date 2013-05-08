package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chameleon Colossus")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAPESHIFTER})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = Expansion.MORNINGTIDE, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class ChameleonColossus extends Card
{
	public static final class ChameleonColossusAbility2 extends ActivatedAbility
	{
		public ChameleonColossusAbility2(GameState state)
		{
			super(state, "(2)(G)(G): Chameleon Colossus gets +X/+X until end of turn, where X is its power.");
			this.setManaCost(new ManaPool("(2)(G)(G)"));
			SetGenerator X = PowerOf.instance(ABILITY_SOURCE_OF_THIS);
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, X, X, "Chameleon Colossus gets +X/+X until end of turn, where X is its power."));
		}
	}

	public ChameleonColossus(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Changeling (This card is every creature type at all times.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Changeling(state));

		// Protection from black
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.FromBlack(state));

		// (2)(G)(G): Chameleon Colossus gets +X/+X until end of turn, where X
		// is its power.
		this.addAbility(new ChameleonColossusAbility2(state));
	}
}

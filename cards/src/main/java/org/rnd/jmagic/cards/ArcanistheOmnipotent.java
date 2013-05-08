package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Arcanis the Omnipotent")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD})
@ManaCost("3UUU")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class ArcanistheOmnipotent extends Card
{
	public static final class TapDrawThree extends ActivatedAbility
	{
		public TapDrawThree(GameState state)
		{
			super(state, "(T): Draw three cards.");
			this.costsTap = true;

			this.addEffect(drawCards(You.instance(), 3, "Draw three cards."));
		}
	}

	public static final class BounceMe extends ActivatedAbility
	{
		public BounceMe(GameState state)
		{
			super(state, "2UU: Return Arcanis the Omnipotent to its owner's hand.");
			this.setManaCost(new ManaPool("2UU"));

			this.addEffect(bounce(ABILITY_SOURCE_OF_THIS, "Return Arcanis the Omnipotent to its owner's hand."));
		}
	}

	public ArcanistheOmnipotent(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		this.addAbility(new TapDrawThree(state));
		this.addAbility(new BounceMe(state));
	}
}

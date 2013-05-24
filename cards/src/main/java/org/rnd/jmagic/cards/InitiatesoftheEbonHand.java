package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Initiates of the Ebon Hand")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.FALLEN_EMPIRES, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class InitiatesoftheEbonHand extends Card
{
	public static final class InitiatesoftheEbonHandAbility0 extends ActivatedAbility
	{
		public InitiatesoftheEbonHandAbility0(GameState state)
		{
			super(state, "(1): Add (B) to your mana pool. If this ability has been activated four or more times this turn, sacrifice Initiates of the Ebon Hand at the beginning of the next end step.");
			this.setManaCost(new ManaPool("(1)"));

			this.addEffect(addManaToYourManaPoolFromAbility("(B)"));
			this.addEffect(ifActivatedNOrMoreTimesSacrifice(state, 4, "Initiates of the Ebon Hand"));
		}
	}

	public InitiatesoftheEbonHand(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (1): Add (B) to your mana pool. If this ability has been activated
		// four or more times this turn, sacrifice Initiates of the Ebon Hand at
		// the beginning of the next end step.
		this.addAbility(new InitiatesoftheEbonHandAbility0(state));
	}
}

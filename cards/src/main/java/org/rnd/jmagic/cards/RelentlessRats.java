package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Relentless Rats")
@Types({Type.CREATURE})
@SubTypes({SubType.RAT})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.FIFTH_DAWN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class RelentlessRats extends Card implements AnyNumberInADeck
{
	/**
	 * This exists only to provide text for the card. RelentlessRats
	 * implementing AnyNumberInADeck provides the actual functionality for
	 * deck-checks.
	 */
	public static final class AnyNumber extends StaticAbility
	{
		public AnyNumber(GameState state)
		{
			super(state, "A deck can have any number of cards named Relentless Rats.");
		}
	}

	public static final class Boost extends StaticAbility
	{
		public Boost(GameState state)
		{
			super(state, "Relentless Rats gets +1/+1 for each other creature on the battlefield named Relentless Rats.");

			SetGenerator thisCard = This.instance();
			SetGenerator rats = Intersect.instance(CreaturePermanents.instance(), HasName.instance("Relentless Rats"));
			SetGenerator boostAmount = Count.instance(RelativeComplement.instance(rats, thisCard));

			this.addEffectPart(modifyPowerAndToughness(thisCard, boostAmount, boostAmount));
		}
	}

	public RelentlessRats(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new Boost(state));
		this.addAbility(new AnyNumber(state));
	}
}

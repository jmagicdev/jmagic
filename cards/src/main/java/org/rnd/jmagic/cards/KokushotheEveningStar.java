package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kokusho, the Evening Star")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT, SubType.DRAGON})
@ManaCost("4BB")
@Printings({@Printings.Printed(ex = Expansion.DRAGONS, r = Rarity.RARE), @Printings.Printed(ex = Expansion.CHAMPIONS_OF_KAMIGAWA, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class KokushotheEveningStar extends Card
{
	public static final class KokushotheEveningStarAbility1 extends EventTriggeredAbility
	{
		public KokushotheEveningStarAbility1(GameState state)
		{
			super(state, "When Kokusho, the Evening Star dies, each opponent loses 5 life. You gain life equal to the life lost this way.");
			this.addPattern(whenThisDies());

			EventFactory lose = loseLife(OpponentsOf.instance(You.instance()), 5, "Each opponent loses 5 life.");
			this.addEffect(lose);

			this.addEffect(gainLife(You.instance(), EffectResult.instance(lose), "You gain life equal to the life lost this way."));
		}
	}

	public KokushotheEveningStar(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Kokusho, the Evening Star is put into a graveyard from the
		// battlefield, each opponent loses 5 life. You gain life equal to the
		// life lost this way.
		this.addAbility(new KokushotheEveningStarAbility1(state));
	}
}

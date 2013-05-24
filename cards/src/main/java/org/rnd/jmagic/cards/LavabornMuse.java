package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lavaborn Muse")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.LEGIONS, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class LavabornMuse extends Card
{
	public static final class UpkeepTrigger extends EventTriggeredAbility
	{
		public UpkeepTrigger(GameState state)
		{
			super(state, "At the beginning of each opponent's upkeep, if that player has two or fewer cards in hand, Lavaborn Muse deals 3 damage to him or her.");

			this.addPattern(atTheBeginningOfEachOpponentsUpkeeps());

			SetGenerator activePlayer = OwnerOf.instance(CurrentStep.instance());
			SetGenerator cardsInHand = Count.instance(InZone.instance(HandOf.instance(activePlayer)));
			SetGenerator twoOrFewer = Identity.instance(new org.rnd.util.NumberRange(0, 2));
			this.interveningIf = Intersect.instance(cardsInHand, twoOrFewer);

			this.addEffect(permanentDealDamage(3, activePlayer, "Lavaborn Muse deals 3 damage to him or her."));
		}
	}

	public LavabornMuse(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new UpkeepTrigger(state));
	}
}

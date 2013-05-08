package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sygg, River Cutthroat")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.MERFOLK})
@ManaCost("(U/B)(U/B)")
@Printings({@Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class SyggRiverCutthroat extends Card
{
	public static final class SyggRiverCutthroatAbility0 extends EventTriggeredAbility
	{
		public SyggRiverCutthroatAbility0(GameState state)
		{
			super(state, "At the beginning of each end step, if an opponent lost 3 or more life this turn, you may draw a card.");
			this.addPattern(atTheBeginningOfEachEndStep());

			state.ensureTracker(new LifeLostThisTurn.LifeTracker());
			SetGenerator lifeLost = LifeLostThisTurn.instance(OpponentsOf.instance(You.instance()));
			this.interveningIf = Intersect.instance(lifeLost, Between.instance(3, null));

			EventFactory draw = drawCards(You.instance(), 1, "Draw a card");
			this.addEffect(youMay(draw, "You may draw a card."));
		}
	}

	public SyggRiverCutthroat(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// At the beginning of each end step, if an opponent lost 3 or more life
		// this turn, you may draw a card. (Damage causes loss of life.)
		this.addAbility(new SyggRiverCutthroatAbility0(state));
	}
}

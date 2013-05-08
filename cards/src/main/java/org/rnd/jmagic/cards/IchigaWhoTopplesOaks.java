package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ichiga, Who Topples Oaks")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@Flipped(BudokaPupil.class)
@ColorIdentity({})
public final class IchigaWhoTopplesOaks extends FlipBottomHalf
{
	public static final class BudokaPupilAbility7 extends ActivatedAbility
	{
		public BudokaPupilAbility7(GameState state)
		{
			super(state, "Remove a ki counter from Ichiga, Who Topples Oaks: Target creature gets +2/+2 until end of turn.");

			this.addCost(removeCountersFromThis(1, Counter.CounterType.KI, "Ichiga, Who Topples Oaks"));

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), 2, 2, "Target creature gets +2/+2 until end of turn."));
		}
	}

	public IchigaWhoTopplesOaks(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		this.addAbility(new BudokaPupilAbility7(state));
	}
}
package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sin Collector")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.CLERIC})
@ManaCost("1WB")
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class SinCollector extends Card
{
	public static final class SinCollectorAbility0 extends EventTriggeredAbility
	{
		public SinCollectorAbility0(GameState state)
		{
			super(state, "When Sin Collector enters the battlefield, target opponent reveals his or her hand. You choose an instant or sorcery card from it and exile that card.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
			SetGenerator hand = HandOf.instance(target);
			this.addEffect(reveal(hand, "Target opponent reveals his or her hand."));

			SetGenerator choices = Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), InZone.instance(hand));
			this.addEffect(exile(You.instance(), choices, 1, "You choose an instant or sorcery card from it and exile that card."));

		}
	}

	public SinCollector(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// When Sin Collector enters the battlefield, target opponent reveals
		// his or her hand. You choose an instant or sorcery card from it and
		// exile that card.
		this.addAbility(new SinCollectorAbility0(state));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Brain Maggot")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class BrainMaggot extends Card
{
	public static final class BrainMaggotAbility0 extends EventTriggeredAbility
	{
		public BrainMaggotAbility0(GameState state)
		{
			super(state, "When Brain Maggot enters the battlefield, target opponent reveals his or her hand and you choose a nonland card from it. Exile that card until Brain Maggot leaves the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
			SetGenerator hand = InZone.instance(HandOf.instance(target));
			this.addEffect(reveal(hand, "Target opponent reveals his or her hand"));

			SetGenerator nonland = RelativeComplement.instance(hand, HasType.instance(Type.LAND));
			EventFactory choose = playerChoose(You.instance(), 1, nonland, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.EXILE, "and you choose a nonland card from it.");
			this.addEffect(choose);

			SetGenerator thatCard = EffectResult.instance(choose);
			this.addEffect(exileUntilThisLeavesTheBattlefield(state, thatCard, "Exile that card until Brain Maggot leaves the battlefield."));
		}
	}

	public BrainMaggot(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Brain Maggot enters the battlefield, target opponent reveals his
		// or her hand and you choose a nonland card from it. Exile that card
		// until Brain Maggot leaves the battlefield.
		this.addAbility(new BrainMaggotAbility0(state));
	}
}

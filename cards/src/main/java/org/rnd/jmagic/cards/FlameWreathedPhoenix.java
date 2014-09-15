package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flame-Wreathed Phoenix")
@Types({Type.CREATURE})
@SubTypes({SubType.PHOENIX})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class FlameWreathedPhoenix extends Card
{
	public static final class ReturnToLife extends EventTriggeredAbility
	{
		public ReturnToLife(GameState state)
		{
			super(state, "When this creature dies, return it to its owner's hand.");
			this.addPattern(whenThisDies());
			this.addEffect(putIntoHand(ABILITY_SOURCE_OF_THIS, You.instance(), "Return Flame-Wreathed Phoenix to its owner's hand."));
		}
	}

	public static final class FlameWreathedPhoenixAbility2 extends EventTriggeredAbility
	{
		public FlameWreathedPhoenixAbility2(GameState state)
		{
			super(state, "When Flame-Wreathed Phoenix enters the battlefield, if tribute wasn't paid, it gains haste and \"When this creature dies, return it to its owner's hand.\"");
			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = org.rnd.jmagic.abilities.keywords.Tribute.WasntPaid.instance();

			ContinuousEffect.Part abilities = addAbilityToObject(ABILITY_SOURCE_OF_THIS,//
					org.rnd.jmagic.abilities.keywords.Haste.class,//
					ReturnToLife.class);
			this.addEffect(createFloatingEffect("Flame-Wreathed Phoenix gains haste and \"When this creature dies, return it to its owner's hand.\"", abilities));
		}
	}

	public FlameWreathedPhoenix(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Tribute 2 (As this creature enters the battlefield, an opponent of
		// your choice may place two +1/+1 counters on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Tribute(state, 2));

		// When Flame-Wreathed Phoenix enters the battlefield, if tribute wasn't
		// paid, it gains haste and
		// "When this creature dies, return it to its owner's hand."
		this.addAbility(new FlameWreathedPhoenixAbility2(state));
	}
}

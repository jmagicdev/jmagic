package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Arm with \u00C6ther")
@Types({Type.SORCERY})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class ArmwithAEther extends Card
{
	public static final class AEther extends EventTriggeredAbility
	{
		public AEther(GameState state)
		{
			super(state, "Whenever this creature deals damage to an opponent, you may return target creature that player controls to its owner's hand.");

			this.addPattern(whenDealsDamageToAnOpponent(ABILITY_SOURCE_OF_THIS));

			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(thatPlayer)), "target creature that player controls"));

			this.addEffect(youMay(bounce(target, "Return target creature that player controls to its owner's hand."), "You may return target creature that player controls to its owner's hand."));
		}
	}

	public ArmwithAEther(GameState state)
	{
		super(state);

		// Until end of turn, creatures you control gain
		// "Whenever this creature deals damage to an opponent, you may return
		// target creature that player controls to its owner's hand."
		this.addEffect(addAbilityUntilEndOfTurn(CREATURES_YOU_CONTROL, AEther.class, "Until end of turn, creatures you control gain \"Whenever this creature deals damage to an opponent, you may return target creature that player controls to its owner's hand.\""));
	}
}

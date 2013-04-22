package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Burn at the Stake")
@Types({Type.SORCERY})
@ManaCost("2RRR")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class BurnattheStake extends Card
{
	public BurnattheStake(GameState state)
	{
		super(state);

		// As an additional cost to cast Burn at the Stake, tap any number of
		// untapped creatures you control.
		EventFactory tapAnyNumber = new EventFactory(EventType.TAP_CHOICE, "tap any number of untapped creatures you control");
		tapAnyNumber.parameters.put(EventType.Parameter.CAUSE, This.instance());
		tapAnyNumber.parameters.put(EventType.Parameter.PLAYER, You.instance());
		tapAnyNumber.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, null));
		tapAnyNumber.parameters.put(EventType.Parameter.CHOICE, CREATURES_YOU_CONTROL);
		this.addCost(tapAnyNumber);

		// Burn at the Stake deals damage to target creature or player equal to
		// three times the number of creatures tapped this way.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));

		this.addEffect(spellDealDamage(Multiply.instance(numberGenerator(3), Count.instance(CostResult.instance(tapAnyNumber))), target, "Burn at the Stake deals damage to target creature or player equal to three times the number of creatures tapped this way."));
	}
}

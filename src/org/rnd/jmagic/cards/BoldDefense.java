package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bold Defense")
@Types({Type.INSTANT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class BoldDefense extends Card
{
	public BoldDefense(GameState state)
	{
		super(state);

		// Kicker (3)(W) (You may pay an additional (3)(W) as you cast this
		// spell.)
		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "3W");
		this.addAbility(ability);
		CostCollection kickerCost = ability.costCollections[0];

		// Creatures you control get +1/+1 until end of turn. If Bold Defense
		// was kicked, instead creatures you control get +2/+2 and gain first
		// strike until end of turn.
		SetGenerator creaturesYouControl = Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.CREATURE));

		ContinuousEffect.Part littlePump = modifyPowerAndToughness(creaturesYouControl, +1, +1);
		ContinuousEffect.Part bigPump = modifyPowerAndToughness(creaturesYouControl, +2, +2);
		ContinuousEffect.Part firstStrike = addAbilityToObject(creaturesYouControl, org.rnd.jmagic.abilities.keywords.FirstStrike.class);
		SetGenerator effectParts = IfThenElse.instance(ThisSpellWasKicked.instance(kickerCost), Identity.instance(bigPump, firstStrike), Identity.instance(littlePump));

		EventFactory pump = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "Creatures you control get +1/+1 until end of turn. If Bold Defense was kicked, instead creatures you control get +2/+2 and gain first strike until end of turn.");
		pump.parameters.put(EventType.Parameter.CAUSE, This.instance());
		pump.parameters.put(EventType.Parameter.EFFECT, effectParts);
		this.addEffect(pump);
	}
}

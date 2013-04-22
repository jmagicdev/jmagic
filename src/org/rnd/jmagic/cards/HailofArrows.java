package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hail of Arrows")
@Types({Type.INSTANT})
@ManaCost("XW")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SAVIORS_OF_KAMIGAWA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class HailofArrows extends Card
{
	public HailofArrows(GameState state)
	{
		super(state);

		this.setDivision(Union.instance(ValueOfX.instance(This.instance()), Identity.instance("damage")));

		Target target = this.addTarget(Attacking.instance(), "any number of target attacking creatures");
		// TODO: this should be 0-inf, not 1-inf.
		target.setNumber(1, null);

		EventType.ParameterMap damageParameters = new EventType.ParameterMap();
		damageParameters.put(EventType.Parameter.SOURCE, This.instance());
		damageParameters.put(EventType.Parameter.TAKER, ChosenTargetsFor.instance(Identity.instance(target), This.instance()));
		this.addEffect(new EventFactory(EventType.DISTRIBUTE_DAMAGE, damageParameters, "Hail of Arrows deals X damage divided as you choose among any number of target attacking creatures."));
	}
}

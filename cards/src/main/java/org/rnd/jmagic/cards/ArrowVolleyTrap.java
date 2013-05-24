package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Arrow Volley Trap")
@Types({Type.INSTANT})
@SubTypes({SubType.TRAP})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class ArrowVolleyTrap extends Card
{
	public ArrowVolleyTrap(GameState state)
	{
		super(state);

		SetGenerator trapCondition = Intersect.instance(Between.instance(4, null), Count.instance(Attacking.instance()));
		this.addAbility(new org.rnd.jmagic.abilities.Trap(state, this.getName(), trapCondition, "If four or more creatures are attacking", "(1)(W)"));

		Target target = this.addTarget(Attacking.instance(), "up to five target attacking creatures");
		target.setNumber(1, 5);

		this.setDivision(Union.instance(numberGenerator(5), Identity.instance("damage")));
		EventType.ParameterMap damageParameters = new EventType.ParameterMap();
		damageParameters.put(EventType.Parameter.SOURCE, This.instance());
		damageParameters.put(EventType.Parameter.TAKER, ChosenTargetsFor.instance(Identity.instance(target), This.instance()));
		this.addEffect(new EventFactory(EventType.DISTRIBUTE_DAMAGE, damageParameters, "Arrow Volley Trap deals 5 damage divided as you choose among any number of target attacking creatures."));
	}
}

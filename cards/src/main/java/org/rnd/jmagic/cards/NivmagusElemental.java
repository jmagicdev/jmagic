package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nivmagus Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("(U/R)")
@ColorIdentity({Color.BLUE, Color.RED})
public final class NivmagusElemental extends Card
{
	public static final class NivmagusElementalAbility0 extends ActivatedAbility
	{
		public NivmagusElementalAbility0(GameState state)
		{
			super(state, "Exile an instant or sorcery spell you control: Put two +1/+1 counters on Nivmagus Elemental.");

			this.addCost(exile(You.instance(), Intersect.instance(Spells.instance(), HasType.instance(Type.INSTANT, Type.SORCERY), ControlledBy.instance(You.instance(), Stack.instance())), 1, "Exile an instant or sorcery spell you control"));

			this.addEffect(putCounters(2, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put two +1/+1 counters on it instead."));
		}
	}

	public NivmagusElemental(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Exile an instant or sorcery spell you control: Put two +1/+1 counters
		// on Nivmagus Elemental. (That spell won't resolve.)
		this.addAbility(new NivmagusElementalAbility0(state));
	}
}

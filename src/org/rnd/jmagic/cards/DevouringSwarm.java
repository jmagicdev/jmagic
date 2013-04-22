package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Devouring Swarm")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DevouringSwarm extends Card
{
	public static final class DevouringSwarmAbility1 extends ActivatedAbility
	{
		public DevouringSwarmAbility1(GameState state)
		{
			super(state, "Sacrifice a creature: Devouring Swarm gets +1/+1 until end of turn.");
			this.addCost(sacrificeACreature());
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +1, "Devouring Swarm gets +1/+1 until end of turn."));
		}
	}

	public DevouringSwarm(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Sacrifice a creature: Devouring Swarm gets +1/+1 until end of turn.
		this.addAbility(new DevouringSwarmAbility1(state));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Fleetfeather Cockatrice")
@Types({Type.CREATURE})
@SubTypes({SubType.COCKATRICE})
@ManaCost("3GU")
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class FleetfeatherCockatrice extends Card
{
	public static final class FleetfeatherCockatriceAbility2 extends ActivatedAbility
	{
		public FleetfeatherCockatriceAbility2(GameState state)
		{
			super(state, "(5)(G)(U): Monstrosity 3.");
			this.setManaCost(new ManaPool("(5)(G)(U)"));
			this.addEffect(monstrosity(3));
		}
	}

	public FleetfeatherCockatrice(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Flying, deathtouch
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (5)(G)(U): Monstrosity 3. (If this creature isn't monstrous, put
		// three +1/+1 counters on it and it becomes monstrous.)
		this.addAbility(new FleetfeatherCockatriceAbility2(state));
	}
}

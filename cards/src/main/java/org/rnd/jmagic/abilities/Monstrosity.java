package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

/**
 * Represents "[cost]: Monstrosity [N]" where [cost] is just a mana cost and [N]
 * is just an integer.
 * 
 * This ability takes care of setting up the tracker. If you write your own
 * ability that uses the monstrosity event type, you'll need to set up the
 * {@link org.rnd.jmagic.engine.eventTypes.Monstrosity.MonstrousTracker}
 * yourself.
 */
public final class Monstrosity extends ActivatedAbility
{
	private final String manaCost;
	private final int N;

	public Monstrosity(GameState state, String manaCost, int N)
	{
		super(state, manaCost + ": Monstrosity " + N + ".");
		this.manaCost = manaCost;
		this.N = N;

		this.setManaCost(new ManaPool(manaCost));

		state.ensureTracker(new org.rnd.jmagic.engine.eventTypes.Monstrosity.MonstrousTracker());
		EventFactory monstrosity = monstrosity(N);
		this.addEffect(monstrosity);
	}

	@Override
	public Monstrosity create(Game game)
	{
		return new Monstrosity(game.physicalState, this.manaCost, this.N);
	}
}

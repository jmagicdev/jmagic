package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Maze of Ith")
@Types({Type.LAND})
@ColorIdentity({})
public final class MazeofIth extends Card
{
	public static final class Amazin extends ActivatedAbility
	{
		public Amazin(GameState state)
		{
			super(state, "(T): Untap target attacking creature. Prevent all combat damage that would be dealt to and dealt by that creature this turn.");
			this.costsTap = true;

			Target target = this.addTarget(Attacking.instance(), "target attacking creature");
			this.addEffect(untap(targetedBy(target), "Untap target attacking creature."));
			this.addEffect(createFloatingReplacement(new PreventCombatDamageDealtToOrBy(state.game, targetedBy(target), "that creature"), "Prevent all combat damage that would be dealt to and dealt by that creature this turn."));
		}
	}

	public MazeofIth(GameState state)
	{
		super(state);

		// (T): Untap target attacking creature. Prevent all combat damage that
		// would be dealt to and dealt by that creature this turn.
		this.addAbility(new Amazin(state));
	}
}

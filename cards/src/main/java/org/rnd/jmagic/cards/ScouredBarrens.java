package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scoured Barrens")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class ScouredBarrens extends Card
{
	public static final class ScouredBarrensAbility1 extends EventTriggeredAbility
	{
		public ScouredBarrensAbility1(GameState state)
		{
			super(state, "When Scoured Barrens enters the battlefield, you gain 1 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public ScouredBarrens(GameState state)
	{
		super(state);

		// Scoured Barrens enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// When Scoured Barrens enters the battlefield, you gain 1 life.
		this.addAbility(new ScouredBarrensAbility1(state));

		// (T): Add (W) or (B) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(WB)"));
	}
}

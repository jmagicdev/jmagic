package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Panic Spellbomb")
@Types({Type.ARTIFACT})
@ManaCost("1")
@ColorIdentity({Color.RED})
public final class PanicSpellbomb extends Card
{
	public static final class PanicSpellbombAbility0 extends ActivatedAbility
	{
		public PanicSpellbombAbility0(GameState state)
		{
			super(state, "(T), Sacrifice Panic Spellbomb: Target creature can't block this turn.");
			this.costsTap = true;
			this.addCost(sacrificeThis("Panic Spellbomb"));

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

			this.addEffect(cantBlockThisTurn(target, "Target creature can't block this turn."));
		}
	}

	public PanicSpellbomb(GameState state)
	{
		super(state);

		// (T), Sacrifice Panic Spellbomb: Target creature can't block this
		// turn.
		this.addAbility(new PanicSpellbombAbility0(state));

		// When Panic Spellbomb is put into a graveyard from the battlefield,
		// you may pay (R). If you do, draw a card.
		this.addAbility(new ScarsSpellbomb(state, this.getName(), "(R)"));
	}
}

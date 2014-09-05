package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Panic Spellbomb")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
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

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(BlockedBy.instance(target)));
			this.addEffect(createFloatingEffect("Target creature can't block this turn.", part));
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

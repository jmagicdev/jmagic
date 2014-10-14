package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Strength from the Fallen")
@Types({Type.ENCHANTMENT})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class StrengthfromtheFallen extends Card
{
	public static final class StrengthfromtheFallenAbility0 extends EventTriggeredAbility
	{
		public StrengthfromtheFallenAbility0(GameState state)
		{
			super(state, "Constellation \u2014 Whenever Strength from the Fallen or another enchantment enters the battlefield under your control, target creature gets +X/+X until end of turn, where X is the number of creature cards in your graveyard.");
			this.addPattern(constellation());

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			SetGenerator deadThings = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance())));
			SetGenerator X = Count.instance(deadThings);

			this.addEffect(ptChangeUntilEndOfTurn(target, X, X, "Target creature gets +X/+X until end of turn, where X is the number of creature cards in your graveyard."));
		}
	}

	public StrengthfromtheFallen(GameState state)
	{
		super(state);

		// Constellation \u2014 Whenever Strength from the Fallen or another
		// enchantment enters the battlefield under your control, target
		// creature gets +X/+X until end of turn, where X is the number of
		// creature cards in your graveyard.
		this.addAbility(new StrengthfromtheFallenAbility0(state));
	}
}

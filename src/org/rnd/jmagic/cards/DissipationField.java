package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Dissipation Field")
@Types({Type.ENCHANTMENT})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class DissipationField extends Card
{
	public static final class DissipationFieldAbility0 extends EventTriggeredAbility
	{
		public DissipationFieldAbility0(GameState state)
		{
			super(state, "Whenever a permanent deals damage to you, return it to its owner's hand.");
			this.addPattern(new SimpleDamagePattern(Permanents.instance(), You.instance()));
			SetGenerator it = SourceOfDamage.instance(TriggerDamage.instance(This.instance()));
			this.addEffect(bounce(it, "Return it to its owner's hand."));
		}
	}

	public DissipationField(GameState state)
	{
		super(state);

		// Whenever a permanent deals damage to you, return it to its owner's
		// hand.
		this.addAbility(new DissipationFieldAbility0(state));
	}
}

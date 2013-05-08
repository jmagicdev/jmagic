package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Intangible Virtue")
@Types({Type.ENCHANTMENT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class IntangibleVirtue extends Card
{
	public static final class IntangibleVirtueAbility0 extends StaticAbility
	{
		public IntangibleVirtueAbility0(GameState state)
		{
			super(state, "Creature tokens you control get +1/+1 and have vigilance.");

			SetGenerator affected = Intersect.instance(CREATURES_YOU_CONTROL, Tokens.instance());

			this.addEffectPart(modifyPowerAndToughness(affected, +1, +1));
			this.addEffectPart(addAbilityToObject(affected, org.rnd.jmagic.abilities.keywords.Vigilance.class));
		}
	}

	public IntangibleVirtue(GameState state)
	{
		super(state);

		// Creature tokens you control get +1/+1 and have vigilance.
		this.addAbility(new IntangibleVirtueAbility0(state));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Racecourse Fury")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("R")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class RacecourseFury extends Card
{
	public static final class RacecourseFuryAbility1 extends StaticAbility
	{
		public static final class GrantHaste extends ActivatedAbility
		{
			public GrantHaste(GameState state)
			{
				super(state, "(T): Target creature gains haste until end of turn.");
				this.costsTap = true;

				Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

				this.addEffect(addAbilityUntilEndOfTurn(targetedBy(target), org.rnd.jmagic.abilities.keywords.Haste.class, "Target creature gains haste until end of turn."));
			}
		}

		public RacecourseFuryAbility1(GameState state)
		{
			super(state, "Enchanted land has \"(T): Target creature gains haste until end of turn.\"");

			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), GrantHaste.class));
		}
	}

	public RacecourseFury(GameState state)
	{
		super(state);

		// Enchant land
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		// Enchanted land has
		// "(T): Target creature gains haste until end of turn."
		this.addAbility(new RacecourseFuryAbility1(state));
	}
}

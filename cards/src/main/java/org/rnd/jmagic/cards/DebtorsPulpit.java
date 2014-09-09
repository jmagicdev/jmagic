package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Debtor's Pulpit")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("4W")
@ColorIdentity({Color.WHITE})
public final class DebtorsPulpit extends Card
{
	public static final class TapTargetCreature extends ActivatedAbility
	{
		public TapTargetCreature(GameState state)
		{
			super(state, "(T): Tap target creature.");
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(tap(target, "Tap target creature."));
		}
	}

	public static final class DebtorsPulpitAbility1 extends StaticAbility
	{
		public DebtorsPulpitAbility1(GameState state)
		{
			super(state, "Enchanted land has \"(T): Tap target creature.\"");
			SetGenerator enchanted = EnchantedBy.instance(This.instance());
			this.addEffectPart(addAbilityToObject(enchanted, TapTargetCreature.class));
		}
	}

	public DebtorsPulpit(GameState state)
	{
		super(state);

		// Enchant land
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		// Enchanted land has "(T): Tap target creature."
		this.addAbility(new DebtorsPulpitAbility1(state));
	}
}

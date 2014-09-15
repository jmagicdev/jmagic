package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Everflame Eidolon")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class EverflameEidolon extends Card
{
	public static final class EverflameEidolonAbility1 extends ActivatedAbility
	{
		public EverflameEidolonAbility1(GameState state)
		{
			super(state, "(R): Everflame Eidolon gets +1/+0 until end of turn. If it's an Aura, enchanted creature gets +1/+0 until end of turn instead.");
			this.setManaCost(new ManaPool("(R)"));

			SetGenerator thisIsAura = Intersect.instance(HasSubType.instance(SubType.AURA), ABILITY_SOURCE_OF_THIS);
			SetGenerator theRightThing = IfThenElse.instance(thisIsAura, EnchantedBy.instance(ABILITY_SOURCE_OF_THIS), ABILITY_SOURCE_OF_THIS);
			this.addEffect(ptChangeUntilEndOfTurn(theRightThing, +1, +0, "Everflame Eidolon gets +1/+0 until end of turn. If it's an Aura, enchanted creature gets +1/+0 until end of turn instead."));
		}
	}

	public static final class EverflameEidolonAbility2 extends StaticAbility
	{
		public EverflameEidolonAbility2(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1.");
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), +1, +1));
		}
	}

	public EverflameEidolon(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Bestow (2)(R) (If you cast this card for its bestow cost, it's an
		// Aura spell with enchant creature. It becomes a creature again if it's
		// not attached to a creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bestow(state, "(2)(R)"));

		// (R): Everflame Eidolon gets +1/+0 until end of turn. If it's an Aura,
		// enchanted creature gets +1/+0 until end of turn instead.
		this.addAbility(new EverflameEidolonAbility1(state));

		// Enchanted creature gets +1/+1.
		this.addAbility(new EverflameEidolonAbility2(state));
	}
}

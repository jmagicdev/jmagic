package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Daybreak Coronet")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("WW")
@ColorIdentity({Color.WHITE})
public final class DaybreakCoronet extends Card
{

	public static final class EnchantCreatureWithAnotherAuraAttachedToIt extends org.rnd.jmagic.abilities.keywords.Enchant
	{
		private static SetGenerator enchantRestriction = null;

		private static SetGenerator enchantRestriction()
		{
			if(enchantRestriction == null)
			{
				SetGenerator otherAuras = RelativeComplement.instance(HasSubType.instance(SubType.AURA), This.instance());
				SetGenerator thingsWithAurasAttached = EnchantedBy.instance(otherAuras);
				enchantRestriction = Intersect.instance(CreaturePermanents.instance(), thingsWithAurasAttached);
			}
			return enchantRestriction;
		}

		public EnchantCreatureWithAnotherAuraAttachedToIt(GameState state)
		{
			super(state, "creature with another aura attached to it", enchantRestriction());
		}
	}

	public static final class LotsOfBonuses extends StaticAbility
	{
		public LotsOfBonuses(GameState state)
		{
			super(state, "Enchanted creature gets +3/+3 and has first strike, vigilance, and lifelink.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, +3, +3));
			this.addEffectPart(addAbilityToObject(enchantedCreature, org.rnd.jmagic.abilities.keywords.FirstStrike.class, org.rnd.jmagic.abilities.keywords.Vigilance.class, org.rnd.jmagic.abilities.keywords.Lifelink.class));
		}
	}

	public DaybreakCoronet(GameState state)
	{
		super(state);

		// Enchant creature with another Aura attached to it
		this.addAbility(new EnchantCreatureWithAnotherAuraAttachedToIt(state));

		// Enchanted creature gets +3/+3 and has first strike, vigilance, and
		// lifelink. (Damage dealt by the creature also causes its controller to
		// gain that much life.)
		this.addAbility(new LotsOfBonuses(state));
	}
}

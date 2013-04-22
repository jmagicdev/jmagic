package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rancor")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.PLANECHASE_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.URZAS_LEGACY, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class Rancor extends Card
{
	public static final class RancorAbility1 extends StaticAbility
	{
		public RancorAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +2/+0 and has trample.");

			SetGenerator enchanted = EnchantedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(enchanted, +2, +0));
			this.addEffectPart(addAbilityToObject(enchanted, new SimpleAbilityFactory(org.rnd.jmagic.abilities.keywords.Trample.class)));
		}
	}

	public static final class RancorAbility2 extends EventTriggeredAbility
	{
		public RancorAbility2(GameState state)
		{
			super(state, "When Rancor is put into a graveyard from the battlefield, return Rancor to its owner's hand.");
			this.addPattern(whenThisIsPutIntoAGraveyardFromTheBattlefield());

			SetGenerator owner = OwnerOf.instance(ABILITY_SOURCE_OF_THIS);

			EventFactory move = new EventFactory(EventType.MOVE_OBJECTS, "Return Rancor to its owner's hand.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.TO, HandOf.instance(owner));
			move.parameters.put(EventType.Parameter.OBJECT, FutureSelf.instance(ABILITY_SOURCE_OF_THIS));
			this.addEffect(move);
		}
	}

	public Rancor(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+0 and has trample.
		this.addAbility(new RancorAbility1(state));

		// When Rancor is put into a graveyard from the battlefield, return
		// Rancor to its owner's hand.
		this.addAbility(new RancorAbility2(state));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Celestial Mantle")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3WWW")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class CelestialMantle extends Card
{
	public static final class CelestialPump extends StaticAbility
	{
		public CelestialPump(GameState state)
		{
			super(state, "Enchanted creature gets +3/+3.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(enchantedCreature, 3, 3));
		}
	}

	public static final class CelestialDamage extends EventTriggeredAbility
	{
		public CelestialDamage(GameState state)
		{
			super(state, "Whenever enchanted creature deals combat damage to a player, double its controller's life total.");

			this.addPattern(whenDealsCombatDamageToAPlayer(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS)));

			SetGenerator itsController = ControllerOf.instance(SourceOfDamage.instance(TriggerDamage.instance(This.instance())));

			EventFactory factory = new EventFactory(EventType.SET_LIFE, "Double its controller's life total.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, Multiply.instance(numberGenerator(2), LifeTotalOf.instance(itsController)));
			factory.parameters.put(EventType.Parameter.PLAYER, itsController);
			this.addEffect(factory);
		}
	}

	public CelestialMantle(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +3/+3.
		this.addAbility(new CelestialPump(state));

		// Whenever enchanted creature deals combat damage to a player, double
		// its controller's life total.
		this.addAbility(new CelestialDamage(state));
	}
}

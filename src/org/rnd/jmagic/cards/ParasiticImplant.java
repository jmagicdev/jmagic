package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Parasitic Implant")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class ParasiticImplant extends Card
{
	public static final class ParasiticImplantAbility1 extends EventTriggeredAbility
	{
		public ParasiticImplantAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, enchanted creature's controller sacrifices it and you put a 1/1 colorless Myr artifact creature token onto the battlefield.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator enchantedCreature = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);

			EventFactory sacrifice = new EventFactory(EventType.SACRIFICE_PERMANENTS, "Enchanted creature's controller sacrifices it");
			sacrifice.parameters.put(EventType.Parameter.CAUSE, This.instance());
			sacrifice.parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(enchantedCreature));
			sacrifice.parameters.put(EventType.Parameter.PERMANENT, enchantedCreature);
			this.addEffect(sacrifice);

			CreateTokensFactory factory = new CreateTokensFactory(1, 1, 1, "and you put a 1/1 colorless Myr artifact creature token onto the battlefield.");
			factory.setSubTypes(SubType.MYR);
			factory.setArtifact();
			this.addEffect(factory.getEventFactory());
		}
	}

	public ParasiticImplant(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// At the beginning of your upkeep, enchanted creature's controller
		// sacrifices it and you put a 1/1 colorless Myr artifact creature token
		// onto the battlefield.
		this.addAbility(new ParasiticImplantAbility1(state));
	}
}

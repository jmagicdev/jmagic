package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Freed from the Real")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = SaviorsOfKamigawa.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class FreedfromtheReal extends Card
{
	public static final class ManipulateCreature extends ActivatedAbility
	{
		private EventType how;

		public ManipulateCreature(GameState state, EventType how)
		{
			super(state, "(U): " + (how == EventType.TAP_PERMANENTS ? "Tap" : "Untap") + " enchanted creature.");
			this.how = how;

			this.setManaCost(new ManaPool("U"));

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.OBJECT, EnchantedBy.instance(ABILITY_SOURCE_OF_THIS));
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			this.addEffect(new EventFactory(how, parameters, ((how == EventType.TAP_PERMANENTS ? "Tap" : "Untap") + " enchanted creature.")));
		}

		@Override
		public ManipulateCreature create(Game game)
		{
			return new ManipulateCreature(game.physicalState, this.how);
		}
	}

	public FreedfromtheReal(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// {U}: Tap enchanted creature.
		this.addAbility(new ManipulateCreature(state, EventType.TAP_PERMANENTS));

		// {U}: Untap enchanted creature.
		this.addAbility(new ManipulateCreature(state, EventType.UNTAP_PERMANENTS));
	}
}

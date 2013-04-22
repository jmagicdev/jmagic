package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vapor Snare")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class VaporSnare extends Card
{
	public static final class ThisCardIsWorseThanMindControl extends EventTriggeredAbility
	{
		public ThisCardIsWorseThanMindControl(GameState state)
		{
			super(state, "At the beginning of your upkeep, sacrifice Vapor Snare unless you return a land you control to its owner's hand.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory sacrifice = sacrificeThis("Vapor Snare");

			EventFactory returnLand = new EventFactory(EventType.PUT_INTO_HAND_CHOICE, "Return a land you control to its owner's hand.");
			returnLand.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnLand.parameters.put(EventType.Parameter.PLAYER, You.instance());
			returnLand.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(LandPermanents.instance(), ControlledBy.instance(You.instance())));
			returnLand.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			this.addEffect(unless(You.instance(), sacrifice, returnLand, "Sacrifice Vapor Snare unless you return a land you control to its owner's hand."));
		}
	}

	public VaporSnare(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// You control enchanted creature.
		this.addAbility(new org.rnd.jmagic.abilities.YouControlEnchantedCreature(state));

		// At the beginning of your upkeep, sacrifice Vapor Snare unless you
		// return a land you control to its owner's hand.
		this.addAbility(new ThisCardIsWorseThanMindControl(state));
	}
}

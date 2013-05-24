package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Narcomoeba")
@Types({Type.CREATURE})
@SubTypes({SubType.ILLUSION})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.FUTURE_SIGHT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Narcomoeba extends Card
{
	public static final class MillToBattlefield extends EventTriggeredAbility
	{
		public MillToBattlefield(GameState state)
		{
			super(state, "When Narcomoeba is put into your graveyard from your library, you may put it onto the battlefield.");
			this.triggersFromGraveyard();

			this.addPattern(new SimpleZoneChangePattern(LibraryOf.instance(You.instance()), GraveyardOf.instance(You.instance()), ABILITY_SOURCE_OF_THIS, false));

			EventFactory putOntoBattlefield = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Put Narcomoeba onto the battlefield.");
			putOntoBattlefield.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putOntoBattlefield.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			putOntoBattlefield.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);

			this.addEffect(youMay(putOntoBattlefield, "You may put it onto the battlefield."));
		}
	}

	public Narcomoeba(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Narcomoeba is put into your graveyard from your library, you may
		// put it onto the battlefield.
		this.addAbility(new MillToBattlefield(state));
	}
}

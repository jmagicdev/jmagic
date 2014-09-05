package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Gravespawn Sovereign")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("4BB")
@Printings({@Printings.Printed(ex = Onslaught.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class GravespawnSovereign extends Card
{
	public static final class GravespawnSovereignAbility0 extends ActivatedAbility
	{
		public GravespawnSovereignAbility0(GameState state)
		{
			super(state, "Tap five untapped Zombies you control: Put target creature card from a graveyard onto the battlefield under your control.");

			SetGenerator yourZombies = Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.ZOMBIE));
			SetGenerator yourUntappedZombies = Intersect.instance(Untapped.instance(), yourZombies);

			EventFactory tapFiveZombies = new EventFactory(EventType.TAP_CHOICE, "Tap five untapped Zombies you control");
			tapFiveZombies.parameters.put(EventType.Parameter.CAUSE, This.instance());
			tapFiveZombies.parameters.put(EventType.Parameter.PLAYER, You.instance());
			tapFiveZombies.parameters.put(EventType.Parameter.NUMBER, numberGenerator(5));
			tapFiveZombies.parameters.put(EventType.Parameter.CHOICE, yourUntappedZombies);
			this.addCost(tapFiveZombies);

			Target target = this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(Players.instance()))), "target creature card from a graveyard");

			EventType.ParameterMap moveParameters = new EventType.ParameterMap();
			moveParameters.put(EventType.Parameter.CAUSE, This.instance());
			moveParameters.put(EventType.Parameter.CONTROLLER, You.instance());
			moveParameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			this.addEffect(new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, moveParameters, "Put target creature card from a graveyard onto the battlefield under your control."));
		}
	}

	public GravespawnSovereign(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Tap five untapped Zombies you control: Put target creature card from
		// a graveyard onto the battlefield under your control.
		this.addAbility(new GravespawnSovereignAbility0(state));
	}
}

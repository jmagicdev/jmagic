package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Flayer of the Hatebound")
@Types({Type.CREATURE})
@SubTypes({SubType.DEVIL})
@ManaCost("5R")
@ColorIdentity({Color.RED})
public final class FlayeroftheHatebound extends Card
{
	public static final class FlayeroftheHateboundAbility1 extends EventTriggeredAbility
	{
		public FlayeroftheHateboundAbility1(GameState state)
		{
			super(state, "Whenever Flayer of the Hatebound or another creature enters the battlefield from your graveyard, that creature deals damage equal to its power to target creature or player.");

			this.addPattern(new SimpleZoneChangePattern(GraveyardOf.instance(You.instance()), Battlefield.instance(), CreaturePermanents.instance(), false));

			SetGenerator thatCreature = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));

			EventFactory factory = new EventFactory(EventType.DEAL_DAMAGE_EVENLY, "That creature deals damage equal to its power to target creature or player.");
			factory.parameters.put(EventType.Parameter.SOURCE, thatCreature);
			factory.parameters.put(EventType.Parameter.NUMBER, PowerOf.instance(thatCreature));
			factory.parameters.put(EventType.Parameter.TAKER, target);
			this.addEffect(factory);
		}
	}

	public FlayeroftheHatebound(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);

		// Undying (When this creature dies, if it had no +1/+1 counters on it,
		// return it to the battlefield under its owner's control with a +1/+1
		// counter on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Undying(state));

		// Whenever Flayer of the Hatebound or another creature enters the
		// battlefield from your graveyard, that creature deals damage equal to
		// its power to target creature or player.
		this.addAbility(new FlayeroftheHateboundAbility1(state));
	}
}

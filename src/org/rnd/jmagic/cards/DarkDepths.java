package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dark Depths")
@SuperTypes({SuperType.LEGENDARY, SuperType.SNOW})
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.COLDSNAP, r = Rarity.RARE)})
@ColorIdentity({})
public final class DarkDepths extends Card
{
	public static final class RemoveCounter extends ActivatedAbility
	{
		public RemoveCounter(GameState state)
		{
			super(state, "(3): Remove an ice counter from Dark Depths.");
			this.setManaCost(new ManaPool("3"));
			this.addEffect(removeCountersFromThis(1, Counter.CounterType.ICE, "Dark Depths"));
		}
	}

	public static final class MakeMaritLage extends StateTriggeredAbility
	{
		public MakeMaritLage(GameState state)
		{
			super(state, "When Dark Depths has no ice counters on it, sacrifice it. If you do, put a legendary 20/20 black Avatar creature token with flying and \"This creature is indestructible\" named Marit Lage onto the battlefield.");

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			SetGenerator iceCountersOnThis = CountersOn.instance(thisCard, Counter.CounterType.ICE);
			SetGenerator numIceCountersOnThis = Count.instance(iceCountersOnThis);
			SetGenerator triggerCondition = Intersect.instance(numberGenerator(0), numIceCountersOnThis);
			this.addCondition(triggerCondition);

			EventFactory sacrifice = sacrificeThis("Dark Depths");

			CreateTokensFactory token = new CreateTokensFactory(1, 20, 20, "put an indestructible legendary 20/20 black Avatar creature token with flying named Marit Lage onto the battlefield");
			token.addAbility(org.rnd.jmagic.abilities.ThisCreatureIsIndestructible.class);
			token.setLegendary();
			token.setColors(Color.BLACK);
			token.setSubTypes(SubType.AVATAR);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			token.setName("Marit Lage");

			EventFactory ifThenElse = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "Sacrifice Dark Depths.  If you do, put an indestructible legendary 20/20 black Avatar creature token with flying named Marit Lage onto the battlefield.");
			ifThenElse.parameters.put(EventType.Parameter.IF, Identity.instance(sacrifice));
			ifThenElse.parameters.put(EventType.Parameter.THEN, Identity.instance(token.getEventFactory()));
			this.addEffect(ifThenElse);

		}
	}

	public DarkDepths(GameState state)
	{
		super(state);

		// Dark Depths enters the battlefield with ten ice counters on it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), 10, Counter.CounterType.ICE));

		// {3}: Remove an ice counter from Dark Depths.
		this.addAbility(new RemoveCounter(state));

		// When Dark Depths has no ice counters on it, sacrifice it. If you do,
		// put an indestructible legendary 20/20 black Avatar creature token
		// with flying named Marit Lage onto the battlefield.
		this.addAbility(new MakeMaritLage(state));
	}
}

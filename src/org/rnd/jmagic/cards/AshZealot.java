package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ash Zealot")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("RR")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class AshZealot extends Card
{
	public static final class AshZealotAbility1 extends EventTriggeredAbility
	{
		private static final class CastFromYardPattern implements EventPattern
		{
			@Override
			public boolean match(Event event, Identified object, GameState state)
			{
				if(!EventType.BECOMES_PLAYED.equals(event.type))
					return false;
				GameObject cast = event.parametersNow.get(EventType.Parameter.OBJECT).evaluate(state, event.getSource()).getOne(GameObject.class);
				if(!(cast.isSpell()))
					return false;
				if(cast.getOwner(state).getGraveyardID() != cast.zoneCastFrom)
					return false;
				return true;
			}

			@Override
			public boolean looksBackInTime()
			{
				return false;
			}

			@Override
			public boolean matchesManaAbilities()
			{
				return false;
			}
		}

		public AshZealotAbility1(GameState state)
		{
			super(state, "Whenever a player casts a spell from a graveyard, Ash Zealot deals 3 damage to that player.");

			this.addPattern(new CastFromYardPattern());

			SetGenerator triggerEvent = TriggerEvent.instance(This.instance());
			SetGenerator thatPlayer = EventParameter.instance(triggerEvent, EventType.Parameter.PLAYER);

			this.addEffect(permanentDealDamage(3, thatPlayer, "Ash Zealot deals 3 damage to that player."));
		}
	}

	public AshZealot(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// First strike, haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Whenever a player casts a spell from a graveyard, Ash Zealot deals 3
		// damage to that player.
		this.addAbility(new AshZealotAbility1(state));
	}
}

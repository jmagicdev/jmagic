package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sunken Hope")
@Types({Type.ENCHANTMENT})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.PLANESHIFT, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class SunkenHope extends Card
{
	public static final class Deserter extends EventTriggeredAbility
	{
		public Deserter(GameState state)
		{
			super(state, "At the beginning of each player's upkeep, that player returns a creature he or she controls to its owner's hand.");

			this.addPattern(atTheBeginningOfEachPlayersUpkeep());

			SetGenerator thatPlayer = OwnerOf.instance(EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.STEP));

			EventType.ParameterMap bounceParameters = new EventType.ParameterMap();
			bounceParameters.put(EventType.Parameter.CAUSE, This.instance());
			bounceParameters.put(EventType.Parameter.PLAYER, thatPlayer);
			bounceParameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			bounceParameters.put(EventType.Parameter.CHOICE, Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(thatPlayer)));
			this.addEffect(new EventFactory(EventType.PUT_INTO_HAND_CHOICE, bounceParameters, "That player returns a creature he or she controls to its owner's hand."));
		}
	}

	public SunkenHope(GameState state)
	{
		super(state);

		this.addAbility(new Deserter(state));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gomazoa")
@Types({Type.CREATURE})
@SubTypes({SubType.JELLYFISH})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Gomazoa extends Card
{
	/**
	 * @eparam CAUSE: gomazoa's ability
	 * @eparam OBJECT: the stuff to shuffle
	 */
	public static EventType GOMAZOA_EVENT = new EventType("GOMAZOA_EVENT")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Set stuff = parameters.get(Parameter.OBJECT);

			java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
			moveParameters.put(Parameter.CAUSE, cause);
			moveParameters.put(Parameter.INDEX, NEGATIVE_ONE);
			moveParameters.put(Parameter.OBJECT, stuff);
			Event move = createEvent(game, "Put Gomazoa and each creature it's blocking on top of their owners' libraries", PUT_INTO_LIBRARY, moveParameters);
			move.perform(event, true);
			Set moved = move.getResult();

			Set owners = new Set();
			for(ZoneChange z: moved.getAll(ZoneChange.class))
				owners.add(game.actualState.<GameObject>get(z.oldObjectID).getOwner(game.actualState));

			java.util.Map<Parameter, Set> shuffleParameters = new java.util.HashMap<Parameter, Set>();
			shuffleParameters.put(Parameter.CAUSE, cause);
			shuffleParameters.put(Parameter.PLAYER, owners);
			Event shuffle = createEvent(game, "Those players shuffle their libraries", EventType.SHUFFLE_LIBRARY, shuffleParameters);
			shuffle.perform(event, true);

			event.setResult(Empty.set);
			return true;
		}
	};

	public static final class ApparentlyJellyfishMakeThingsDisappear extends ActivatedAbility
	{
		public ApparentlyJellyfishMakeThingsDisappear(GameState state)
		{
			super(state, "(T): Put Gomazoa and each creature it's blocking on top of their owners' libraries, then those players shuffle their libraries.");
			this.costsTap = true;

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			SetGenerator stuff = Union.instance(thisCard, BlockedBy.instance(thisCard));

			EventFactory gomazoa = new EventFactory(GOMAZOA_EVENT, "Put Gomazoa and each creature it's blocking on top of their owners' libraries, then those players shuffle their libraries.");
			gomazoa.parameters.put(EventType.Parameter.CAUSE, This.instance());
			gomazoa.parameters.put(EventType.Parameter.OBJECT, stuff);
			this.addEffect(gomazoa);
		}
	}

	public Gomazoa(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);

		// Defender, flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (T): Put Gomazoa and each creature it's blocking on top of their
		// owners' libraries, then those players shuffle their libraries.
		this.addAbility(new ApparentlyJellyfishMakeThingsDisappear(state));
	}
}

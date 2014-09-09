package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tangle Wire")
@Types({Type.ARTIFACT})
@ManaCost("3")
@ColorIdentity({})
public final class TangleWire extends Card
{
	public static final class TangleWireAbility1 extends EventTriggeredAbility
	{
		public TangleWireAbility1(GameState state)
		{
			super(state, "At the beginning of each player's upkeep, that player taps an untapped artifact, creature, or land he or she controls for each fade counter on Tangle Wire.");
			this.addPattern(atTheBeginningOfEachPlayersUpkeep());

			SetGenerator thatPlayer = OwnerOf.instance(CurrentStep.instance());
			SetGenerator stuff = Intersect.instance(Untapped.instance(), HasType.instance(Type.ARTIFACT, Type.CREATURE, Type.LAND));
			SetGenerator thatPlayersStuff = Intersect.instance(stuff, ControlledBy.instance(thatPlayer));
			SetGenerator numFadeCounters = Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.FADE));

			EventFactory tappyTappy = new EventFactory(EventType.TAP_CHOICE, "That player taps an untapped artifact, creature, or land he or she controls for each fade counter on Tangle Wire.");
			tappyTappy.parameters.put(EventType.Parameter.CAUSE, This.instance());
			tappyTappy.parameters.put(EventType.Parameter.PLAYER, thatPlayer);
			tappyTappy.parameters.put(EventType.Parameter.CHOICE, thatPlayersStuff);
			tappyTappy.parameters.put(EventType.Parameter.NUMBER, numFadeCounters);
			this.addEffect(tappyTappy);
		}
	}

	public TangleWire(GameState state)
	{
		super(state);

		// Fading 4
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Fading(state, 4));

		// At the beginning of each player's upkeep, that player taps an
		// untapped artifact, creature, or land he or she controls for each fade
		// counter on Tangle Wire.
		this.addAbility(new TangleWireAbility1(state));
	}
}

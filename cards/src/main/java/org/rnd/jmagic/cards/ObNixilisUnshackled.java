package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Ob Nixilis, Unshackled")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("4BB")
@ColorIdentity({Color.BLACK})
public final class ObNixilisUnshackled extends Card
{
	public static final class ObNixilisUnshackledAbility1 extends EventTriggeredAbility
	{
		public ObNixilisUnshackledAbility1(GameState state)
		{
			super(state, "Whenever an opponent searches his or her library, that player sacrifices a creature and loses 10 life.");

			SetGenerator opponents = OpponentsOf.instance(You.instance());
			SetGenerator libraries = LibraryOf.instance(opponents);
			SetGenerator inLibraries = InZone.instance(libraries);

			SimpleEventPattern search = new SimpleEventPattern(EventType.SEARCH_MARKER);
			search.put(EventType.Parameter.PLAYER, opponents);
			search.put(EventType.Parameter.CARD, Union.instance(libraries, inLibraries));
			this.addPattern(search);

			SetGenerator thatPlayer = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.PLAYER);
			this.addEffect(sacrifice(thatPlayer, 1, CreaturePermanents.instance(), "That player sacrifices a creature"));
			this.addEffect(loseLife(thatPlayer, 10, "and loses 10 life."));
		}
	}

	public static final class ObNixilisUnshackledAbility2 extends EventTriggeredAbility
	{
		public ObNixilisUnshackledAbility2(GameState state)
		{
			super(state, "Whenever another creature dies, put a +1/+1 counter on Ob Nixilis, Unshackled.");
			this.addPattern(whenXDies(RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS)));
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Put a +1/+1 counter on Ob Nixilis, Unshackled."));
		}
	}

	public ObNixilisUnshackled(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying, trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Whenever an opponent searches his or her library, that player
		// sacrifices a creature and loses 10 life.
		this.addAbility(new ObNixilisUnshackledAbility1(state));

		// Whenever another creature dies, put a +1/+1 counter on Ob Nixilis,
		// Unshackled.
		this.addAbility(new ObNixilisUnshackledAbility2(state));
	}
}

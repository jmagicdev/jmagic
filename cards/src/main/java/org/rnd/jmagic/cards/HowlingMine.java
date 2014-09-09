package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Howling Mine")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class HowlingMine extends Card
{
	public static final class Howl extends EventTriggeredAbility
	{
		public Howl(GameState state)
		{
			super(state, "At the beginning of each player's draw step, if Howling Mine is untapped, that player draws an additional card.");

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			SetGenerator thatPlayer = OwnerOf.instance(EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.STEP));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, DrawStepOf.instance(Players.instance()));
			this.addPattern(pattern);

			this.interveningIf = RelativeComplement.instance(thisCard, Tapped.instance());

			this.addEffect(drawCards(thatPlayer, 1, "That player draws an additional card."));
		}
	}

	public HowlingMine(GameState state)
	{
		super(state);

		this.addAbility(new Howl(state));
	}
}

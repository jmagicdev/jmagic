package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Font of Mythos")
@Types({Type.ARTIFACT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Conflux.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class FontofMythos extends Card
{
	public static final class Howl extends EventTriggeredAbility
	{
		public Howl(GameState state)
		{
			super(state, "At the beginning of each player's draw step, that player draws two additional cards.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, DrawStepOf.instance(Players.instance()));
			this.addPattern(pattern);

			SetGenerator thatPlayer = OwnerOf.instance(EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.STEP));
			this.addEffect(drawCards(thatPlayer, 2, "That player draws two additional cards."));
		}
	}

	public FontofMythos(GameState state)
	{
		super(state);

		this.addAbility(new Howl(state));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mind Bend")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MIRAGE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class MindBend extends Card
{
	public MindBend(GameState state)
	{
		super(state);

		Target target = this.addTarget(Permanents.instance(), "target permanent");

		// Change the text of target permanent by replacing all instances of one
		// color word with another or one basic land type with another. (For
		// example, you may change "nonblack creature" to "nongreen creature" or
		// "forestwalk" to "islandwalk." This effect doesn't end at end of
		// turn.)
		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.TARGET, targetedBy(target));
		parameters.put(EventType.Parameter.EFFECT, Identity.instance(Empty.instance()));
		this.addEffect(new EventFactory(EventType.TEXT_CHANGE_COLOR_OR_BASIC_LAND_TYPE, parameters, "Change the text of target permanent by replacing all instances of one color word with another or one basic land type with another."));
	}
}

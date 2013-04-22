package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Angel's Tomb")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class AngelsTomb extends Card
{
	public static final class AngelsTombAbility0 extends EventTriggeredAbility
	{
		public AngelsTombAbility0(GameState state)
		{
			super(state, "Whenever a creature enters the battlefield under your control, you may have Angel's Tomb become a 3/3 white Angel artifact creature with flying until end of turn.");
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), CreaturePermanents.instance(), ControllerOf.instance(ABILITY_SOURCE_OF_THIS), false));

			Animator animate = new Animator(ABILITY_SOURCE_OF_THIS, 3, 3);
			animate.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			animate.addColor(Color.WHITE);
			animate.addSubType(SubType.ANGEL);
			animate.addType(Type.ARTIFACT);
			animate.addType(Type.CREATURE);
			animate.removeOldTypes();

			this.addEffect(youMay(createFloatingEffect("Angel's Tomb become a 3/3 white Angel artifact creature with flying until end of turn.", animate.getParts()), "You may have Angel's Tomb become a 3/3 white Angel artifact creature with flying until end of turn."));
		}
	}

	public AngelsTomb(GameState state)
	{
		super(state);

		// Whenever a creature enters the battlefield under your control, you
		// may have Angel's Tomb become a 3/3 white Angel artifact creature with
		// flying until end of turn.
		this.addAbility(new AngelsTombAbility0(state));
	}
}

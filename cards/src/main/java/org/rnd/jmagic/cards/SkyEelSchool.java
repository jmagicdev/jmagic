package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Sky-Eel School")
@Types({Type.CREATURE})
@SubTypes({SubType.FISH})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SkyEelSchool extends Card
{
	public static final class SkyEelSchoolAbility1 extends EventTriggeredAbility
	{
		public SkyEelSchoolAbility1(GameState state)
		{
			super(state, "When Sky-Eel School enters the battlefield, draw a card, then discard a card.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(drawACard());
			this.addEffect(discardCards(You.instance(), 1, "Discard a card."));
		}
	}

	public SkyEelSchool(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Sky-Eel School enters the battlefield, draw a card, then discard
		// a card.
		this.addAbility(new SkyEelSchoolAbility1(state));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Consecrated Sphinx")
@Types({Type.CREATURE})
@SubTypes({SubType.SPHINX})
@ManaCost("4UU")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE})
public final class ConsecratedSphinx extends Card
{
	public static final class ConsecratedSphinxAbility1 extends EventTriggeredAbility
	{
		public ConsecratedSphinxAbility1(GameState state)
		{
			super(state, "Whenever an opponent draws a card, you may draw two cards.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DRAW_ONE_CARD);
			pattern.put(EventType.Parameter.PLAYER, OpponentsOf.instance(ControllerOf.instance(ABILITY_SOURCE_OF_THIS)));
			this.addPattern(pattern);

			this.addEffect(youMay(drawCards(You.instance(), 2, "Draw two cards."), "You may draw two cards."));
		}
	}

	public ConsecratedSphinx(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(6);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever an opponent draws a card, you may draw two cards.
		this.addAbility(new ConsecratedSphinxAbility1(state));
	}
}

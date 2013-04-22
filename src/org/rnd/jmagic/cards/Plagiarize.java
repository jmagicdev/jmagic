package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Plagiarize")
@Types({Type.INSTANT})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.TORMENT, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Plagiarize extends Card
{
	public Plagiarize(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		SimpleEventPattern theyDraw = new SimpleEventPattern(EventType.DRAW_ONE_CARD);
		theyDraw.put(EventType.Parameter.PLAYER, targetedBy(target));

		EventReplacementEffect replacement = new EventReplacementEffect(state.game, "If target player would draw a card, instead that player skips that draw and you draw a card.", theyDraw);

		EventType.ParameterMap youDrawParameters = new EventType.ParameterMap();
		youDrawParameters.put(EventType.Parameter.PLAYER, You.instance());
		youDrawParameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		replacement.addEffect(new EventFactory(EventType.DRAW_CARDS, youDrawParameters, "You draw a card."));

		this.addEffect(createFloatingReplacement(replacement, "Until end of turn, if target player would draw a card, instead that player skips that draw and you draw a card."));
	}
}

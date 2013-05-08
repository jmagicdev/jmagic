package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Disrupting Shoal")
@Types({Type.INSTANT})
@SubTypes({SubType.ARCANE})
@ManaCost("XUU")
@Printings({@Printings.Printed(ex = Expansion.BETRAYERS_OF_KAMIGAWA, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class DisruptingShoal extends Card
{
	public DisruptingShoal(GameState state)
	{
		super(state);

		// You may exile a blue card with converted mana cost X from your hand
		// rather than pay Disrupting Shoal's mana cost.
		this.addAbility(new org.rnd.jmagic.abilities.ShoalAbility(state, Color.BLUE, this.getName()));

		// Counter target spell if its converted mana cost is X.
		SetGenerator target = targetedBy(this.addTarget(Spells.instance(), "target spell"));
		SetGenerator itsCMCisX = Intersect.instance(ConvertedManaCostOf.instance(target), ValueOfX.instance(This.instance()));

		EventFactory counter = counter(target, "Counter target spell");
		EventFactory counterIf = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "Counter target spell if its converted mana cost is X.");
		counterIf.parameters.put(EventType.Parameter.IF, itsCMCisX);
		counterIf.parameters.put(EventType.Parameter.THEN, Identity.instance(counter));
		this.addEffect(counterIf);
	}
}

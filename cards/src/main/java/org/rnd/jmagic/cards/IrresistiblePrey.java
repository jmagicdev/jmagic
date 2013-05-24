package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Irresistible Prey")
@Types({Type.SORCERY})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class IrresistiblePrey extends Card
{
	public IrresistiblePrey(GameState state)
	{
		super(state);

		// Target creature must be blocked this turn if able.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT);
		part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, targetedBy(target));
		this.addEffect(createFloatingEffect("Target creature must be blocked this turn if able.", part));

		// Draw a card.
		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}

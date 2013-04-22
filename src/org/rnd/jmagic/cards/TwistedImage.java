package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Twisted Image")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class TwistedImage extends Card
{
	public TwistedImage(GameState state)
	{
		super(state);

		// Switch target creature's power and toughness until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SWITCH_POWER_AND_TOUGHNESS);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
		this.addEffect(createFloatingEffect("Switch target creature's power and toughness until end of turn.", part));

		// Draw a card.
		this.addEffect(drawACard());
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Reviving Melody")
@Types({Type.SORCERY})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class RevivingMelody extends Card
{
	public RevivingMelody(GameState state)
	{
		super(state);

		// Choose one or both \u2014
		this.setNumModes(new Set(new org.rnd.util.NumberRange(1, 2)));

		// • Return target creature card from your graveyard to your hand.
		{
			SetGenerator deadThings = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance())));
			SetGenerator target = targetedBy(this.addTarget(1, deadThings, "target creature card from your graveyard"));
			this.addEffect(1, putIntoHand(target, You.instance(), "Return target creature card from your graveyard to your hand."));
		}

		// • Return target enchantment card from your graveyard to your hand.
		{
			SetGenerator deadThings = Intersect.instance(HasType.instance(Type.ENCHANTMENT), InZone.instance(GraveyardOf.instance(You.instance())));
			SetGenerator target = targetedBy(this.addTarget(2, deadThings, "target enchantment card from your graveyard"));
			this.addEffect(2, putIntoHand(target, You.instance(), "Return target enchantment card from your graveyard to your hand."));
		}
	}
}

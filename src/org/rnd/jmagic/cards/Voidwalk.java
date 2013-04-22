package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Voidwalk")
@Types({Type.SORCERY})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Voidwalk extends Card
{
	public Voidwalk(GameState state)
	{
		super(state);

		// Exile target creature. Return it to the battlefield under its owner's
		// control at the beginning of the next end step.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		EventFactory slide = new EventFactory(SLIDE, "Exile target creature. Return it to the battlefield under its owner's control at the beginning of the next end step.");
		slide.parameters.put(EventType.Parameter.CAUSE, This.instance());
		slide.parameters.put(EventType.Parameter.TARGET, target);
		this.addEffect(slide);

		// Cipher (Then you may exile this spell card encoded on a creature you
		// control. Whenever that creature deals combat damage to a player, its
		// controller may cast a copy of the encoded card without paying its
		// mana cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cipher(state));
	}
}

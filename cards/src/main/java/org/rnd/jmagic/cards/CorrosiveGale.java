package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Corrosive Gale")
@Types({Type.SORCERY})
@ManaCost("X(G/P)")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class CorrosiveGale extends Card
{
	public CorrosiveGale(GameState state)
	{
		super(state);

		// ((g/p) can be paid with either (G) or 2 life.)

		// Corrosive Gale deals X damage to each creature with flying.
		this.addEffect(spellDealDamage(ValueOfX.instance(This.instance()), Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class)), "Corrosive Gale deals X damage to each creature with flying."));
	}
}

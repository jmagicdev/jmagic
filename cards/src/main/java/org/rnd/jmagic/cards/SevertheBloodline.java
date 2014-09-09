package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sever the Bloodline")
@Types({Type.SORCERY})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class SevertheBloodline extends Card
{
	public SevertheBloodline(GameState state)
	{
		super(state);

		// Exile target creature and all other creatures with the same name as
		// that creature.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(exile(Union.instance(target, HasName.instance(NameOf.instance(target))), "Exile target creature and all other creatures with the same name as that creature."));

		// Flashback (5)(B)(B) (You may cast this card from your graveyard for
		// its flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(5)(B)(B)"));
	}
}

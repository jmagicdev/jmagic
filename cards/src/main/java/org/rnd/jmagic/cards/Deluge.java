package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Deluge")
@Types({Type.INSTANT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Odyssey.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Deluge extends Card
{
	public Deluge(GameState state)
	{
		super(state);

		SetGenerator affectedCreatures = RelativeComplement.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
		this.addEffect(tap(affectedCreatures, "Tap all creatures without flying."));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Annul")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Mirrodin.class, r = Rarity.COMMON), @Printings.Printed(ex = UrzasSaga.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Annul extends Card
{
	public Annul(GameState state)
	{
		super(state);

		// Counter target artifact or enchantment spell.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(Spells.instance(), HasType.instance(Type.ARTIFACT, Type.ENCHANTMENT)), "target artifact or enchantment spell"));
		this.addEffect(counter(target, "Counter target artifact or enchantment spell."));
	}
}

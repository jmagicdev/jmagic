package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Sculpting Steel")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Mirrodin.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class SculptingSteel extends Card
{
	public SculptingSteel(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.YouMayHaveThisEnterTheBattlefieldAsACopy(ArtifactPermanents.instance()).generateName(this.getName(), "any artifact on the battlefield").getStaticAbility(state));
	}
}

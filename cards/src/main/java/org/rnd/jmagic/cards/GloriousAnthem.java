package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Glorious Anthem")
@Types({Type.ENCHANTMENT})
@ManaCost("1WW")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = EighthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.RARE), @Printings.Printed(ex = UrzasSaga.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class GloriousAnthem extends Card
{
	public GloriousAnthem(GameState state)
	{
		super(state);

		SetGenerator creatures = CreaturePermanents.instance();
		SetGenerator youControl = ControlledBy.instance(ControllerOf.instance(This.instance()));
		SetGenerator creaturesYouControl = Intersect.instance(creatures, youControl);
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, creaturesYouControl, "Creatures you control", +1, +1, true));
	}
}

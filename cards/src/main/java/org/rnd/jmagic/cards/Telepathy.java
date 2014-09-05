package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Telepathy")
@Types({Type.ENCHANTMENT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UrzasSaga.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Telepathy extends Card
{
	public static final class RevealHands extends StaticAbility
	{
		public RevealHands(GameState state)
		{
			super(state, "Your opponents play with their hands revealed.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REVEAL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, InZone.instance(HandOf.instance(OpponentsOf.instance(ControllerOf.instance(This.instance())))));
			this.addEffectPart(part);
		}
	}

	public Telepathy(GameState state)
	{
		super(state);

		this.addAbility(new RevealHands(state));
	}
}

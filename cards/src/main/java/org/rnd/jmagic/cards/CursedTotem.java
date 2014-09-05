package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Cursed Totem")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Mirage.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class CursedTotem extends Card
{
	public static final class CursedTotemAbility0 extends StaticAbility
	{
		public CursedTotemAbility0(GameState state)
		{
			super(state, "Activated abilities of creatures can't be activated.");

			SimpleEventPattern prohibitPattern = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			prohibitPattern.put(EventType.Parameter.OBJECT, new ActivatedAbilitiesOfPattern(CreaturePermanents.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
			this.addEffectPart(part);
		}
	}

	public CursedTotem(GameState state)
	{
		super(state);

		// Activated abilities of creatures can't be activated.
		this.addAbility(new CursedTotemAbility0(state));
	}
}

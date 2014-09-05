package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Stony Silence")
@Types({Type.ENCHANTMENT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class StonySilence extends Card
{
	public static final class StonySilenceAbility0 extends StaticAbility
	{
		public StonySilenceAbility0(GameState state)
		{
			super(state, "Activated abilities of artifacts can't be activated.");

			SimpleEventPattern prohibitPattern = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			prohibitPattern.put(EventType.Parameter.OBJECT, new ActivatedAbilitiesOfPattern(ArtifactPermanents.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
			this.addEffectPart(part);
		}
	}

	public StonySilence(GameState state)
	{
		super(state);

		// Activated abilities of artifacts can't be activated.
		this.addAbility(new StonySilenceAbility0(state));
	}
}

package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Null Rod")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.WEATHERLIGHT, r = Rarity.RARE)})
@ColorIdentity({})
public final class NullRod extends Card
{
	public static final class NullRodAbility0 extends StaticAbility
	{
		public NullRodAbility0(GameState state)
		{
			super(state, "Activated abilities of artifacts can't be activated.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			pattern.put(EventType.Parameter.OBJECT, ActivatedAbilitiesOf.instance(ArtifactPermanents.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance());
			this.addEffectPart(part);
		}
	}

	public NullRod(GameState state)
	{
		super(state);

		// Activated abilities of artifacts can't be activated.
		this.addAbility(new NullRodAbility0(state));
	}
}

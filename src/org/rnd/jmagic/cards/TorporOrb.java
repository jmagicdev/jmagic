package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Torpor Orb")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.RARE)})
@ColorIdentity({})
public final class TorporOrb extends Card
{
	public static final class TorporOrbAbility0 extends StaticAbility
	{
		public TorporOrbAbility0(GameState state)
		{
			super(state, "Creatures entering the battlefield don't cause abilities to trigger.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.STOP_TRIGGERED_ABILITY);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(new EventTriggeredAbilityStopper(new SimpleZoneChangePattern(null, Battlefield.instance(), CreaturePermanents.instance(), false))));
			this.addEffectPart(part);
		}
	}

	public TorporOrb(GameState state)
	{
		super(state);

		// Creatures entering the battlefield don't cause abilities to trigger.
		this.addAbility(new TorporOrbAbility0(state));
	}
}

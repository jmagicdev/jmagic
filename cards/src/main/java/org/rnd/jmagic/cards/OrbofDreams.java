package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Orb of Dreams")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.BETRAYERS_OF_KAMIGAWA, r = Rarity.RARE)})
@ColorIdentity({})
public final class OrbofDreams extends Card
{
	public static final class TappyTappy extends StaticAbility
	{
		public TappyTappy(GameState state)
		{
			super(state, "Permanents enter the battlefield tapped.");

			ZoneChangeReplacementEffect gatekeeping = new ZoneChangeReplacementEffect(this.game, "Permanents enter the battlefield tapped");
			gatekeeping.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), null, false));
			gatekeeping.addEffect(tap(NewObjectOf.instance(gatekeeping.replacedByThis()), "A permanent enters the battlefield tapped."));

			this.addEffectPart(replacementEffectPart(gatekeeping));
		}
	}

	public OrbofDreams(GameState state)
	{
		super(state);

		this.addAbility(new TappyTappy(state));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Blind Obedience")
@Types({Type.ENCHANTMENT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class BlindObedience extends Card
{
	public static final class BlindObedienceAbility1 extends StaticAbility
	{
		public BlindObedienceAbility1(GameState state)
		{
			super(state, "Artifacts and creatures your opponents control enter the battlefield tapped.");
			SetGenerator controller = ControllerOf.instance(This.instance());

			SetGenerator stuff = HasType.instance(Type.ARTIFACT, Type.CREATURE);
			SetGenerator opponents = OpponentsOf.instance(controller);

			ZoneChangeReplacementEffect gatekeeping = new ZoneChangeReplacementEffect(this.game, "Artifacts and creatures your opponents control enter the battlefield tapped");
			gatekeeping.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), stuff, opponents, false));
			gatekeeping.addEffect(tap(NewObjectOf.instance(gatekeeping.replacedByThis()), "An artifact or creature an opponent controls enters the battlefield tapped."));
			this.addEffectPart(replacementEffectPart(gatekeeping));
		}
	}

	public BlindObedience(GameState state)
	{
		super(state);

		// Extort (Whenever you cast a spell, you may pay (w/b). If you do, each
		// opponent loses 1 life and you gain that much life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Extort(state));

		// Artifacts and creatures your opponents control enter the battlefield
		// tapped.
		this.addAbility(new BlindObedienceAbility1(state));
	}
}

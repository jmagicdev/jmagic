package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vessel of Endless Rest")
@Types({Type.ARTIFACT})
@ManaCost("3")
@ColorIdentity({})
public final class VesselofEndlessRest extends Card
{
	public static final class VesselofEndlessRestAbility0 extends EventTriggeredAbility
	{
		public VesselofEndlessRestAbility0(GameState state)
		{
			super(state, "When Vessel of Endless Rest enters the battlefield, put target card from a graveyard on the bottom of its owner's library.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(InZone.instance(GraveyardOf.instance(Players.instance())), "target card from a graveyard"));

			this.addEffect(putOnBottomOfLibrary(target, "Put target card from a graveyard on the bottom of its owner's library."));
		}
	}

	public VesselofEndlessRest(GameState state)
	{
		super(state);

		// When Vessel of Endless Rest enters the battlefield, put target card
		// from a graveyard on the bottom of its owner's library.
		this.addAbility(new VesselofEndlessRestAbility0(state));

		// (T): Add one mana of any color to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForAnyColor(state));
	}
}

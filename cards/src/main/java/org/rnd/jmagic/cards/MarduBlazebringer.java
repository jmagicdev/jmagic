package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mardu Blazebringer")
@Types({Type.CREATURE})
@SubTypes({SubType.OGRE, SubType.WARRIOR})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class MarduBlazebringer extends Card
{
	public static final class MarduBlazebringerAbility0 extends EventTriggeredAbility
	{
		public MarduBlazebringerAbility0(GameState state)
		{
			super(state, "When Mardu Blazebringer attacks or blocks, sacrifice it at end of combat.");

			this.addPattern(whenThisAttacks());
			this.addPattern(whenThisBlocks());

			EventFactory sacrifice = sacrificeSpecificPermanents(You.instance(), delayedTriggerContext(ABILITY_SOURCE_OF_THIS), "Sacrifice Mardu Blazebringer.");

			EventFactory sacrificeLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Sacrifice Mardu Blazebringer at end of combat.");
			sacrificeLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
			sacrificeLater.parameters.put(EventType.Parameter.EVENT, Identity.instance(atEndOfCombat()));
			sacrificeLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(sacrifice));
			this.addEffect(sacrificeLater);
		}
	}

	public MarduBlazebringer(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// When Mardu Blazebringer attacks or blocks, sacrifice it at end of
		// combat.
		this.addAbility(new MarduBlazebringerAbility0(state));
	}
}

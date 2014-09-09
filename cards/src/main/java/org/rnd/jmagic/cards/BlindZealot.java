package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blind Zealot")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.CLERIC})
@ManaCost("1BB")
@ColorIdentity({Color.BLACK})
public final class BlindZealot extends Card
{
	public static final class BlindZealotAbility1 extends EventTriggeredAbility
	{
		public BlindZealotAbility1(GameState state)
		{
			super(state, "Whenever Blind Zealot deals combat damage to a player, you may sacrifice it. If you do, destroy target creature that player controls.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(thatPlayer)), "target creature that player controls"));

			EventFactory effect = new EventFactory(org.rnd.jmagic.engine.EventType.IF_EVENT_THEN_ELSE, "You may sacrifice it. If you do, destroy target creature that player controls.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(youMay(sacrificeThis("it"), "You may sacrifice it.")));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(destroy(target, "Destroy target creature that player controls.")));
			this.addEffect(effect);
		}
	}

	public BlindZealot(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Intimidate (This creature can't be blocked except by artifact
		// creatures and/or creatures that share a color with it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Intimidate(state));

		// Whenever Blind Zealot deals combat damage to a player, you may
		// sacrifice it. If you do, destroy target creature that player
		// controls.
		this.addAbility(new BlindZealotAbility1(state));
	}
}

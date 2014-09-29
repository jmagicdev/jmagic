package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Secret Plans")
@Types({Type.ENCHANTMENT})
@ManaCost("GU")
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class SecretPlans extends Card
{
	public static final class SecretPlansAbility0 extends StaticAbility
	{
		public SecretPlansAbility0(GameState state)
		{
			super(state, "Face-down creatures you control get +0/+1.");
			SetGenerator yourFaceDown = Intersect.instance(CREATURES_YOU_CONTROL, FaceDown.instance());
			this.addEffectPart(modifyPowerAndToughness(yourFaceDown, +0, +1));
		}
	}

	public static final class SecretPlansAbility1 extends EventTriggeredAbility
	{
		public SecretPlansAbility1(GameState state)
		{
			super(state, "Whenever a permanent you control is turned face up, draw a card.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.TURN_PERMANENT_FACE_UP);
			pattern.put(EventType.Parameter.OBJECT, ControlledBy.instance(You.instance()));
			this.addPattern(pattern);

			this.addEffect(drawACard());
		}
	}

	public SecretPlans(GameState state)
	{
		super(state);

		// Face-down creatures you control get +0/+1.
		this.addAbility(new SecretPlansAbility0(state));

		// Whenever a permanent you control is turned face up, draw a card.
		this.addAbility(new SecretPlansAbility1(state));
	}
}

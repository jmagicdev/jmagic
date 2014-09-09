package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Glint Hawk")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class GlintHawk extends Card
{
	public static final class GlintHawkAbility1 extends EventTriggeredAbility
	{
		public GlintHawkAbility1(GameState state)
		{
			super(state, "When Glint Hawk enters the battlefield, sacrifice it unless you return an artifact you control to its owner's hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory bounce = new EventFactory(EventType.PUT_INTO_HAND_CHOICE, "Return an artifact you control to its owner's hand.");
			bounce.parameters.put(EventType.Parameter.CAUSE, This.instance());
			bounce.parameters.put(EventType.Parameter.PLAYER, You.instance());
			bounce.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			bounce.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(ArtifactPermanents.instance(), ControlledBy.instance(You.instance())));

			this.addEffect(unless(You.instance(), sacrificeThis("Glint Hawk"), bounce, "Sacrifice it unless you return an artifact you control to its owner's hand."));
		}
	}

	public GlintHawk(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Glint Hawk enters the battlefield, sacrifice it unless you
		// return an artifact you control to its owner's hand.
		this.addAbility(new GlintHawkAbility1(state));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flickerwisp")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("1WW")
@Printings({@Printings.Printed(ex = Expansion.EVENTIDE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class Flickerwisp extends Card
{
	public static final class FlickerwispAbility1 extends EventTriggeredAbility
	{
		public FlickerwispAbility1(GameState state)
		{
			super(state, "When Flickerwisp enters the battlefield, exile another target permanent. Return that card to the battlefield under its owner's control at the beginning of the next end step.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(Permanents.instance(), ABILITY_SOURCE_OF_THIS), "another target permanent"));

			EventFactory effect = new EventFactory(SLIDE, "Exile another target permanent. Return that card to the battlefield under its owner's control at the beginning of the next end step.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.TARGET, target);
			this.addEffect(effect);
		}
	}

	public Flickerwisp(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Flickerwisp enters the battlefield, exile another target
		// permanent. Return that card to the battlefield under its owner's
		// control at the beginning of the next end step.
		this.addAbility(new FlickerwispAbility1(state));
	}
}

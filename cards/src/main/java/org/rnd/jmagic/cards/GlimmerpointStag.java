package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Glimmerpoint Stag")
@Types({Type.CREATURE})
@SubTypes({SubType.ELK})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class GlimmerpointStag extends Card
{
	public static final class GlimmerpointStagAbility1 extends EventTriggeredAbility
	{
		public GlimmerpointStagAbility1(GameState state)
		{
			super(state, "When Glimmerpoint Stag enters the battlefield, exile another target permanent. Return that card to the battlefield under its owner's control at the beginning of the next end step.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(Permanents.instance(), ABILITY_SOURCE_OF_THIS), "another target permanent"));
			EventFactory factory = new EventFactory(SLIDE, "Exile another target permanent. Return that card to the battlefield under its owner's control at the beginning of the next end step.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.TARGET, target);
			this.addEffect(factory);
		}
	}

	public GlimmerpointStag(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// When Glimmerpoint Stag enters the battlefield, exile another target
		// permanent. Return that card to the battlefield under its owner's
		// control at the beginning of the next end step.
		this.addAbility(new GlimmerpointStagAbility1(state));
	}
}

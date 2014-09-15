package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Griffin Dreamfinder")
@Types({Type.CREATURE})
@SubTypes({SubType.GRIFFIN})
@ManaCost("3WW")
@ColorIdentity({Color.WHITE})
public final class GriffinDreamfinder extends Card
{
	public static final class GriffinDreamfinderAbility1 extends EventTriggeredAbility
	{
		public GriffinDreamfinderAbility1(GameState state)
		{
			super(state, "When Griffin Dreamfinder enters the battlefield, return target enchantment card from your graveyard to your hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator deadThings = Intersect.instance(HasType.instance(Type.ENCHANTMENT), InZone.instance(GraveyardOf.instance(You.instance())));
			SetGenerator target = targetedBy(this.addTarget(deadThings, "target enchantment card from your graveyard"));
			this.addEffect(putIntoHand(target, You.instance(), "Return target enchantment card from your graveyard to your hand."));
		}
	}

	public GriffinDreamfinder(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Griffin Dreamfinder enters the battlefield, return target
		// enchantment card from your graveyard to your hand.
		this.addAbility(new GriffinDreamfinderAbility1(state));
	}
}

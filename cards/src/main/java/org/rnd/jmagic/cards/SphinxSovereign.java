package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Sphinx Sovereign")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.SPHINX})
@ManaCost("4WUUB")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.BLACK})
public final class SphinxSovereign extends Card
{
	public static final class LifeSwing extends EventTriggeredAbility
	{
		public LifeSwing(GameState state)
		{
			super(state, "At the beginning of your end step, you gain 3 life if Sphinx Sovereign is untapped. Otherwise, each opponent loses 3 life.");
			this.addPattern(atTheBeginningOfYourEndStep());

			EventFactory effect = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "You gain 3 life if Sphinx Sovereign is untapped. Otherwise, each opponent loses 3 life.");
			effect.parameters.put(EventType.Parameter.IF, Intersect.instance(Untapped.instance(), ABILITY_SOURCE_OF_THIS));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(gainLife(You.instance(), 3, "You gain 3 life")));
			effect.parameters.put(EventType.Parameter.ELSE, Identity.instance(loseLife(OpponentsOf.instance(You.instance()), 3, "Each opponent loses 3 life")));
			this.addEffect(effect);
		}
	}

	public SphinxSovereign(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// At the beginning of your end step, you gain 3 life if Sphinx
		// Sovereign is untapped. Otherwise, each opponent loses 3 life.
		this.addAbility(new LifeSwing(state));
	}
}

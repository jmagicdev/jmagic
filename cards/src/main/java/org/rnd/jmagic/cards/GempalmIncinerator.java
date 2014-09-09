package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gempalm Incinerator")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class GempalmIncinerator extends Card
{
	public static final class GempalmIncineratorAbility1 extends EventTriggeredAbility
	{
		public GempalmIncineratorAbility1(GameState state)
		{
			super(state, "When you cycle Gempalm Incinerator, you may have it deal X damage to target creature, where X is the number of Goblins on the battlefield.");
			this.addPattern(whenYouCycleThis());

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

			this.addEffect(youMay(permanentDealDamage(Count.instance(Intersect.instance(Permanents.instance(), HasSubType.instance(SubType.GOBLIN))), target, "Gempalm Incinerator deals X damage to target creature, where X is the number of Goblins on the battlefield."), "You may have it deal X damage to target creature, where X is the number of Goblins on the battlefield."));
		}
	}

	public GempalmIncinerator(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Cycling (1)(R) ((1)(R), Discard this card: Draw a card.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cycling(state, "(1)(R)"));

		// When you cycle Gempalm Incinerator, you may have it deal X damage to
		// target creature, where X is the number of Goblins on the battlefield.
		this.addAbility(new GempalmIncineratorAbility1(state));
	}
}

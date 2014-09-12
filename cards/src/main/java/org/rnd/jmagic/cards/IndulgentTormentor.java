package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Indulgent Tormentor")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("3BB")
@ColorIdentity({Color.BLACK})
public final class IndulgentTormentor extends Card
{
	public static final class IndulgentTormentorAbility1 extends EventTriggeredAbility
	{
		public IndulgentTormentorAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, draw a card unless target opponent sacrifices a creature or pays 3 life.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponnt"));

			EventFactory sacrifice = sacrifice(target, 1, CreaturePermanents.instance(), "Sacrifice a creature");
			EventFactory payLife = payLife(target, 3, "Pay 3 life");

			EventFactory getTormented = new EventFactory(EventType.CHOOSE_AND_PERFORM, "Sacrifice a creature or pay 3 life");
			getTormented.parameters.put(EventType.Parameter.PLAYER, target);
			getTormented.parameters.put(EventType.Parameter.EVENT, Identity.instance(sacrifice, payLife));

			this.addEffect(unless(target, drawACard(), getTormented, "Draw a card unless target opponent sacrifices a creature or pays 3 life."));
		}
	}

	public IndulgentTormentor(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// At the beginning of your upkeep, draw a card unless target opponent
		// sacrifices a creature or pays 3 life.
		this.addAbility(new IndulgentTormentorAbility1(state));
	}
}

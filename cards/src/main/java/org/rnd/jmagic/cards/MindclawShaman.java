package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mindclaw Shaman")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.VIASHINO})
@ManaCost("4R")
@ColorIdentity({Color.RED})
public final class MindclawShaman extends Card
{
	public static final class MindclawShamanAbility0 extends EventTriggeredAbility
	{
		public MindclawShamanAbility0(GameState state)
		{
			super(state, "When Mindclaw Shaman enters the battlefield, target opponent reveals his or her hand. You may cast an instant or sorcery card from it without paying its mana cost.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
			this.addEffect(reveal(HandOf.instance(target), "Target opponent reveals his or her hand."));

			EventFactory cast = new EventFactory(PLAY_WITHOUT_PAYING_MANA_COSTS, "Cast an instant or sorcery card from it without paying its mana cost.");
			cast.parameters.put(EventType.Parameter.CAUSE, This.instance());
			cast.parameters.put(EventType.Parameter.PLAYER, You.instance());
			cast.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(InZone.instance(HandOf.instance(target)), HasType.instance(Type.INSTANT, Type.SORCERY)));
			cast.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			this.addEffect(youMay(cast));
		}
	}

	public MindclawShaman(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Mindclaw Shaman enters the battlefield, target opponent reveals
		// his or her hand. You may cast an instant or sorcery card from it
		// without paying its mana cost.
		this.addAbility(new MindclawShamanAbility0(state));
	}
}
